import school.sptech.Computador;
import school.sptech.Jogador;
import school.sptech.Jogo;

void main(String[] args) throws InterruptedException {
    Jogador jogador = new Jogador("Yugi Muto");
    Computador computador = new Computador("Seto Kaiba");
    Jogo jogo = new Jogo(jogador, computador);
    jogo.iniciar();
}
