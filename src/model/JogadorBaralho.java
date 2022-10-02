package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JogadorBaralho extends Jogador {

    private List<Carta> cartas = new ArrayList<>();

    public JogadorBaralho() {
    }

    public JogadorBaralho(int id, String nome) {
        this.setId(id);
        this.setNome(nome);
    }

    public JogadorBaralho(int id, String nome, boolean isCPU) {
        this.setId(id);
        this.setNome(nome);
        this.setCPU(isCPU);
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }

    public void exibirCartas() {
        for (Carta carta : this.cartas) {
            System.out.println(carta);
        }
    }

    public void exibirCartas(int numInicial) {
        for (Carta carta : this.cartas) {
            System.out.println(numInicial + " - " + carta);
            numInicial++;
        }
    }

    public Carta jogarCarta(int index) {
        Carta carta = this.cartas.get(index);
        this.cartas.remove(index);
        return carta;
    }

    public void exibirCartasLinha() {
        for (Carta carta : this.cartas) {
            System.out.print(" -- " + carta + " -- ");
        }
        System.out.println();
    }

    public void jogarCarta(Carta carta) {
        this.cartas.remove(carta);
    }

    public int somarCartas() {
        int total = 0;
        for (Carta carta : this.cartas) {
            total += carta.getValor().ordinal() + 1;
        }
        return total;
    }

    public Carta jogarCartaAleatoria() {
        int index = new Random().nextInt(this.cartas.size());
        Carta carta = this.getCartas().get(index);
        this.getCartas().remove(index);
        return carta;
    }

    @Override
    public boolean tomarDecisao(int min) {
        return this.somarCartas() >= min;
    }
}
