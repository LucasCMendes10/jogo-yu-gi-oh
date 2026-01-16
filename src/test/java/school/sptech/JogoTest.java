package school.sptech;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Jogo")
class JogoTest {

    @Test
    @DisplayName("Construtor deve inicializar jogo corretamente")
    void construtorDeveInicializarJogo() {
        Jogador jogador = new Jogador("Yugi");
        Computador computador = new Computador("CPU");

        Jogo jogo = new Jogo(jogador, computador);

        assertNotNull(jogo, "Jogo não deve ser nulo após construção");
    }

    @Test
    @DisplayName("Jogador e computador devem iniciar com 4000 pontos de vida")
    void jogadoresIniciamComPontosDeVidaPadrao() {
        Jogador jogador = new Jogador("Yugi");
        Computador computador = new Computador("CPU");

        assertEquals(4000, jogador.getPontosDeVida());
        assertEquals(4000, computador.getPontosDeVida());
    }

    @Test
    @DisplayName("Jogo deve encerrar quando pontos de vida do jogador chegam a zero")
    void jogoDeveEncerrarQuandoJogadorMorre() throws InterruptedException {
        Jogador jogador = new Jogador("Yugi");
        Computador computador = new Computador("CPU");

        new Jogo(jogador, computador);

        jogador.receberAtaque(Carta.criarCarta("Ataque Fatal", 4, 4000, 1000));

        assertTrue(jogador.getPontosDeVida() <= 0,
                "Jogador deveria estar derrotado");
    }

    @Test
    @DisplayName("Jogo deve encerrar quando pontos de vida do computador chegam a zero")
    void jogoDeveEncerrarQuandoComputadorMorre() throws InterruptedException {
        Jogador jogador = new Jogador("Yugi");
        Computador computador = new Computador("CPU");

        new Jogo(jogador, computador);

        computador.receberAtaque(Carta.criarCarta("Ataque Fatal", 4, 4000, 1000));

        assertTrue(computador.getPontosDeVida() <= 0,
                "Computador deveria estar derrotado");
    }

    @Test
    @DisplayName("Jogo deve iniciar em andamento")
    void jogoDeveIniciarEmAndamento() throws Exception {
        Jogador jogador = new Jogador("Yugi");
        Computador computador = new Computador("CPU");
        Jogo jogo = new Jogo(jogador, computador);

        var field = Jogo.class.getDeclaredField("jogoEmAndamento");
        field.setAccessible(true);

        Boolean emAndamento = (Boolean) field.get(jogo);

        assertTrue(emAndamento, "Jogo deveria iniciar em andamento");
    }

    @Test
    @DisplayName("Vencedor deve ser o jogador quando computador perde toda a vida")
    void vencedorDeveSerJogador() throws InterruptedException {
        Jogador jogador = new Jogador("Yugi");
        Computador computador = new Computador("CPU");

        new Jogo(jogador, computador);

        computador.receberAtaque(Carta.criarCarta("Final", 4, 4000, 0));

        assertTrue(jogador.getPontosDeVida() > 0);
        assertTrue(computador.getPontosDeVida() <= 0);
    }
}
