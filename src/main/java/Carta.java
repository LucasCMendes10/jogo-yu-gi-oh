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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtdEstrela() {
        return qtdEstrela;
    }

    public void setQtdEstrela(Integer qtdEstrela) {
        this.qtdEstrela = qtdEstrela;
    }

    public Integer getAtaque() {
        return ataque;
    }

    public void setAtaque(Integer ataque) {
        this.ataque = ataque;
    }

    public Integer getDefesa() {
        return defesa;
    }

    public void setDefesa(Integer defesa) {
        this.defesa = defesa;
    }

    @Override
    public String toString() {
        return "Carta{" +
                "nome='" + nome +
                ", qtdEstrela=" + qtdEstrela +
                ", ataque=" + ataque +
                ", defesa=" + defesa +
                '}';
    }
}
