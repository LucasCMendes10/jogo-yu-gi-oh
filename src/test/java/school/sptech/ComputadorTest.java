package school.sptech;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe Computador")
public class ComputadorTest {

    private Carta cartaFraca() {
        return Carta.criarCarta("Kuriboh", 1, 300, 200);
    }

    private Carta cartaMedia() {
        return Carta.criarCarta("Mago Negro", 7, 2500, 2100);
    }

    private Carta cartaForte() {
        return Carta.criarCarta("Dragão Branco", 8, 3000, 2500);
    }

    @Test
    @DisplayName("Computador deve herdar de Duelista")
    void deveHerdarDeDuelista() {
        assertTrue(Duelista.class.isAssignableFrom(Computador.class));
    }

    @Test
    @DisplayName("Computador não deve declarar getters, setters ou atributos próprios")
    void naoDeveDeclararMetodosIndesejados() {
        for (Method method : Computador.class.getDeclaredMethods()) {
            assertFalse(method.getName().startsWith("get"));
            assertFalse(method.getName().startsWith("set"));
        }
    }

    @Test
    @DisplayName("Construtor deve inicializar corretamente o computador")
    void construtorDeveInicializarCorretamente() {
        Computador cpu = new Computador("CPU");

        assertEquals("CPU", cpu.getNome());
        assertEquals(4000, cpu.getPontosDeVida());
        assertTrue(cpu.getBaralho().isEmpty());
        assertTrue(cpu.getMao().isEmpty());
        assertTrue(cpu.getCampo().isEmpty());
    }

    @Test
    @DisplayName("Montar baralho deve adicionar exatamente o tamanho informado")
    void montarBaralhoCorretamente() {
        Computador cpu = new Computador("CPU");

        List<Carta> cartas = new ArrayList<>(List.of(
                cartaFraca(), cartaMedia(), cartaForte()
        ));

        cpu.montarBaralho(cartas, 2);

        assertEquals(2, cpu.getBaralho().size());
    }

    @Test
    @DisplayName("Comprar carta deve mover carta do baralho para a mão")
    void comprarCartaComSucesso() {
        Computador cpu = new Computador("CPU");
        cpu.getBaralho().add(cartaFraca());

        cpu.comprarCarta();

        assertEquals(0, cpu.getBaralho().size());
        assertEquals(1, cpu.getMao().size());
    }

    @Test
    @DisplayName("Comprar carta com baralho vazio não deve alterar estado")
    void comprarCartaSemBaralho() {
        Computador cpu = new Computador("CPU");

        cpu.comprarCarta();

        assertTrue(cpu.getMao().isEmpty());
    }

    @Test
    @DisplayName("Jogada simples deve mover carta da mão para o campo")
    void jogarCartaSimples() {
        Computador cpu = new Computador("CPU");
        cpu.getMao().add(cartaFraca());

        cpu.jogarCarta(new ArrayList<>());

        assertEquals(0, cpu.getMao().size());
        assertEquals(1, cpu.getCampo().size());
    }

    @Test
    @DisplayName("Jogada com sacrifício sem cartas suficientes não deve travar ou ficar em loop")
    void naoDeveTravarAoNaoTerCartasParaSacrificio() {
        Computador cpu = new Computador("CPU");

        cpu.getMao().add(cartaForte());

        assertDoesNotThrow(() ->
                        cpu.jogarCarta(new ArrayList<>()),
                "O método não pode entrar em loop infinito"
        );

        assertTrue(cpu.getCampo().isEmpty(),
                "Carta não deveria ser invocada sem sacrifícios suficientes");
    }

    @Test
    @DisplayName("Não deve jogar carta quando a mão está vazia")
    void naoDeveJogarComMaoVazia() {
        Computador cpu = new Computador("CPU");

        assertDoesNotThrow(() ->
                cpu.jogarCarta(new ArrayList<>())
        );

        assertTrue(cpu.getCampo().isEmpty());
    }

    @Test
    @DisplayName("Não deve travar se posição sorteada já tiver sido escolhida")
    void naoDeveTravarComPosicaoRepetida() {
        Computador cpu = new Computador("CPU");
        cpu.getMao().add(cartaFraca());
        cpu.getMao().add(cartaFraca());

        List<Integer> usadas = new ArrayList<>();
        usadas.add(0);
        usadas.add(1);

        assertDoesNotThrow(() ->
                cpu.jogarCarta(usadas)
        );
    }

    @Test
    @DisplayName("Deve invocar carta que exige 1 sacrifício quando houver cartas suficientes")
    void deveInvocarComUmSacrificio() {
        Computador cpu = new Computador("CPU");

        Carta sacrificio = cartaFraca();
        Carta invocada = Carta.criarCarta("Summoned Skull", 6, 2500, 1200);

        cpu.getMao().add(invocada);
        cpu.getCampo().add(sacrificio);

        cpu.jogarCarta(new ArrayList<>());

        assertEquals(1, cpu.getCampo().size(),
                "A carta invocada deve estar no campo");
    }

    @Test
    @DisplayName("Deve invocar carta que exige 2 sacrifícios quando houver cartas suficientes")
    void deveInvocarComDoisSacrificios() {
        Computador cpu = new Computador("CPU");

        Carta sacrificio1 = cartaFraca();
        Carta sacrificio2 = cartaFraca();
        Carta invocada = cartaForte();

        cpu.getMao().add(invocada);
        cpu.getCampo().add(sacrificio1);
        cpu.getCampo().add(sacrificio2);

        cpu.jogarCarta(new ArrayList<>());

        assertEquals(1, cpu.getCampo().size(),
                "A carta invocada deve substituir os sacrifícios");
    }

    @Test
    @DisplayName("Não deve entrar em loop se só houver cartas já utilizadas")
    void naoDeveEntrarEmLoopComCartasJaUsadas() {
        Computador cpu = new Computador("CPU");

        cpu.getMao().add(cartaFraca());

        List<Integer> usadas = new ArrayList<>();
        usadas.add(0);

        assertDoesNotThrow(() ->
                        cpu.jogarCarta(usadas),
                "Recursão não pode ocorrer sem mudança de estado"
        );
    }

    @Test
    @DisplayName("Jogada especial deve retornar carta que atende condição")
    void jogadaEspecialComSucesso() {
        Computador cpu = new Computador("CPU");
        Carta alvo = cartaMedia();

        cpu.getMao().add(cartaFraca());
        cpu.getMao().add(alvo);

        Carta resultado = cpu.jogadaEspecial(c -> c.getAtaque() > 2000);

        assertEquals(alvo, resultado);
    }

    @Test
    @DisplayName("Jogada especial deve retornar null se nenhuma carta atender condição")
    void jogadaEspecialSemResultado() {
        Computador cpu = new Computador("CPU");
        cpu.getMao().add(cartaFraca());

        Carta resultado = cpu.jogadaEspecial(c -> c.getAtaque() > 2000);

        assertNull(resultado);
    }

    @Test
    @DisplayName("jogarCarta com ataque direto deve reduzir pontos de vida do oponente")
    void ataqueDiretoFunciona() {
        Computador cpu = new Computador("CPU");
        Computador alvo = new Computador("ALVO");

        Carta atacante = cartaMedia();
        cpu.getCampo().add(atacante);

        cpu.atacar(alvo);

        assertEquals(1500, alvo.getPontosDeVida());
    }
}
