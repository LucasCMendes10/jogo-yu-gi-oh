package school.sptech;

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
