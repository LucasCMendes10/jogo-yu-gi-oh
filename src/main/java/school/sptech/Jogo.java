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

    public void iniciar() {
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
        Carta dragaoNegro = criarCarta("Dragão Negro de Olho Vermelhos", 7, 2400, 2000);
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

    private void realizarPrimeiraRodada() {
        jogador.embaralhar();
        computador.embaralhar();

        IO.println("\n=== Nova Rodada ===");
        IO.println("\nStatus dos Jogadores:");
        IO.println("school.sptech.Duelista 1: " + jogador.getNome() + " | Pontos de Vida: " + jogador.getPontosDeVida());
        IO.println("school.sptech.Duelista 2: " + computador.getNome() + " | Pontos de Vida: " + computador.getPontosDeVida());

        IO.println("\n===================================");
        IO.println("Vez de " + jogador.getNome());
        IO.println("===================================");
        IO.println("\nSituação do jogo:");
        jogador.exibirMao();
        jogador.exibirCampo();
        computador.exibirCampo();

        IO.println("""
                \nOpções de ação:
                1. Comprar carta
                """);

        try {
            Integer opcaoEscolhida = Integer.valueOf(IO.readln("Escolha uma ação: "));

            if (opcaoEscolhida == 1) {
                IO.println("\nAção escolhida: Comprar carta");
                jogador.comprarCarta();
                jogador.exibirMao();
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
        IO.println("\nSituação do jogo:");
        computador.exibirCampo();
        jogador.exibirCampo();

        IO.println("""
                \nOpções de ação:
                1. Comprar carta
                """);

        try {
            IO.println("O computador irá escolher uma ação...");
            IO.println("\nAção escolhida: Comprar carta");
            computador.comprarCarta();
        } catch (NumberFormatException ex) {
            IO.println("Opção inválida. Tente novamente.");
        }
    }

    private void realizarRodada() {
        if (jogador.getPontosDeVida() <= 0 || computador.getPontosDeVida() <= 0) {
            jogoEmAndamento = false;
            return;
        }

        jogador.embaralhar();
        computador.embaralhar();

        IO.println("\n=== Nova Rodada ===");
        IO.println("\nStatus dos Jogadores:");
        IO.println("school.sptech.Duelista 1: " + jogador.getNome() + " | Pontos de Vida: " + jogador.getPontosDeVida());
        IO.println("school.sptech.Duelista 2: " + computador.getNome() + " | Pontos de Vida: " + computador.getPontosDeVida());

        IO.println("\n===================================");
        IO.println("Vez de " + jogador.getNome());
        IO.println("===================================");
        IO.println("\nSituação do jogo:");
        jogador.exibirMao();
        jogador.exibirCampo();
        computador.exibirCampo();

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
                    jogador.comprarCarta();
                    jogador.exibirMao();
                }
                case 2 -> {
                    IO.println("\nAção escolhida: Jogar carta");
                    jogador.jogarCarta(new ArrayList<>());
                    jogador.exibirCampo();
                }
                case 3 -> {
                    IO.println("\nAção escolhida: Atacar");
                    jogador.atacar(computador);
                    jogador.exibirCampo();
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
        IO.println("\nSituação do jogo:");
        computador.exibirCampo();
        jogador.exibirCampo();

        computador.executarTurno(jogador);
        computador.exibirCampo();
    }

    private void encerrarJogo() {
        Duelista vencedor = jogador.getPontosDeVida() > 0 ? jogador : computador;
        IO.println("\n=============================================");
        IO.println("O jogo acabou! O vencedor é: " + vencedor.getNome());
        IO.println("=============================================");
    }
}
