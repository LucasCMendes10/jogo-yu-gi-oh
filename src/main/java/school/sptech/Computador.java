package school.sptech;

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
        IO.println("\nAção escolhida: Comprar carta");
        if (!baralho.isEmpty()) {
            IO.println(nome + " está comprando uma carta...");
            Carta cartaComprada = baralho.removeFirst();
            mao.add(cartaComprada);
            IO.println("Uma nova carta foi comprada por " + nome + ".");
        } else {
            IO.println(nome + " não tem mais cartas no baralho para comprar.");
        }
    }

    @Override
    public void jogarCarta(List<Integer> posicoesCartasEscolhidas) {
        IO.println("\nAção escolhida: Jogar carta");

        if (mao.isEmpty()) {
            IO.println(nome + " não tem cartas na mão para jogar.");
            return;
        }

        IO.println(nome + " está escolhendo uma carta para jogar...");

        List<Integer> posicoesDisponiveis = new ArrayList<>();

        for (int i = 0; i < mao.size(); i++) {
            if (!posicoesCartasEscolhidas.contains(i)) {
                posicoesDisponiveis.add(i);
            }
        }

        if (posicoesDisponiveis.isEmpty()) {
            IO.println(nome + " não tem cartas válidas para jogar. Pulando a jogada...");
            return;
        }

        Integer posicaoCartaEscolhida = posicoesDisponiveis.get((int) (Math.random() * posicoesDisponiveis.size()));

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
        IO.println("\nAção escolhida: Atacar");
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

    public void atacar(Duelista oponente, Carta alvo) {
        IO.println("\nAção escolhida: Atacar");
        exibirCampo();
        IO.println(nome + " está escolhendo uma carta para atacar...");

        Carta cartaMaisForte = this.getCampo().getFirst();

        for (Carta carta : this.getCampo()) {
            if (cartaMaisForte.getAtaque() < carta.getAtaque()) {
                cartaMaisForte = carta;
            }
        }

        Carta cartaAtacante = cartaMaisForte;
        IO.println(nome + " escolheu " + cartaAtacante.getNome() + " para atacar.");

        IO.println("Há cartas no campo do oponente para defender.");
        oponente.exibirCampo();
        IO.println(nome + " está sscolhendo uma das suas cartas para atacar...");
        IO.println(nome + " escolheu atacar a carta: " + alvo.getNome());
        oponente.receberAtaque(cartaAtacante, alvo, this);
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
        IO.println("\nAção escolhida: Jogar carta");
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

    public void executarTurno(Duelista oponente) {
        IO.println("\nO computador irá escolher uma ação...");

        if (mao.isEmpty() && campo.isEmpty()) {
            comprarCarta();
            return;
        }

        if (!mao.isEmpty() && campo.isEmpty()) {
            executarJogadaInicial();
            return;
        }

        executarJogadaComCampo(oponente);
    }

    private void executarJogadaInicial() {
        int semSacrificio = 0;
        int umSacrificio = 0;
        int doisSacrificios = 0;

        for (Carta carta : mao) {
            if (carta.getQtdEstrela() < 5) semSacrificio++;
            else if (carta.getQtdEstrela() <= 6) umSacrificio++;
            else doisSacrificios++;
        }

        if (doisSacrificios > 0 && semSacrificio >= 2) {
            jogarCarta(jogadaEspecial(c -> c.getQtdEstrela() >= 7));
            return;
        }

        if (umSacrificio > 0 && semSacrificio >= 1) {
            jogarCarta(jogadaEspecial(c -> c.getQtdEstrela() == 5 || c.getQtdEstrela() == 6));
            return;
        }

        Carta carta = jogadaEspecial(c -> c.getQtdEstrela() <= 4);
        if (carta == null) {
            comprarCarta();
        } else {
            jogarCarta(carta);
        }
    }

    private void executarJogadaComCampo(Duelista oponente) {
        if (!campo.isEmpty() && oponente.getCampo().isEmpty()) {
            atacar(oponente);
            return;
        }

        Carta alvo = escolherAlvoDerrotavel(oponente);

        if (alvo != null) {
            atacar(oponente, alvo);
            return;
        }

        if (mao.isEmpty()) {
            comprarCarta();
        } else {
            jogarCarta(new ArrayList<>());
        }
    }

    private Carta escolherAlvoDerrotavel(Duelista oponente) {
        List<Carta> derrotaveis = new ArrayList<>();

        for (Carta atacante : campo) {
            for (Carta defensora : oponente.getCampo()) {
                if (atacante.getAtaque() > defensora.getDefesa()) {
                    derrotaveis.add(defensora);
                }
            }
        }

        if (derrotaveis.isEmpty()) return null;

        Carta maisFraca = derrotaveis.getFirst();
        for (Carta carta : derrotaveis) {
            if (carta.getDefesa() < maisFraca.getDefesa()) {
                maisFraca = carta;
            }
        }

        return maisFraca;
    }

}
