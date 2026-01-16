package school.sptech;

import static java.util.Objects.isNull;

public class Carta {
    private String nome;
    private Integer qtdEstrela;
    private Integer ataque;
    private Integer defesa;

    public Carta(String nome, Integer qtdEstrela, Integer ataque, Integer defesa) {
        this.nome = nome;
        this.qtdEstrela = qtdEstrela;
        this.ataque = ataque;
        this.defesa = defesa;
    }

    public static Carta criarCarta(String nome, Integer qtdEstrela, Integer ataque, Integer defesa) {
        if (validacoesNullPointer(nome, qtdEstrela, ataque, defesa)) {
            throw new NullPointerException();
        } else if (validacoesIllegalArgument(nome, qtdEstrela, ataque, defesa)) {
            throw new IllegalArgumentException();
        }

        return new Carta(nome, qtdEstrela, ataque, defesa);
    }

    private static Boolean validacoesNullPointer(String nome, Integer qtdEstrela, Integer ataque, Integer defesa) {
        return isNull(nome) || isNull(qtdEstrela) || isNull(ataque) || isNull(defesa);
    }

    private static Boolean validacoesIllegalArgument(String nome, Integer qtdEstrela, Integer ataque, Integer defesa) {
        return nome.isBlank() || qtdEstrela <= 0 || qtdEstrela > 12 || ataque < 0 || defesa < 0;
    }

    public String getNome() {
        return nome;
    }

    public Integer getQtdEstrela() {
        return qtdEstrela;
    }

    public Integer getAtaque() {
        return ataque;
    }

    public Integer getDefesa() {
        return defesa;
    }

    @Override
    public String toString() {
        return "school.sptech.Carta{" +
                "nome='" + nome +
                ", qtdEstrela=" + qtdEstrela +
                ", ataque=" + ataque +
                ", defesa=" + defesa +
                '}';
    }
}
