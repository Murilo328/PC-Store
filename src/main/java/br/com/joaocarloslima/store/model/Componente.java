package br.com.joaocarloslima.store.model;

/**
 * Classe base (abstrata) de todos os componentes do computador.
 * Reúne os atributos comuns: id, nome, consumo (W) e preço (R$).
 */
public abstract class Componente {

    private Long id;
    private String nome;
    private int consumo;
    private double preco;

    protected Componente(Long id, String nome, int consumo, double preco) {
        this.id = id;
        this.nome = nome;
        this.consumo = consumo;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
