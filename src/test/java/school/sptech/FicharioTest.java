package school.sptech;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Fichario")
public class FicharioTest {

    @Test
    @DisplayName("Construtor deve inicializar o fichário com lista de cartas vazia")
    void construtorDeveInicializarListaVazia() {
        Fichario fichario = new Fichario();

        assertNotNull(fichario);
        assertNotNull(fichario.getCartas());
        assertTrue(fichario.getCartas().isEmpty());
    }

    @Test
    @DisplayName("adicionarCarta deve adicionar uma carta ao fichário")
    void adicionarCartaDeveAdicionarUmaCarta() {
        Fichario fichario = new Fichario();
        Carta carta = Carta.criarCarta("Mago Negro", 7, 2500, 2100);

        fichario.adicionarCarta(carta);

        assertEquals(1, fichario.getCartas().size());
        assertTrue(fichario.getCartas().contains(carta));
    }

    @Test
    @DisplayName("adicionarCartas deve adicionar múltiplas cartas ao fichário")
    void adicionarCartasDeveAdicionarMultiplasCartas() {
        Fichario fichario = new Fichario();

        Carta c1 = Carta.criarCarta("Dragão Branco de Olhos Azuis", 8, 3000, 2500);
        Carta c2 = Carta.criarCarta("Exodia", 10, 1000, 1000);
        Carta c3 = Carta.criarCarta("Kuriboh", 1, 300, 200);

        fichario.adicionarCartas(c1, c2, c3);

        assertEquals(3, fichario.getCartas().size());
        assertIterableEquals(List.of(c1, c2, c3), fichario.getCartas());
    }

    @Test
    @DisplayName("removerCarta deve remover uma carta existente do fichário")
    void removerCartaDeveRemoverCartaExistente() {
        Fichario fichario = new Fichario();

        Carta carta = Carta.criarCarta("Mago Negro", 7, 2500, 2100);
        fichario.adicionarCarta(carta);

        fichario.removerCarta(carta);

        assertTrue(fichario.getCartas().isEmpty());
    }

    @Test
    @DisplayName("removerCartas deve remover múltiplas cartas do fichário")
    void removerCartasDeveRemoverMultiplasCartas() {
        Fichario fichario = new Fichario();

        Carta c1 = Carta.criarCarta("A", 5, 1500, 1500);
        Carta c2 = Carta.criarCarta("B", 6, 1600, 1600);
        Carta c3 = Carta.criarCarta("C", 7, 1700, 1700);

        fichario.adicionarCartas(c1, c2, c3);
        fichario.removerCartas(c1, c3);

        assertEquals(1, fichario.getCartas().size());
        assertTrue(fichario.getCartas().contains(c2));
    }

    @Test
    @DisplayName("exibirCartasDisponiveis deve funcionar mesmo com fichário vazio")
    void exibirCartasDisponiveisComListaVazia() {
        Fichario fichario = new Fichario();

        assertDoesNotThrow(fichario::exibirCartasDisponiveis);
    }

    @Test
    @DisplayName("exibirCartasDisponiveis deve funcionar com cartas cadastradas")
    void exibirCartasDisponiveisComCartas() {
        Fichario fichario = new Fichario();
        Carta carta = Carta.criarCarta("Exodia", 10, 1000, 1000);

        fichario.adicionarCarta(carta);

        assertDoesNotThrow(fichario::exibirCartasDisponiveis);
    }

    @Test
    @DisplayName("getCartas deve refletir corretamente o estado interno do fichário")
    void getCartasDeveRefletirEstadoAtual() {
        Fichario fichario = new Fichario();
        Carta carta = Carta.criarCarta("Kuriboh", 1, 300, 200);

        fichario.adicionarCarta(carta);

        List<Carta> cartas = fichario.getCartas();

        assertEquals(1, cartas.size());
        assertSame(carta, cartas.get(0));
    }
}
