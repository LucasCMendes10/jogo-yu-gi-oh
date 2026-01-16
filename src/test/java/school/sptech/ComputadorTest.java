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
        return Carta.criarCarta("Dragão Branco de Olhos Azuis", 8, 3000, 2500);
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
    @DisplayName("Montar baralho deve adicionar exatamente o tamanho informado pelo jogador")
    void montarBaralhoCorretamente() throws InterruptedException {
        Computador cpu = new Computador("CPU");

        List<Carta> cartas = new ArrayList<>(List.of(
                cartaFraca(), cartaMedia(), cartaForte()
        ));

        cpu.montarBaralho(cartas, 2);

        assertEquals(2, cpu.getBaralho().size());
    }

    @Test
    @DisplayName("Comprar carta deve mover carta do baralho para a mão")
    void comprarCartaComSucesso() throws InterruptedException {
        Computador cpu = new Computador("CPU");
        cpu.getBaralho().add(cartaFraca());

        cpu.comprarCarta();

        assertEquals(0, cpu.getBaralho().size());
        assertEquals(1, cpu.getMao().size());
    }

    @Test
    @DisplayName("Comprar carta com baralho vazio não deve alterar estado")
    void comprarCartaSemBaralho() throws InterruptedException {
        Computador cpu = new Computador("CPU");

        cpu.comprarCarta();

        assertTrue(cpu.getMao().isEmpty());
    }

    @Test
    @DisplayName("Jogada simples deve mover carta da mão para o campo")
    void jogarCartaSimples() throws InterruptedException {
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
    @DisplayName("Não deve travar se todas as posições possíveis de serem sorteadas já tiverem sido escolhidas")
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
    void deveInvocarComUmSacrificio() throws InterruptedException {
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
    void deveInvocarComDoisSacrificios() throws InterruptedException {
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
    void jogadaEspecialComSucesso() throws InterruptedException {
        Computador cpu = new Computador("CPU");
        Carta alvo = cartaMedia();

        cpu.getMao().add(cartaFraca());
        cpu.getMao().add(alvo);

        Carta resultado = cpu.jogadaEspecial(c -> c.getAtaque() > 2000);

        assertEquals(alvo, resultado);
    }

    @Test
    @DisplayName("Jogada especial deve retornar null se nenhuma carta atender condição")
    void jogadaEspecialSemResultado() throws InterruptedException {
        Computador cpu = new Computador("CPU");
        cpu.getMao().add(cartaFraca());

        Carta resultado = cpu.jogadaEspecial(c -> c.getAtaque() > 2000);

        assertNull(resultado);
    }

    @Test
    @DisplayName("jogarCarta com ataque direto deve reduzir pontos de vida do oponente")
    void ataqueDiretoFunciona() throws InterruptedException {
        Computador cpu = new Computador("CPU");
        Computador alvo = new Computador("ALVO");

        Carta atacante = cartaMedia();
        cpu.getCampo().add(atacante);

        cpu.atacar(alvo);

        assertEquals(1500, alvo.getPontosDeVida());
    }

    @Test
    @DisplayName("jogarCarta(Carta) sem sacrifício deve invocar corretamente")
    void jogarCartaDiretaSemSacrificio() throws InterruptedException {
        Computador cpu = new Computador("CPU");
        Carta carta = cartaFraca();

        cpu.getMao().add(carta);

        cpu.jogarCarta(carta);

        assertTrue(cpu.getMao().isEmpty());
        assertEquals(1, cpu.getCampo().size());
    }

    @Test
    @DisplayName("jogarCarta(Carta) com sacrifício deve invocar corretamente")
    void jogarCartaDiretaComSacrificio() throws InterruptedException {
        Computador cpu = new Computador("CPU");

        Carta sacrificio = cartaFraca();
        Carta invocada = cartaMedia();

        cpu.getCampo().add(cartaFraca());
        cpu.getCampo().add(sacrificio);
        cpu.getMao().add(invocada);

        cpu.jogarCarta(invocada);

        assertEquals(1, cpu.getCampo().size());
        assertTrue(cpu.getCampo().contains(invocada));
    }

    @Test
    @DisplayName("Ataque contra carta defensora deve remover carta derrotada")
    void ataqueComDefensor() throws InterruptedException {
        Computador atacante = new Computador("ATK");
        Computador defensor = new Computador("DEF");

        Carta cartaAtacante = cartaForte();
        Carta cartaDefensora = cartaFraca();

        atacante.getCampo().add(cartaAtacante);
        defensor.getCampo().add(cartaDefensora);

        atacante.atacar(defensor);

        assertTrue(defensor.getCampo().isEmpty(),
                "Carta defensora deveria ser destruída");
    }

    @Test
    @DisplayName("executarTurno deve comprar carta quando mão e campo estão vazios")
    void executarTurnoComTudoVazio() throws InterruptedException {
        Computador cpu = new Computador("CPU");
        Computador oponente = new Computador("OP");

        cpu.getBaralho().add(cartaFraca());

        cpu.executarTurno(oponente);

        assertEquals(1, cpu.getMao().size());
    }

    @Test
    @DisplayName("executarTurno deve atacar quando oponente não tem campo")
    void executarTurnoAtacaDireto() throws InterruptedException {
        Computador cpu = new Computador("CPU");
        Computador alvo = new Computador("ALVO");

        cpu.getCampo().add(cartaMedia());

        cpu.executarTurno(alvo);

        assertTrue(alvo.getPontosDeVida() < 4000,
                "Oponente deveria sofrer dano direto");
    }
}
