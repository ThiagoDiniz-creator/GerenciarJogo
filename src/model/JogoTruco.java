package model;

import java.util.List;

public class JogoTruco extends JogoBaralho {

    private RodadaTruco rodadaTruco;
    private boolean aberto = true;
    private List<JogadorBaralho> timeA;
    private List<JogadorBaralho> timeB;
    private int tentosTimeA = 0;
    private int tentosTimeB = 0;


    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public void updateGameState() {
        if (tentosTimeA >= 12) {
            this.setTimeVencedor(1);
            this.setAberto(false);
        } else if (tentosTimeB >= 12) {
            this.setTimeVencedor(2);
            this.setAberto(false);
        }
    }

    public List<JogadorBaralho> getTimeA() {
        return timeA;
    }

    public void setTimeA(List<JogadorBaralho> timeA) {
        this.timeA = timeA;
    }

    public int getTentosTimeA() {
        return tentosTimeA;
    }

    public void incrementTentosTime1(int tentos) {
        this.tentosTimeA += tentos;
    }

    public List<JogadorBaralho> getTimeB() {
        return timeB;
    }

    public void setTimeB(List<JogadorBaralho> timeB) {
        this.timeB = timeB;
    }

    public int getTentosTimeB() {
        return tentosTimeB;
    }

    public void incrementTentosTime2(int tentos) {
        this.tentosTimeB += tentos;
    }

    public Carta compararCartas(Carta carta1, Carta carta2) {
        int manilha = this.rodadaTruco.getVira().getValor().ordinal() + 1;
        if (carta1.getValor().ordinal() == manilha) {
            if (carta2.getValor().ordinal() == manilha) {
                return carta1.getNaipe().ordinal() > carta2.getNaipe().ordinal() ? carta1 : carta2;
            }
            return carta1;
        } else if (carta2.getValor().ordinal() == manilha) {
            return carta2;
        } else {
            if (carta1.getValor().ordinal() == carta2.getValor().ordinal())
                return new Carta();
            return carta1.getValor().ordinal() > carta2.getValor().ordinal() ? carta1 : carta2;
        }
    }

    public void registrarTimes(List<JogadorBaralho> jogadorTrucoList1, List<JogadorBaralho> jogadorTrucoList2) {
        this.timeA = jogadorTrucoList1;
        this.timeB = jogadorTrucoList2;
    }

    public int obterTimeDoJogador(JogadorBaralho jogadorBaralho) {
        if (this.timeA.contains(jogadorBaralho))
            return 1;
        else if (this.timeB.contains(jogadorBaralho))
            return 2;
        else
            return 0;
    }

    public RodadaTruco getRodadaTruco() {
        return rodadaTruco;
    }

    public void setRodadaTruco(RodadaTruco rodadaTruco) {
        this.rodadaTruco = rodadaTruco;
    }

    public Carta iniciarRodada() {
        Carta vira = this.getBaralho().distribuirCartas(1).get(0);
        rodadaTruco.setVira(vira);
        for (JogadorBaralho jogadorBaralho : this.timeA) {
            jogadorBaralho.setCartas(this.getBaralho().distribuirCartas(3));
        }
        for (JogadorBaralho jogadorBaralho : this.timeB) {
            jogadorBaralho.setCartas(this.getBaralho().distribuirCartas(3));
        }
        return vira;
    }
}
