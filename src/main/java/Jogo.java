import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        Carta magoNegro = new Carta("Mago Negro", 7, 2500, 2100);
        Carta dragaoBranco = new Carta("Dragão Branco de Olhos Azuis", 8, 3000, 2500);
        Carta magoDoTempo = new Carta("Mago do Tempo", 2, 500, 400);
        Carta dragaoNegro = new Carta("Dragão Negro de Olho Vermelhos", 7, 2400, 2000);
        Carta magaNegra = new Carta("Pequena Maga Negra", 6, 2000, 1700);
        Carta guerreiroGuardiao = new Carta("Guerreiro Guardião Celta", 4, 1400, 1200);
        Carta ladyHarpia = new Carta("Lady Harpia", 4, 1300, 1400);
        Carta sparkman = new Carta("SPARKMAN, HERÓI do Elemento", 4, 1600, 1400);
        Carta kuriboh = new Carta("Kuriboh", 1, 300, 200);
        Carta dragaoBebe = new Carta("Dragão Bebê", 3, 1200, 700);
        Carta menteAliada = new Carta("Mente Aliada", 5, 1800, 1400);
        Carta guardaFlamvell = new Carta("Guarda Flamvell", 1, 100, 2000);
        Carta dragaoGuarda = new Carta("Dragão-Guarda Justícia", 2, 0, 2100);
        Carta controladorGenex = new Carta("Controlador Genex", 3, 1400, 1200);
        Carta guerreiroSintonia = new Carta("Guerreiro da Sintonia", 3, 1600, 200);

        this.fichario.adicionarCartas(magoNegro, dragaoBranco, magoDoTempo, dragaoNegro, magaNegra, guerreiroGuardiao,
                ladyHarpia, sparkman, kuriboh, dragaoBebe, menteAliada, guardaFlamvell, dragaoGuarda, controladorGenex,
                guerreiroSintonia);
    }

    private void realizarPrimeiraRodada() {
        jogador.embaralhar();
        computador.embaralhar();

        IO.println("\n=== Nova Rodada ===");
        IO.println("\nStatus dos Jogadores:");
        IO.println("Duelista 1: " + jogador.getNome() + " | Pontos de Vida: " + jogador.getPontosDeVida());
        IO.println("Duelista 2: " + computador.getNome() + " | Pontos de Vida: " + computador.getPontosDeVida());

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
        IO.println("Duelista 1: " + jogador.getNome() + " | Pontos de Vida: " + jogador.getPontosDeVida());
        IO.println("Duelista 2: " + computador.getNome() + " | Pontos de Vida: " + computador.getPontosDeVida());

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
                case 1:
                    IO.println("\nAção escolhida: Comprar carta");
                    jogador.comprarCarta();
                    jogador.exibirMao();
                    break;
                case 2:
                    IO.println("\nAção escolhida: Jogar carta");
                    jogador.jogarCarta(new ArrayList<>());
                    jogador.exibirCampo();
                    break;
                case 3:
                    IO.println("\nAção escolhida: Atacar");
                    jogador.atacar(computador);
                    jogador.exibirCampo();
                    break;
                default:
                    IO.println("Opção inválida. Tente novamente.");
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

        IO.println("""
                \nOpções de ação:
                1. Comprar carta
                2. Jogar carta
                3. Atacar
                """);

        try {
            IO.println("O computador irá escolher uma ação...");

            if (computador.getMao().isEmpty() && computador.getCampo().isEmpty()) {
                IO.println("\nAção escolhida: Comprar carta");
                computador.comprarCarta();
                return;
            } else if (!computador.getMao().isEmpty() && computador.getCampo().isEmpty()) {
                Integer qtdCartasQueNaoPrecisamDeSacrificio = 0;
                Integer qtdCartasQuePrecisamDe1Sacrificio = 0;
                Integer qtdCartasQuePrecisamDe2Sacrificios = 0;

                for (Carta carta : computador.getMao()) {
                    if (carta.getQtdEstrela() < 5) {
                        qtdCartasQueNaoPrecisamDeSacrificio++;
                    } else if (carta.getQtdEstrela() <= 6) {
                        qtdCartasQuePrecisamDe1Sacrificio++;
                    } else {
                        qtdCartasQuePrecisamDe2Sacrificios++;
                    }
                }

                if (qtdCartasQuePrecisamDe2Sacrificios > 0 && qtdCartasQueNaoPrecisamDeSacrificio >= 2) {
                    Carta cartaParaJogar = computador.jogadaEspecial(carta -> carta.getQtdEstrela() >= 7);

                    IO.println("\nAção escolhida: Jogar carta");
                    computador.jogarCarta(cartaParaJogar);
                    computador.exibirCampo();
                    return;
                } else if (qtdCartasQuePrecisamDe1Sacrificio > 0 && qtdCartasQueNaoPrecisamDeSacrificio >= 1) {
                    Carta cartaParaJogar = computador.jogadaEspecial(carta -> carta.getQtdEstrela() == 5 || carta.getQtdEstrela() == 6);

                    IO.println("\nAção escolhida: Jogar carta");
                    computador.jogarCarta(cartaParaJogar);
                    computador.exibirCampo();
                    return;
                }

                Carta cartaParaJogar = computador.jogadaEspecial(carta -> carta.getQtdEstrela() <= 4);

                if (Objects.isNull(cartaParaJogar)) {
                    IO.println("\nAção escolhida: Comprar carta");
                    computador.comprarCarta();
                    return;
                }

                IO.println("\nAção escolhida: Jogar carta");
                computador.jogarCarta(cartaParaJogar);
                computador.exibirCampo();
                return;
            }

            Integer opcaoEscolhida = (int) (Math.random() * 3 + 1);

            if (!computador.getCampo().isEmpty()) {
                if (jogador.getCampo().isEmpty()) {
                    opcaoEscolhida = 3;
                } else {
                    List<Carta> cartasDerrotaveis = new ArrayList<>();

                    for (Carta cartaComputador : computador.getCampo()) {
                        for (int i = 0; i < jogador.getCampo().size(); i++) {
                            if (cartaComputador.getAtaque() > jogador.getCampo().get(i).getDefesa()) {
                                cartasDerrotaveis.add(jogador.getCampo().get(i));
                            }
                        }
                    }

                    if (!cartasDerrotaveis.isEmpty()) {
                        Carta cartaMaisFraca = cartasDerrotaveis.getFirst();

                        for (Carta carta : cartasDerrotaveis) {
                            if (cartaMaisFraca.getDefesa() > carta.getDefesa()) {
                                cartaMaisFraca = carta;
                            }
                        }

                        IO.println("\nAção escolhida: Atacar");
                        computador.atacar(jogador, cartaMaisFraca);
                        computador.exibirCampo();
                        return;
                    } else {
                        if (computador.getMao().isEmpty()) {
                            opcaoEscolhida = 1;
                        } else {
                            opcaoEscolhida = 2;
                        }
                    }
                }

                switch (opcaoEscolhida) {
                    case 1:
                        IO.println("\nAção escolhida: Comprar carta");
                        computador.comprarCarta();
                        break;
                    case 2:
                        IO.println("\nAção escolhida: Jogar carta");
                        computador.jogarCarta(new ArrayList<>());
                        computador.exibirCampo();
                        break;
                    case 3:
                        IO.println("\nAção escolhida: Atacar");
                        computador.atacar(jogador);
                        computador.exibirCampo();
                        break;
                    default:
                        IO.println("Opção inválida. Tente novamente.");
                }
            } else {
                switch (opcaoEscolhida) {
                    case 1:
                        IO.println("\nAção escolhida: Comprar carta");
                        computador.comprarCarta();
                        break;
                    case 2:
                        IO.println("\nAção escolhida: Jogar carta");
                        computador.jogarCarta(new ArrayList<>());
                        computador.exibirCampo();
                        break;
                    case 3:
                        IO.println("\nAção escolhida: Atacar");
                        computador.atacar(jogador);
                        computador.exibirCampo();
                        break;
                    default:
                        IO.println("Opção inválida. Tente novamente.");
                }
            }
        } catch (NumberFormatException ex) {
            IO.println("Opção inválida. Tente novamente.");
        }
    }

    private void encerrarJogo() {
        Duelista vencedor = jogador.getPontosDeVida() > 0 ? jogador : computador;
        IO.println("\n=============================================");
        IO.println("O jogo acabou! O vencedor é: " + vencedor.getNome());
        IO.println("=============================================");
    }
}
