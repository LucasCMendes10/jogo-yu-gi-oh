package school.sptech;

import java.util.ArrayList;
import java.util.List;

public class Fichario {
    private List<Carta> cartas;

    public Fichario() {
        this.cartas = new ArrayList<>();
    }

    public void adicionarCarta(Carta novaCarta) {
        cartas.add(novaCarta);
    }

    public void adicionarCartas(Carta... novasCartas) {
        for (Carta novasCarta : novasCartas) {
            cartas.add(novasCarta);
        }
    }

    public void removerCarta(Carta carta) {
        cartas.remove(carta);
    }

    public void removerCartas(Carta... novasCartas) {
        for (Carta novasCarta : novasCartas) {
            cartas.remove(novasCarta);
        }
    }

    public void exibirCartasDisponiveis() {
        IO.println("Cartas disponíveis para montar o baralho:");
        for (int i = 0; i < cartas.size(); i++) {
            IO.println((i + 1) + ": " + cartas.get(i).toString());
        }
    }

    public List<Carta> getCartas() {
        return cartas;
    }
}
