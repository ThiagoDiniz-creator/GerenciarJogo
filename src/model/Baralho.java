package model;

import java.util.*;

public class Baralho {
    private List<Carta> monte = new ArrayList<>();
    private ListIterator<Carta> monteIterator;


    public Baralho() {
        this.construirBaralho();
        this.monteIterator = this.monte.listIterator();
    }

    public int cartasRemanescentes() {
        if (this.monte.size() > 0) {
            int qtdCartas = this.monteIterator.nextIndex();
            return qtdCartas - this.monte.size();
        } else {
            return 0;
        }
    }

    public void construirBaralho() {
        if (this.monte.size() == 0) {
            for (Naipe naipe : Naipe.values()) {
                for (Valor valor : Valor.values()) {
                    Carta carta = new Carta();
                    carta.setNaipe(naipe);
                    carta.setValor(valor);
                    this.monte.add(carta);
                }
            }
        }
    }

    public void embaralhar() {
        if (this.monte.size() > 0) {
            Collections.shuffle(this.monte);
            this.monteIterator = this.monte.listIterator();
        }
    }

    public List<Carta> distribuirCartas(int qtd) {
        if (this.monte.size() > 0) {
            int cartasRemanescentes = this.cartasRemanescentes();
            if (qtd > cartasRemanescentes) {
                this.embaralhar();
            }
            List<Carta> cartaList = new ArrayList<>();
            for (int i = 0; i < qtd; i++) {
                cartaList.add(this.monteIterator.next());
            }
            return cartaList;
        } else {
            return new ArrayList<>();
        }
    }

}
