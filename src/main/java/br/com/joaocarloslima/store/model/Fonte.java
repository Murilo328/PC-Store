package br.com.joaocarloslima.store.model;

public class Fonte extends Componente {

    private int potencia;

    public Fonte(Long id, String nome, int consumo, double preco, int potencia) {
        super(id, nome, consumo, preco);
        this.potencia = potencia;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }
}
