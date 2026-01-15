import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Computador extends Duelista {
    public Computador(String nome) {
        super(nome);
    }

    public void montarBaralho(List<Carta> cartas, Integer tamanhoBaralhoJogador) {
        java.util.Collections.shuffle(cartas);
        for (int i = 0; i < tamanhoBaralhoJogador; i++) {
            this.baralho.add(cartas.get(i));
        }
    }

    @Override
    public void comprarCarta() {
        if (!baralho.isEmpty()) {
            Carta cartaComprada = baralho.removeFirst();
            mao.add(cartaComprada);
            IO.println("Uma nova carta foi comprada por " + nome + ".");
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

        IO.println(nome + " está escolhendo uma carta para jogar...");
        Integer posicaoCartaEscolhida = (int) (Math.random() * mao.size());

        while (posicoesCartasEscolhidas.contains(posicaoCartaEscolhida)) {
            posicaoCartaEscolhida = (int) (Math.random() * mao.size());
        }

        Carta cartaEscolhida = mao.get(posicaoCartaEscolhida);
        posicoesCartasEscolhidas.add(posicaoCartaEscolhida);

        if (!precisaDeSacrifio(cartaEscolhida)) {
            invocarCarta(cartaEscolhida);
            return;
        }

        IO.println(nome + " precisará sacrificar cartas para invocar a carta que escolheu.");
        Integer qtdSacrificios = (cartaEscolhida.getQtdEstrela() == 5 || cartaEscolhida.getQtdEstrela() == 6) ? 1 : 2;

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
        IO.println(nome + " está escolhendo as cartas para sacrificar...");

        for (int i = 0; i < qtdSacrificios; i++) {
            Integer posicaoCartaSacrificio = (int) (Math.random() * todasAsCartas.size());

            while (posicaoCartaSacrificio.equals(posicaoCartaEscolhida) || cartasSacrificio.contains(todasAsCartas.get(posicaoCartaSacrificio))) {
                posicaoCartaSacrificio = (int) (Math.random() * todasAsCartas.size());
            }

            Carta cartaSacrificio = todasAsCartas.get(posicaoCartaSacrificio);
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
        IO.println(nome + " está escolhendo uma carta para atacar...");
        Integer posicaoCartaAtacante = (int) (Math.random() * mao.size());
        Carta cartaAtacante = campo.get(posicaoCartaAtacante);
        IO.println(nome + " escolheu " + cartaAtacante.getNome() + " para atacar.");

        if (oponente.getCampo().isEmpty()) {
            IO.println(oponente.getNome() + " não tem cartas no campo para se defender.");
            oponente.receberAtaque(cartaAtacante);
        } else {
            IO.println("Há cartas no campo do oponente para defender.");
            oponente.exibirCampo();
            IO.println(nome + " está sscolhendo uma das suas cartas para atacar...");
            Integer escolhaAlvo = (int) (Math.random() * oponente.getCampo().size());
            Carta cartaDefensora = oponente.getCampo().get(escolhaAlvo);
            IO.println(nome + " escolheu atacar a carta: " + cartaDefensora.getNome());
            oponente.receberAtaque(cartaAtacante, cartaDefensora, this);
        }
    }

    public Carta jogadaEspecial(Predicate<Carta> condicao) {
        for (Carta carta : mao) {
            if (condicao.test(carta)) {
                return carta;
            }
        }

        return null;
    }

    public void jogarCarta(Carta carta) {
        IO.println(nome + " está escolhendo uma carta para jogar...");
        Integer posicaoCartaEscolhida = 0;

        for (int i = 0; i < mao.size(); i++) {
            if (mao.get(i).equals(carta)) {
                posicaoCartaEscolhida = i;
                break;
            }
        }

        if (!precisaDeSacrifio(carta)) {
            invocarCarta(carta);
            return;
        }

        IO.println(nome + " precisará sacrificar cartas para invocar a carta que escolheu.");
        Integer qtdSacrificios = (carta.getQtdEstrela() == 5 || carta.getQtdEstrela() == 6) ? 1 : 2;

        List<Carta> todasAsCartas = new ArrayList<>();
        todasAsCartas.addAll(mao);
        todasAsCartas.addAll(campo);

        List<Carta> cartasSacrificio = new ArrayList<>();
        IO.println(nome + " está escolhendo as cartas para sacrificar...");

        for (int i = 0; i < qtdSacrificios; i++) {
            Integer posicaoCartaSacrificio = (int) (Math.random() * todasAsCartas.size());

            while (posicaoCartaSacrificio.equals(posicaoCartaEscolhida) || cartasSacrificio.contains(todasAsCartas.get(posicaoCartaSacrificio))) {
                posicaoCartaSacrificio = (int) (Math.random() * todasAsCartas.size());
            }

            Carta cartaSacrificio = todasAsCartas.get(posicaoCartaSacrificio);
            cartasSacrificio.add(cartaSacrificio);
        }

        invocarCarta(carta, qtdSacrificios, cartasSacrificio);
    }
}
