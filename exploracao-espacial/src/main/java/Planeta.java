import java.util.List;

public class Planeta {
    private final int posicao;
    private final List<Recurso> recursos;

    public Planeta(int posicao, List<Recurso> recursos) {
        this.posicao = posicao;
        this.recursos = recursos;
    }

    public int getPosicao() {
        return posicao;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public int getValorTotal() {
        return recursos.stream().mapToInt(Recurso::getValor).sum();
    }

    public int getValorPorPeso() {
        return recursos.stream().mapToInt(r -> r.getValor() / r.getPeso()).sum();
    }
}
