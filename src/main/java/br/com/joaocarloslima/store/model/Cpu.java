package br.com.joaocarloslima.store.model;

public class Cpu extends Componente {

    private Socket socket;

    public Cpu(Long id, String nome, int consumo, double preco, Socket socket) {
        super(id, nome, consumo, preco);
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
