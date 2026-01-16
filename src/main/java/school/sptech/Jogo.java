package school.sptech;

import java.util.ArrayList;

import static school.sptech.Carta.criarCarta;

public class Jogo {
    private Fichario fichario;
    private Jogador jogador;
    private Computador computador;
    private Boolean jogoEmAndamento;

    public Jogo(Jogador jogador, Computador computador) {
        this.fichario = new Fichario();
        this.jogador = jogador;
        this.computador = computador;
        this.jogoEmAndamento = true;
    }

    public void iniciar() throws InterruptedException {
        setFichario();
        this.fichario.exibirCartasDisponiveis();
        jogador.montarBaralho(this.fichario.getCartas());
        computador.montarBaralho(this.fichario.getCartas(), jogador.getBaralho().size());

        realizarPrimeiraRodada();

        while (jogoEmAndamento) {
            realizarRodada();
        }

        encerrarJogo();
    }

    private void setFichario() {
        Carta magoNegro = criarCarta("Mago Negro", 7, 2500, 2100);
        Carta dragaoBranco = criarCarta("Dragão Branco de Olhos Azuis", 8, 3000, 2500);
        Carta magoDoTempo = criarCarta("Mago do Tempo", 2, 500, 400);
        Carta dragaoNegro = criarCarta("Dragão Negro de Olhos Vermelhos", 7, 2400, 2000);
        Carta magaNegra = criarCarta("Pequena Maga Negra", 6, 2000, 1700);
        Carta guerreiroGuardiao = criarCarta("Guerreiro Guardião Celta", 4, 1400, 1200);
        Carta ladyHarpia = criarCarta("Lady Harpia", 4, 1300, 1400);
        Carta sparkman = criarCarta("SPARKMAN, HERÓI do Elemento", 4, 1600, 1400);
        Carta kuriboh = criarCarta("Kuriboh", 1, 300, 200);
        Carta dragaoBebe = criarCarta("Dragão Bebê", 3, 1200, 700);
        Carta menteAliada = criarCarta("Mente Aliada", 5, 1800, 1400);
        Carta guardaFlamvell = criarCarta("Guarda Flamvell", 1, 100, 2000);
        Carta dragaoGuarda = criarCarta("Dragão-Guarda Justícia", 2, 0, 2100);
        Carta controladorGenex = criarCarta("Controlador Genex", 3, 1400, 1200);
        Carta guerreiroSintonia = criarCarta("Guerreiro da Sintonia", 3, 1600, 200);

        this.fichario.adicionarCartas(magoNegro, dragaoBranco, magoDoTempo, dragaoNegro, magaNegra, guerreiroGuardiao,
                ladyHarpia, sparkman, kuriboh, dragaoBebe, menteAliada, guardaFlamvell, dragaoGuarda, controladorGenex,
                guerreiroSintonia);
    }

    private void realizarPrimeiraRodada() throws InterruptedException {
        Thread.sleep(1000);
        IO.println("\nIniciando duelo entre " + jogador.getNome() + " e " + computador.getNome() + "...");
        Thread.sleep(2000);
        IO.println("Boa sorte duelistas!\n");
        Thread.sleep(2000);

        IO.println("Embaralhando os baralhos...");
        jogador.embaralhar();
        computador.embaralhar();
        Thread.sleep(1000);

        IO.println("\n=== Nova Rodada ===");
        IO.println("\nStatus dos Jogadores:");
        IO.println("Duelista 1: " + jogador.getNome() + " | Pontos de Vida: " + jogador.getPontosDeVida());
        IO.println("Duelista 2: " + computador.getNome() + " | Pontos de Vida: " + computador.getPontosDeVida());
        Thread.sleep(2000);

        IO.println("\n===================================");
        IO.println("Vez de " + jogador.getNome());
        IO.println("===================================");
        Thread.sleep(2000);
        IO.println("\nSituação do jogo:");
        jogador.exibirMao();
        jogador.exibirCampo();
        computador.exibirCampo();

        Thread.sleep(2000);

        IO.println("""
                \nOpções de ação:
                1. Comprar carta
                """);

        try {
            Integer opcaoEscolhida = Integer.valueOf(IO.readln("Escolha uma ação: "));

            if (opcaoEscolhida == 1) {
                IO.println("\nAção escolhida: Comprar carta");
                Thread.sleep(1500);
                jogador.comprarCarta();
                Thread.sleep(2000);
                jogador.exibirMao();
                Thread.sleep(3000);
            } else {
                IO.println("Opção inválida. Tente novamente.");
            }
        } catch (NumberFormatException ex) {
            IO.println("Opção inválida. Tente novamente.");
            return;
        }

        IO.println("\n===================================");
        IO.println("Vez de " + computador.getNome());
        IO.println("===================================");
        Thread.sleep(2000);
        IO.println("\nSituação do jogo:");
        computador.exibirCampo();
        jogador.exibirCampo();

        Thread.sleep(2000);

        IO.println("""
                \nOpções de ação:
                1. Comprar carta
                """);

        try {
            Thread.sleep(1500);
            IO.println("O computador irá escolher uma ação...");
            Thread.sleep(2500);
            computador.comprarCarta();
            Thread.sleep(2000);
        } catch (NumberFormatException ex) {
            IO.println("Opção inválida. Tente novamente.");
        }
    }

    private void realizarRodada() throws InterruptedException {
        if (jogador.getPontosDeVida() <= 0 || computador.getPontosDeVida() <= 0) {
            jogoEmAndamento = false;
            return;
        }

        IO.println("\nEmbaralhando os baralhos...");
        jogador.embaralhar();
        computador.embaralhar();
        Thread.sleep(1000);

        IO.println("\n=== Nova Rodada ===");
        IO.println("\nStatus dos Jogadores:");
        IO.println("Duelista 1: " + jogador.getNome() + " | Pontos de Vida: " + jogador.getPontosDeVida());
        IO.println("Duelista 2: " + computador.getNome() + " | Pontos de Vida: " + computador.getPontosDeVida());
        Thread.sleep(2000);

        IO.println("\n===================================");
        IO.println("Vez de " + jogador.getNome());
        IO.println("===================================");
        Thread.sleep(2000);
        IO.println("\nSituação do jogo:");
        jogador.exibirMao();
        jogador.exibirCampo();
        computador.exibirCampo();

        Thread.sleep(2000);

        IO.println("""
                \nOpções de ação:
                1. Comprar carta
                2. Jogar carta
                3. Atacar
                """);

        try {
            Integer opcaoEscolhida = Integer.valueOf(IO.readln("Escolha uma ação: "));

            switch (opcaoEscolhida) {
                case 1 -> {
                    IO.println("\nAção escolhida: Comprar carta");
                    Thread.sleep(1500);
                    jogador.comprarCarta();
                    Thread.sleep(2000);
                    jogador.exibirMao();
                    Thread.sleep(3000);
                }
                case 2 -> {
                    IO.println("\nAção escolhida: Jogar carta");
                    Thread.sleep(1500);
                    jogador.jogarCarta(new ArrayList<>());
                    Thread.sleep(2000);
                    jogador.exibirCampo();
                    Thread.sleep(3000);
                }
                case 3 -> {
                    IO.println("\nAção escolhida: Atacar");
                    Thread.sleep(1500);
                    jogador.atacar(computador);
                    Thread.sleep(2000);
                    jogador.exibirCampo();
                    Thread.sleep(3000);
                }
                default -> IO.println("Opção inválida. Tente novamente.");
            }
        } catch (NumberFormatException ex) {
            IO.println("Opção inválida. Tente novamente.");
            return;
        }

        if (computador.getPontosDeVida() <= 0) {
            return;
        }

        IO.println("\n===================================");
        IO.println("Vez de " + computador.getNome());
        IO.println("===================================");
        Thread.sleep(2000);
        IO.println("\nSituação do jogo:");
        computador.exibirCampo();
        jogador.exibirCampo();

        Thread.sleep(3000);

        IO.println("""
                \nOpções de ação:
                1. Comprar carta
                2. Jogar carta
                3. Atacar
                """);

        computador.executarTurno(jogador);
        Thread.sleep(2000);
        computador.exibirCampo();
        Thread.sleep(2000);
    }

    private void encerrarJogo() {
        Duelista vencedor = jogador.getPontosDeVida() > 0 ? jogador : computador;
        IO.println("\n=============================================");
        IO.println("O jogo acabou! O vencedor é: " + vencedor.getNome());
        IO.println("=============================================");
    }
}
