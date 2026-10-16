package br.com.joaocarloslima.store.model;

public class PlacaMae extends Componente {

    private Socket socket;
    private TipoMemoria tipoMemoria;

    public PlacaMae(Long id, String nome, int consumo, double preco, Socket socket, TipoMemoria tipoMemoria) {
        super(id, nome, consumo, preco);
        this.socket = socket;
        this.tipoMemoria = tipoMemoria;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public TipoMemoria getTipoMemoria() {
        return tipoMemoria;
    }

    public void setTipoMemoria(TipoMemoria tipoMemoria) {
        this.tipoMemoria = tipoMemoria;
    }

    /** A CPU é compatível quando usa o mesmo socket da placa-mãe. */
    public boolean compativel(Cpu cpu) {
        return cpu != null && this.socket == cpu.getSocket();
    }

    /** A RAM é compatível quando é do mesmo tipo de memória suportado pela placa-mãe. */
    public boolean compativel(Ram ram) {
        return ram != null && this.tipoMemoria == ram.getTipo();
    }
}
