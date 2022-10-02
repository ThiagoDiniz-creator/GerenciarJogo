package model;

public class RodadaTruco extends Rodada {
    private int[] turnos = {-1, -1, -1};
    private Carta vira;
    private boolean emDisputa = true;

    @Override
    public void setVencedor(int vencedor) {
        this.vencedor = vencedor;
        this.emDisputa = false;
    }

    public int[] getTurnos() {
        return turnos;
    }

    public void setTurnos(int[] turnos) {
        this.turnos = turnos;
    }

    public Carta getVira() {
        return vira;
    }

    public void setVira(Carta vira) {
        this.vira = vira;
    }

    public boolean isEmDisputa() {
        return emDisputa;
    }

    public void setEmDisputa(boolean emDisputa) {
        this.emDisputa = emDisputa;
    }

    public void incrementValorDaRodada() {
        if (this.getValor() == 1) {
            this.setValor(3);
        } else
            this.setValor(this.getValor() + 3);
    }

    public void finishTurno(int timeVencedor) {
        if (timeVencedor == 1) {
            turnos[this.getTurnoAtual()] = 1;
        } else if (timeVencedor == 2) {
            turnos[this.getTurnoAtual()] = 2;
        } else {
            turnos[this.getTurnoAtual()] = 0;
        }
        if (this.getTurnoAtual() >= 1) {
            verificarResultado();
        }
        this.incrementTurnoAtual();
    }

    public void verificarResultado() {
        if (this.turnos[0] == 1 && this.turnos[1] == 1 ||
                this.turnos[0] == 1 && this.turnos[2] == 1 ||
                this.turnos[1] == 1 && this.turnos[2] == 1)
            this.setVencedor(1);
        else if (this.turnos[0] == 2 && this.turnos[1] == 2 ||
                this.turnos[0] == 2 && this.turnos[2] == 2 ||
                this.turnos[1] == 2 && this.turnos[2] == 2)
            this.setVencedor(2);
        else if (this.turnos[0] == 0 && this.turnos[1] == 0 && this.turnos[2] == 0)
            this.setVencedor(0);
        else if (this.turnos[2] == 0 && this.turnos[0] != 0)
            this.setVencedor(this.turnos[0]);
        else if (this.turnos[0] == 0 && this.turnos[1] == 0 && this.turnos[2] != -1)
            this.setVencedor(this.turnos[2]);
        else if (this.turnos[0] == 0 && this.turnos[1] != -1)
            this.setVencedor(this.turnos[1]);
        else if (this.turnos[0] != 0 && this.turnos[1] == 0) {
            this.setVencedor(this.turnos[0]);
        }
    }

    public void reciclar() {
        this.emDisputa = true;
        this.turnos = new int[]{-1, -1, -1};
        this.vencedor = 0;
        this.setTurnoAtual(0);
        this.setValor(1);
    }

    public void encerrarComTruco(int timeVencedor) {
        this.setVencedor(timeVencedor);
    }
}
