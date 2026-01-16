package school.sptech;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Carta")
class CartaTest {

    @Test
    @DisplayName("Construtor deve criar carta corretamente com todos os atributos informados")
    void construtorDeveCriarCartaComValoresCorretos() {
        Carta carta = new Carta("Dragão Branco de Olhos Azuis", 8, 3000, 2500);

        assertEquals("Dragão Branco de Olhos Azuis", carta.getNome());
        assertEquals(8, carta.getQtdEstrela());
        assertEquals(3000, carta.getAtaque());
        assertEquals(2500, carta.getDefesa());
    }

    @Test
    @DisplayName("Getters devem retornar exatamente os valores passados no construtor")
    void gettersDevemRetornarValoresCorretos() {
        String nome = "Mago Negro";
        Integer estrelas = 7;
        Integer ataque = 2500;
        Integer defesa = 2100;

        Carta carta = new Carta(nome, estrelas, ataque, defesa);

        assertAll(
                () -> assertEquals(nome, carta.getNome()),
                () -> assertEquals(estrelas, carta.getQtdEstrela()),
                () -> assertEquals(ataque, carta.getAtaque()),
                () -> assertEquals(defesa, carta.getDefesa())
        );
    }

    @Test
    @DisplayName("Classe Carta deve possuir apenas getters públicos, sem setters")
    void naoDevePossuirSetters() {
        Method[] methods = Carta.class.getDeclaredMethods();

        boolean possuiSetter = false;

        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                possuiSetter = true;
                break;
            }
        }

        assertFalse(possuiSetter, "A classe Carta não deve possuir métodos setters");
    }

    @Test
    @DisplayName("toString deve conter todas as informações da carta")
    void toStringDeveConterTodosOsAtributos() {
        Carta carta = new Carta("Exodia", 10, 1000, 1000);

        String resultado = carta.toString();

        assertAll(
                () -> assertTrue(resultado.contains("Exodia")),
                () -> assertTrue(resultado.contains("10")),
                () -> assertTrue(resultado.contains("1000")),
                () -> assertTrue(resultado.contains("defesa"))
        );
    }

    @Test
    @DisplayName("criarCarta deve criar uma Carta válida quando todos os dados estiverem corretos")
    void criarCartaComDadosValidos() {
        Carta carta = Carta.criarCarta(
                "Dragão Negro de Olhos Vermelhos",
                7,
                2400,
                2000
        );

        assertAll(
                () -> assertNotNull(carta),
                () -> assertEquals("Dragão Negro de Olhos Vermelhos", carta.getNome()),
                () -> assertEquals(7, carta.getQtdEstrela()),
                () -> assertEquals(2400, carta.getAtaque()),
                () -> assertEquals(2000, carta.getDefesa())
        );
    }

    @Test
    @DisplayName("criarCarta deve lançar exceções adequadas quando regras de validação forem violadas")
    void criarCartaComDadosInvalidosDeveLancarExcecoes() {
        
        assertThrows(NullPointerException.class,
                () -> Carta.criarCarta(null, 5, 1500, 1500),
                "Nome não pode ser nulo");

        assertThrows(IllegalArgumentException.class,
                () -> Carta.criarCarta("", 5, 1500, 1500),
                "Nome não pode ser vazio");

        assertThrows(IllegalArgumentException.class,
                () -> Carta.criarCarta("   ", 5, 1500, 1500),
                "Nome não pode ser em branco");

        assertThrows(NullPointerException.class,
                () -> Carta.criarCarta("Carta", null, 1500, 1500),
                "Quantidade de estrelas não pode ser nula");

        assertThrows(IllegalArgumentException.class,
                () -> Carta.criarCarta("Carta", 0, 1500, 1500),
                "Quantidade de estrelas deve ser maior que zero");

        assertThrows(IllegalArgumentException.class,
                () -> Carta.criarCarta("Carta", -1, 1500, 1500),
                "Quantidade de estrelas não pode ser negativa");

        assertThrows(IllegalArgumentException.class,
                () -> Carta.criarCarta("Carta", 13, 1500, 1500),
                "Quantidade de estrelas não pode ser maior que 12");

        assertThrows(NullPointerException.class,
                () -> Carta.criarCarta("Carta", 5, null, 1500),
                "Ataque não pode ser nulo");

        assertThrows(IllegalArgumentException.class,
                () -> Carta.criarCarta("Carta", 5, 99, 1500),
                "Ataque não pode ser menor que 100");

        assertThrows(NullPointerException.class,
                () -> Carta.criarCarta("Carta", 5, 1500, null),
                "Defesa não pode ser nula");

        assertThrows(IllegalArgumentException.class,
                () -> Carta.criarCarta("Carta", 5, 1500, 50),
                "Defesa não pode ser menor que 100");
    }
}
