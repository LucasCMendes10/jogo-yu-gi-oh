package school.sptech;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Carta")
class CartaTeste {

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
}
