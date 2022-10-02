package model;

public class JogoBaralho {
    private Baralho baralho;
    private int rodada = 1;
    private int timeVencedor = 0;

    public Baralho getBaralho() {
        return baralho;
    }

    public void setBaralho(Baralho baralho) {
        this.baralho = baralho;
    }


    public int getRodada() {
        return rodada;
    }

    public void incrementRodada() {
        this.rodada += 1;
    }

    public int getTimeVencedor() {
        return timeVencedor;
    }

    public void setTimeVencedor(int timeVencedor) {
        this.timeVencedor = timeVencedor;
    }
}
