package school.sptech;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Jogador")
class JogadorTest {

    @Test
    @DisplayName("Jogador deve herdar de Duelista")
    void jogadorDeveExtenderDuelista() {
        assertTrue(Duelista.class.isAssignableFrom(Jogador.class));
    }

    @Test
    @DisplayName("Jogador não deve declarar atributos próprios")
    void naoDeveDeclararAtributosProprios() {
        Field[] fields = Jogador.class.getDeclaredFields();
        assertEquals(0, fields.length, "Jogador não deve possuir atributos próprios");
    }

    @Test
    @DisplayName("Jogador não deve possuir getters nem setters")
    void naoDevePossuirGettersNemSetters() {
        Method[] methods = Jogador.class.getDeclaredMethods();

        for (Method method : methods) {
            assertFalse(
                    method.getName().startsWith("get") || method.getName().startsWith("set"),
                    "Jogador não deve possuir getters ou setters"
            );
        }
    }

    @Test
    @DisplayName("Construtor deve inicializar corretamente os atributos herdados")
    void construtorDeveInicializarEstadoCorreto() {
        Jogador jogador = new Jogador("Yugi");

        assertAll(
                () -> assertEquals("Yugi", jogador.getNome()),
                () -> assertEquals(4000, jogador.getPontosDeVida()),
                () -> assertNotNull(jogador.getBaralho()),
                () -> assertNotNull(jogador.getMao()),
                () -> assertNotNull(jogador.getCampo()),
                () -> assertTrue(jogador.getBaralho().isEmpty()),
                () -> assertTrue(jogador.getMao().isEmpty()),
                () -> assertTrue(jogador.getCampo().isEmpty())
        );
    }

    @Test
    @DisplayName("comprarCarta não deve alterar mão quando baralho estiver vazio")
    void comprarCartaComBaralhoVazio() {
        Jogador jogador = new Jogador("Kaiba");

        jogador.comprarCarta();

        assertTrue(jogador.getMao().isEmpty());
    }

    @Test
    @DisplayName("comprarCarta deve mover carta do baralho para a mão")
    void comprarCartaComSucesso() {
        Jogador jogador = new Jogador("Kaiba");
        Carta carta = Carta.criarCarta("Dragão Branco de Olhos Azuis", 8, 3000, 2500);

        jogador.getBaralho().add(carta);

        jogador.comprarCarta();

        assertAll(
                () -> assertEquals(1, jogador.getMao().size()),
                () -> assertTrue(jogador.getBaralho().isEmpty()),
                () -> assertEquals(carta, jogador.getMao().getFirst())
        );
    }

    @Test
    @DisplayName("jogarCarta deve retornar sem erro quando mão estiver vazia")
    void jogarCartaComMaoVazia() {
        Jogador jogador = new Jogador("Yugi");

        assertDoesNotThrow(() -> jogador.jogarCarta(List.of()));
    }

    @Test
    @DisplayName("atacar não deve causar efeito quando campo estiver vazio")
    void atacarComCampoVazio() {
        Jogador atacante = new Jogador("Yugi");
        Jogador defensor = new Jogador("Kaiba");

        atacante.atacar(defensor);

        assertEquals(4000, defensor.getPontosDeVida());
    }

    @Test
    @DisplayName("exibirMao não deve alterar estado quando mão estiver vazia")
    void exibirMaoComMaoVazia() {
        Jogador jogador = new Jogador("Yugi");

        jogador.exibirMao();

        assertTrue(jogador.getMao().isEmpty());
    }

    @Test
    @DisplayName("exibirMao não deve alterar estado quando mão possuir cartas")
    void exibirMaoComCartas() {
        Jogador jogador = new Jogador("Yugi");
        jogador.getMao().add(Carta.criarCarta("Kuriboh", 1, 300, 200));

        jogador.exibirMao();

        assertEquals(1, jogador.getMao().size());
    }
}
