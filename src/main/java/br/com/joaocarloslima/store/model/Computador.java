package br.com.joaocarloslima.store.model;

/**
 * Computador montado por COMPOSIÇÃO: ele "tem" uma placa-mãe, uma CPU,
 * uma RAM e uma fonte (qualquer um pode estar ausente enquanto a montagem não termina).
 */
public class Computador {

    private PlacaMae placaMae;
    private Cpu cpu;
    private Ram ram;
    private Fonte fonte;

    public PlacaMae getPlacaMae() {
        return placaMae;
    }

    public void setPlacaMae(PlacaMae placaMae) {
        this.placaMae = placaMae;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Ram getRam() {
        return ram;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public Fonte getFonte() {
        return fonte;
    }

    public void setFonte(Fonte fonte) {
        this.fonte = fonte;
    }

    /**
     * Status da montagem. As verificações seguem esta ordem de prioridade:
     * 1) faltam componentes, 2) CPU x placa-mãe, 3) RAM x placa-mãe, 4) fonte.
     */
    public String status() {
        if (placaMae == null || cpu == null || ram == null || fonte == null) {
            return "Computador incompleto";
        }
        if (!placaMae.compativel(cpu)) {
            return "Incompatibilidade entre CPU e Placa Mãe";
        }
        if (!placaMae.compativel(ram)) {
            return "Incompatibilidade entre RAM e Placa Mãe";
        }
        if (consumoTotal() > fonte.getPotencia()) {
            return "Fonte insuficiente para o sistema";
        }
        return "Computador completo e funcionando";
    }

    /**
     * Soma o preço dos componentes já escolhidos (os ausentes contam como zero,
     * pois a página mostra o total parcial durante a montagem).
     */
    public double precoTotal() {
        double total = 0;
        if (placaMae != null) total += placaMae.getPreco();
        if (cpu != null) total += cpu.getPreco();
        if (ram != null) total += ram.getPreco();
        if (fonte != null) total += fonte.getPreco();
        return total;
    }

    /**
     * Consumo que a fonte precisa suprir: CPU + placa-mãe + RAM.
     * A própria fonte não entra na soma, pois a potência dela é o que ela entrega aos demais.
     */
    private int consumoTotal() {
        return cpu.getConsumo() + placaMae.getConsumo() + ram.getConsumo();
    }
}
