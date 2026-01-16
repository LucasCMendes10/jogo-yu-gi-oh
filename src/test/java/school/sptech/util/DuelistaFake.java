package school.sptech.util;

import school.sptech.Duelista;

import java.util.List;

public class DuelistaFake extends Duelista {

    public DuelistaFake(String nome) {
        super(nome);
    }

    @Override
    protected void comprarCarta() {

    }

    @Override
    protected void jogarCarta(List<Integer> posicoesCartasEscolhidas) {

    }

    @Override
    protected void atacar(Duelista oponente) {

    }
}
