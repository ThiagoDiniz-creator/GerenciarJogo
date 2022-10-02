package model;

public class Rodada {
    protected int vencedor;
    private int valor = 1;
    private int turnoAtual = 0;

    public int getVencedor() {
        return vencedor;
    }

    public void setVencedor(int vencedor) {
        this.vencedor = vencedor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getTurnoAtual() {
        return turnoAtual;
    }

    public void setTurnoAtual(int turnoAtual) {
        this.turnoAtual = turnoAtual;
    }

    public void incrementTurnoAtual() {
        this.turnoAtual += 1;
    }

    public void incrementTurnoAtual(int valor) {
        this.turnoAtual += valor;
    }
}
