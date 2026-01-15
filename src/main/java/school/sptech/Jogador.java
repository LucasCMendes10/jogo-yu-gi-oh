package school.sptech;

import java.util.ArrayList;
import java.util.List;

public class Jogador extends Duelista {
    public Jogador(String nome) {
        super(nome);
    }

    public void montarBaralho(List<Carta> cartas) {
        List<Integer> indicesCartasEscolhidas = new ArrayList<>();

        Integer tamanhoBaralho = Integer.valueOf(IO.readln("Escolha o tamanho do baralho (mínimo 5 cartas): "));
        while (tamanhoBaralho < 5 || tamanhoBaralho > cartas.size()) {
            IO.println("Tamanho inválido. O baralho deve ter no mínimo 5 cartas e no máximo " + cartas.size() + " cartas.");
            tamanhoBaralho = Integer.valueOf(IO.readln("Escolha o tamanho do baralho (mínimo 5 cartas): "));
        }

        if (tamanhoBaralho == cartas.size()) {
            IO.println("Você escolheu todas as cartas disponíveis para o baralho.");
            this.baralho.addAll(cartas);
            IO.println("Baralho montado com sucesso!");
            return;
        }

        IO.println("Escolha as cartas para montar o baralho:");
        for (int i = 0; i < tamanhoBaralho; i++) {
            Integer posicaoCartaEscolhida = Integer.valueOf(IO.readln("Escolha uma carta entre as posições (1 a " + cartas.size() + "): "));

            while (posicaoCartaEscolhida < 1 || posicaoCartaEscolhida > cartas.size() || indicesCartasEscolhidas.contains(posicaoCartaEscolhida)) {
                IO.println("Posição inválida. Tente novamente.");
                posicaoCartaEscolhida = Integer.valueOf(IO.readln("Escolha uma carta entre as posições (1 a " + cartas.size() + "): "));
            }

            Carta cartaEscolhida = cartas.get(posicaoCartaEscolhida - 1);
            this.baralho.add(cartaEscolhida);
            indicesCartasEscolhidas.add(posicaoCartaEscolhida);
        }

        IO.println("Baralho montado com sucesso!");
    }

    @Override
    public void comprarCarta() {
        if (!baralho.isEmpty()) {
            Carta cartaComprada = baralho.removeFirst();
            mao.add(cartaComprada);
            IO.println(nome + " comprou a carta: " + cartaComprada.getNome());
        } else {
            IO.println(nome + " não tem mais cartas no baralho para comprar.");
        }
    }

    @Override
    public void jogarCarta(List<Integer> posicoesCartasEscolhidas) {
        if (mao.isEmpty()) {
            IO.println(nome + " não tem cartas na mão para jogar.");
            return;
        }

        Integer posicaoCartaEscolhida = Integer.valueOf(IO.readln("Escolha uma carta para jogar (1 a " + mao.size() + "): "));

        if (posicoesCartasEscolhidas.contains(posicaoCartaEscolhida)) {
            IO.println("Essa carta não pode ser invocada no momento. Escolha outra carta.");
            posicaoCartaEscolhida = Integer.valueOf(IO.readln("Escolha uma carta para jogar (1 a " + mao.size() + "): "));
        }

        Carta cartaEscolhida = mao.get(posicaoCartaEscolhida - 1);
        IO.println(nome + " escolheu a carta: " + cartaEscolhida.getNome() + " para colocar no campo.");
        posicoesCartasEscolhidas.add(posicaoCartaEscolhida);

        if (!precisaDeSacrifio(cartaEscolhida)) {
            invocarCarta(cartaEscolhida);
            return;
        }

        IO.println("É necessário sacrificar cartas para invocar " + cartaEscolhida.getNome());
        Integer qtdSacrificios = (cartaEscolhida.getQtdEstrela() == 5 || cartaEscolhida.getQtdEstrela() == 6) ? 1 : 2;
        IO.println("Essa carta precisa de " + qtdSacrificios + " sacrifício(s).");
        exibirMao();
        exibirCampo();

        List<Carta> todasAsCartas = new ArrayList<>();
        todasAsCartas.addAll(mao);
        todasAsCartas.addAll(campo);

        if (todasAsCartas.size() < (qtdSacrificios + 1)) {
            IO.println(nome + " não tem cartas suficientes na mão ou no campo para serem sacrificas.");

            Integer monstroSemPrecisarSacrificio = 0;

            for (int i = 0; i < todasAsCartas.size(); i++) {
                if (!precisaDeSacrifio(todasAsCartas.get(i))) {
                    monstroSemPrecisarSacrificio += 1;
                }
            }

            if (monstroSemPrecisarSacrificio == 0) {
                IO.println(nome + " não tem cartas suficientes na mão ou no campo para sacrificar e nem cartas que possam ser invocadas sem sacrifício. Pulando a jogada...");
                return;
            }

            jogarCarta(posicoesCartasEscolhidas);
        }

        List<Carta> cartasSacrificio = new ArrayList<>();
        IO.println("Escolha as cartas para sacrificar:");

        for (int i = 0; i < qtdSacrificios; i++) {
            Integer posicaoCartaSacrificio = Integer.valueOf(IO.readln("Escolha a carta para sacrificar (1 a " + todasAsCartas.size() + "): "));

            while (posicaoCartaSacrificio.equals(posicaoCartaEscolhida) || cartasSacrificio.contains(todasAsCartas.get(posicaoCartaSacrificio - 1))) {
                IO.println("school.sptech.Carta inválida. Escolha outra carta.");
                posicaoCartaSacrificio = Integer.valueOf(IO.readln("Escolha a carta para sacrificar (1 a " + todasAsCartas.size() + "): "));
            }

            Carta cartaSacrificio = todasAsCartas.get(posicaoCartaSacrificio - 1);
            cartasSacrificio.add(cartaSacrificio);
        }

        invocarCarta(cartaEscolhida, qtdSacrificios, cartasSacrificio);
    }

    @Override
    public void atacar(Duelista oponente) {
        if (campo.isEmpty()) {
            IO.println(nome + " não tem cartas no campo para atacar.");
            return;
        }

        exibirCampo();
        Integer posicaoCartaAtacante = Integer.valueOf(IO.readln("Escolha uma carta para atacar (1 a " + campo.size() + "): "));
        Carta cartaAtacante = campo.get(posicaoCartaAtacante - 1);
        IO.println(nome + " escolheu " + cartaAtacante.getNome() + " para atacar.");

        if (oponente.getCampo().isEmpty()) {
            IO.println(oponente.getNome() + " não tem cartas no campo para se defender.");
            oponente.receberAtaque(cartaAtacante);
        } else {
            IO.println("Há cartas no campo do oponente para defender.");
            oponente.exibirCampo();
            Integer escolhaAlvo = Integer.valueOf(IO.readln("Escolha uma dessas cartas para atacar (1 a " + oponente.getCampo().size() + "): "));
            Carta cartaDefensora = oponente.getCampo().get(escolhaAlvo - 1);
            IO.println(nome + " escolheu atacar a carta: " + cartaDefensora.getNome());
            oponente.receberAtaque(cartaAtacante, cartaDefensora, this);
        }
    }

    public void exibirMao() {
        if (mao.isEmpty()) {
            IO.println("\n" + nome + " não tem cartas na mão.");
            return;
        }

        IO.println("\nCartas na mão de " + nome + ":");

        for (int i = 0; i < mao.size(); i++) {
            IO.println((i + 1) + ": " + mao.get(i).toString());
        }
    }
}
