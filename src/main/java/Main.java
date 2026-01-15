void main(String[] args) {
    Jogador jogador = new Jogador("Yugi Muto");
    Computador computador = new Computador("Seto Kaiba");
    Jogo jogo = new Jogo(jogador, computador);
    jogo.iniciar();
}
