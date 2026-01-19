package school.sptech;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.sptech.util.DuelistaFake;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Duelista")
class DuelistaTest {

    @Test
    @DisplayName("Classe Duelista deve ser abstrata")
    void classeDuelistaDeveSerAbstrata() {
        assertTrue(
                Modifier.isAbstract(Duelista.class.getModifiers()),
                "A classe Duelista deve ser declarada como abstract"
        );
    }

    @Test
    @DisplayName("Construtor deve inicializar duelista com valores padrões corretos")
    void construtorDeveInicializarValoresPadrao() {
        Duelista duelista = new DuelistaFake("Yugi");

        assertAll(
                () -> assertEquals("Yugi", duelista.getNome()),
                () -> assertEquals(4000, duelista.getPontosDeVida()),
                () -> assertNotNull(duelista.getBaralho()),
                () -> assertNotNull(duelista.getMao()),
                () -> assertNotNull(duelista.getCampo()),
                () -> assertTrue(duelista.getBaralho().isEmpty()),
                () -> assertTrue(duelista.getMao().isEmpty()),
                () -> assertTrue(duelista.getCampo().isEmpty())
        );
    }

    @Test
    @DisplayName("Classe Duelista não deve possuir setters públicos")
    void naoDevePossuirSetters() {
        Method[] methods = Duelista.class.getDeclaredMethods();

        boolean possuiSetter = false;

        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                possuiSetter = true;
                break;
            }
        }

        assertFalse(possuiSetter, "Duelista não deve possuir métodos setters");
    }

    @Test
    @DisplayName("precisaDeSacrificio deve retornar false para cartas com até 4 estrelas")
    void naoPrecisaDeSacrificioParaCartasComPoucasEstrelas() {
        Duelista duelista = new DuelistaFake("Kaiba");
        Carta carta = Carta.criarCarta("Kuriboh", 1, 300, 200);

        assertFalse(duelista.precisaDeSacrifio(carta));
    }

    @Test
    @DisplayName("precisaDeSacrificio deve retornar true para cartas com mais de 4 estrelas")
    void precisaDeSacrificioParaCartasComMuitasEstrelas() {
        Duelista duelista = new DuelistaFake("Kaiba");
        Carta carta = Carta.criarCarta("Dragão Branco de Olhos Azuis", 8, 3000, 2500);

        assertTrue(duelista.precisaDeSacrifio(carta));
    }

    @Test
    @DisplayName("invocarCarta deve mover carta da mão para o campo")
    void invocarCartaDeveMoverCartaDaMaoParaOCampo() throws InterruptedException {
        Duelista duelista = new DuelistaFake("Yugi");
        Carta carta = Carta.criarCarta("Mago Negro", 7, 2500, 2100);

        duelista.getMao().add(carta);

        duelista.invocarCarta(carta);

        assertAll(
                () -> assertFalse(duelista.getMao().contains(carta)),
                () -> assertTrue(duelista.getCampo().contains(carta))
        );
    }

    @Test
    @DisplayName("invocarCarta com sacrifícios deve remover sacrifícios e invocar o monstro")
    void invocarCartaComSacrificios() throws InterruptedException {
        Duelista duelista = new DuelistaFake("Yugi");

        Carta sacrificio1 = Carta.criarCarta("S1", 4, 1000, 1000);
        Carta sacrificio2 = Carta.criarCarta("S2", 4, 1000, 1000);
        Carta monstro = Carta.criarCarta("Exodia", 10, 1000, 1000);

        duelista.getCampo().addAll(List.of(sacrificio1, sacrificio2));
        duelista.getMao().add(monstro);

        duelista.invocarCarta(monstro, 2, List.of(sacrificio1, sacrificio2));

        assertAll(
                () -> assertFalse(duelista.getCampo().contains(sacrificio1)),
                () -> assertFalse(duelista.getCampo().contains(sacrificio2)),
                () -> assertTrue(duelista.getCampo().contains(monstro))
        );
    }

    @Test
    @DisplayName("receberAtaque direto deve reduzir pontos de vida corretamente")
    void receberAtaqueDireto() throws InterruptedException {
        Duelista duelista = new DuelistaFake("Joey");
        Carta atacante = Carta.criarCarta("Dragão Negro de Olhos Vermelhos", 5, 1500, 1200);

        duelista.receberAtaque(atacante);

        assertEquals(4000 - 1500, duelista.getPontosDeVida());
    }

    @Test
    @DisplayName("receberAtaque com defesa menor deve destruir defensora e causar dano")
    void ataqueComDefesaMenor() throws InterruptedException {
        Duelista defensor = new DuelistaFake("Yugi");
        Duelista atacante = new DuelistaFake("Kaiba");

        Carta atacanteCarta = Carta.criarCarta("Dragão", 5, 2000, 1500);
        Carta defensora = Carta.criarCarta("Soldado", 4, 1000, 1500);

        defensor.getCampo().add(defensora);

        defensor.receberAtaque(atacanteCarta, defensora, atacante);

        assertFalse(defensor.getCampo().contains(defensora));
        assertEquals(4000 - 500, defensor.getPontosDeVida());
    }

    @Test
    @DisplayName("Quando ataque for menor que a defesa, atacante deve perder pontos de vida")
    void ataqueMenorQueDefesa() throws InterruptedException {
        Duelista defensor = new DuelistaFake("Yugi");
        Duelista atacante = new DuelistaFake("Kaiba");

        Carta cartaAtacante = Carta.criarCarta("Dragão Fraco", 4, 1000, 800);
        Carta cartaDefensora = Carta.criarCarta("Guardião", 4, 1200, 2000);

        defensor.getCampo().add(cartaDefensora);

        defensor.receberAtaque(cartaAtacante, cartaDefensora, atacante);

        assertAll(
                () -> assertTrue(defensor.getCampo().contains(cartaDefensora),
                        "Carta defensora deve permanecer no campo"),
                () -> assertEquals(4000, defensor.getPontosDeVida(),
                        "Defensor não deve perder pontos de vida"),
                () -> assertEquals(4000 - (2000 - 1000), atacante.getPontosDeVida(),
                        "Atacante deve perder a diferença entre defesa e ataque")
        );
    }

    @Test
    @DisplayName("Quando ataque for igual à defesa, ambas as cartas devem ser destruídas sem perda de vida")
    void ataqueIgualADefesa() throws InterruptedException {
        Duelista defensor = new DuelistaFake("Yugi");
        Duelista atacante = new DuelistaFake("Kaiba");

        Carta cartaAtacante = Carta.criarCarta("Espadas Iguais", 4, 1500, 1500);
        Carta cartaDefensora = Carta.criarCarta("Escudo Igual", 4, 1500, 1500);

        defensor.getCampo().add(cartaDefensora);
        atacante.getCampo().add(cartaAtacante);

        defensor.receberAtaque(cartaAtacante, cartaDefensora, atacante);

        assertAll(
                () -> assertFalse(defensor.getCampo().contains(cartaDefensora),
                        "Carta defensora deve ser destruída"),
                () -> assertFalse(atacante.getCampo().contains(cartaAtacante),
                        "Carta atacante deve ser destruída"),
                () -> assertEquals(4000, defensor.getPontosDeVida(),
                        "Defensor não deve perder pontos de vida"),
                () -> assertEquals(4000, atacante.getPontosDeVida(),
                        "Atacante não deve perder pontos de vida")
        );
    }

    @Test
    @DisplayName("toString deve conter informações principais do duelista")
    void toStringDeveConterInformacoes() {
        Duelista duelista = new DuelistaFake("Yugi");

        String resultado = duelista.toString();

        assertAll(
                () -> assertTrue(resultado.contains("Yugi")),
                () -> assertTrue(resultado.contains("pontosDeVida")),
                () -> assertTrue(resultado.contains("baralho")),
                () -> assertTrue(resultado.contains("mao")),
                () -> assertTrue(resultado.contains("campo"))
        );
    }
}
