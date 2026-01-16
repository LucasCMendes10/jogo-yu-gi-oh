package school.sptech;

import java.util.ArrayList;
import java.util.List;

public abstract class Duelista {
    protected String nome;
    protected Integer pontosDeVida;
    protected List<Carta> baralho;
    protected List<Carta> mao;
    protected List<Carta> campo;

    public Duelista(String nome) {
        this.nome = nome;
        this.pontosDeVida = 4000;
        this.baralho = new ArrayList<>();
        this.mao = new ArrayList<>();
        this.campo = new ArrayList<>();
    }

    protected abstract void comprarCarta() throws InterruptedException;

    protected abstract void jogarCarta(List<Integer> posicoesCartasEscolhidas) throws InterruptedException;

    protected abstract void atacar(Duelista oponente) throws InterruptedException;

    public void embaralhar() {
        if (baralho.isEmpty()) {
            IO.println(nome + " não tem cartas no baralho para embaralhar.");
            return;
        }

        java.util.Collections.shuffle(baralho);
    }

    public void exibirCampo() {
        if (campo.isEmpty()) {
            IO.println("\n" + nome + " não tem cartas no campo.");
            return;
        }

        IO.println("\nCartas no campo de " + nome + ":");

        for (int i = 0; i < campo.size(); i++) {
            IO.println((i + 1) + ": " + campo.get(i).toString());
        }
    }

    protected Boolean precisaDeSacrifio(Carta monstro) {
        return monstro.getQtdEstrela() > 4;
    }

    protected void invocarCarta(Carta monstro) throws InterruptedException {
        mao.remove(monstro);
        campo.add(monstro);
        Thread.sleep(2000);
        IO.println(nome + " invocou a carta: " + monstro.getNome());
    }

    protected void invocarCarta(Carta monstro, Integer qtdSacrificios, List<Carta> sacrificios) throws InterruptedException {
        for (int i = 0; i < qtdSacrificios; i++) {
            Carta sacrificio = sacrificios.get(i);
            campo.remove(sacrificio);
            mao.remove(sacrificio);
            IO.println(nome + " sacrificou a carta: " + sacrificio.getNome());
        }

        invocarCarta(monstro);
    }

    protected void receberAtaque(Carta cartaAtacante) throws InterruptedException {
        pontosDeVida -= cartaAtacante.getAtaque();
        Thread.sleep(1000);
        IO.println(nome + " recebeu " + cartaAtacante.getAtaque() + " de dano direto!");
        Thread.sleep(800);
        IO.println(nome + " agora tem " + pontosDeVida + " pontos de vida.");
    }

    protected void receberAtaque(Carta cartaAtacante, Carta cartaDefensora, Duelista atacante) throws InterruptedException {
        if (cartaAtacante.getAtaque() > cartaDefensora.getDefesa()) {
            campo.remove(cartaDefensora);
            Thread.sleep(1000);
            IO.println(cartaDefensora.getNome() + " não resistiu ao ataque e foi destruído(a)!");
            Integer diferenca = cartaAtacante.getAtaque() - cartaDefensora.getDefesa();
            pontosDeVida -= diferenca;
            Thread.sleep(1000);
            IO.println(nome + " recebeu " + diferenca + " de dano!");
            Thread.sleep(1000);
            IO.println(nome + " agora tem " + pontosDeVida + " pontos de vida.");

        } else if (cartaAtacante.getAtaque() < cartaDefensora.getDefesa()) {
            Integer dano = cartaDefensora.getDefesa() - cartaAtacante.getAtaque();
            Thread.sleep(1000);
            IO.println(cartaDefensora.getNome() + " resistiu ao ataque e permeneceu no campo!");
            atacante.pontosDeVida -= dano;
            Thread.sleep(1000);
            IO.println(atacante.nome + " recebeu " + dano + " de dano!");
            Thread.sleep(1000);
            IO.println(atacante.nome + " agora tem " + atacante.pontosDeVida + " pontos de vida.");
            Thread.sleep(1000);
            IO.println(nome + " permanece com " + pontosDeVida + " pontos de vida.");

        } else {
            this.campo.remove(cartaDefensora);
            atacante.getCampo().remove(cartaAtacante);
            Thread.sleep(1000);
            IO.println("Ambas as cartas têm força igual. Ambas foram destruídas!");
            Thread.sleep(1000);
            IO.println("Ambos jogadores mantêm seus pontos de vida.");
        }
    }

    public String getNome() {
        return nome;
    }

    public Integer getPontosDeVida() {
        return pontosDeVida;
    }

    public List<Carta> getBaralho() {
        return baralho;
    }

    public List<Carta> getMao() {
        return mao;
    }

    public List<Carta> getCampo() {
        return campo;
    }

    @Override
    public String toString() {
        return "Duelista{" +
                "nome='" + nome + '\'' +
                ", pontosDeVida=" + pontosDeVida +
                ", baralho=" + baralho +
                ", mao=" + mao +
                ", campo=" + campo +
                '}';
    }
}
