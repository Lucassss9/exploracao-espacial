public class Recurso {
    private final String nome;
    private final int valor;
    private final int peso;

    public Recurso(String nome, int valor, int peso) {
        this.nome = nome;
        this.valor = valor;
        this.peso = peso;
    }

    public int getValor() {
        return valor;
    }

    public int getPeso() {
        return peso;
    }

    public String getNome() {
        return nome;
    }
}
