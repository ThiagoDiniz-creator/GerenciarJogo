package model;

import java.util.Random;

public class Jogador {
    private int id;
    private String nome;
    private boolean isCPU = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isCPU() {
        return isCPU;
    }

    public void setCPU(boolean CPU) {
        isCPU = CPU;
    }

    public boolean tomarDecisao(int min) {
        return (new Random()).nextBoolean();
    }
}
