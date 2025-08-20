import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Nave {
    private int combustivel;
    private int posicao;

    public Nave(int combustivel) {
        this.combustivel = combustivel;
        this.posicao = 0;
    }

    public int getQuantidadeDeCombustivel() {
        return combustivel;
    }

    public int getPosicao() {
        return posicao;
    }

    private boolean podeViajar(int destino) {
        return combustivel >= Math.abs(destino - posicao) * 3;
    }

    public List<Recurso> explorar(Planeta planeta) {
        List<Planeta> lista = new ArrayList<>();
        lista.add(planeta);
        return explorar(lista, Comparator.comparingInt(Planeta::getPosicao));
    }

    public List<Recurso> explorar(List<Planeta> planetas, Comparator<Planeta> prioridade) {
        List<Recurso> recursosColetados = new ArrayList<>();
        planetas.sort(prioridade);

        for (Planeta planeta : planetas) {
            int custoIda = Math.abs(planeta.getPosicao() - posicao) * 3;
            int custoVolta = planeta.getPosicao() * 3;

            if (combustivel >= (custoIda + custoVolta)) {
                combustivel -= custoIda;
                posicao = planeta.getPosicao();
                recursosColetados.addAll(planeta.getRecursos());
            } else if (combustivel >= custoIda) {
                combustivel -= custoIda;
                posicao = planeta.getPosicao();
                break;
            } else {
                int maxDistancia = combustivel / 3;
                posicao += (planeta.getPosicao() > posicao) ? maxDistancia : -maxDistancia;
                combustivel %= 3;
                break;
            }
        }

        int custoRetorno = posicao * 3;
        if (combustivel >= custoRetorno) {
            combustivel -= custoRetorno;
            posicao = 0;
        } else {
            int maxRetorno = combustivel / 3;
            posicao -= maxRetorno;
            combustivel %= 3;
        }

        return recursosColetados;
    }
}
