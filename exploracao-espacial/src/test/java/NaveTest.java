import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class NaveTest {

    @Test
    public void deveFicarADerivaQuandoFaltarCombustivelParaIrAteUmPlaneta() {
        int posicaoEsperada = 3;
        int combustivelEsperado = 1;
        Nave milleniumFalcon = new Nave(10);
        Planeta tatooine = new Planeta(4, new ArrayList<>());

        List<Recurso> recursos = milleniumFalcon.explorar(tatooine);
        int posicaoResultante = milleniumFalcon.getPosicao();
        int combustivelFinal = milleniumFalcon.getQuantidadeDeCombustivel();

        Assert.assertTrue(recursos.isEmpty());
        Assert.assertEquals(combustivelEsperado, combustivelFinal);
        Assert.assertEquals(posicaoEsperada, posicaoResultante);
    }

    @Test
    public void deveTerValorTotalZeradoQuandoNaoExistirNenhumRecurso() {
        Planeta planeta = new Planeta(1, new ArrayList<>());
        Assert.assertEquals(0, planeta.getValorTotal());
    }

    @Test
    public void deveTerValorTotalQuandoExistirRecursosNoPlaneta() {
        Planeta planeta = new Planeta(1, Arrays.asList(
                new Recurso("Água", 180, 10),
                new Recurso("Ouro", 120, 25)
        ));
        Assert.assertEquals(300, planeta.getValorTotal());
    }

    @Test
    public void deveTerValorPorPesoZeradoQuandoNaoExistirNenhumRecurso() {
        Planeta planeta = new Planeta(2, new ArrayList<>());
        Assert.assertEquals(0, planeta.getValorPorPeso());
    }

    @Test
    public void deveTerValorPorPesoQuandoExistirRecursosNoPlaneta() {
        Planeta planeta = new Planeta(2, Arrays.asList(
                new Recurso("Água", 180, 10),
                new Recurso("Oxigênio", 300, 2)
        ));
        Assert.assertEquals((180 / 10) + (300 / 2), planeta.getValorPorPeso());
    }

    @Test
    public void deveExplorarUmPlanetaComRecursosEVoltarComSucesso() {
        Nave nave = new Nave(30); // 5 posições ida e volta = 30
        Recurso agua = new Recurso("Água", 180, 10);
        Planeta planeta = new Planeta(5, Arrays.asList(agua));

        List<Recurso> recursos = nave.explorar(planeta);

        Assert.assertEquals(0, nave.getPosicao()); // voltou à base
        Assert.assertEquals(0, nave.getQuantidadeDeCombustivel()); // combustível exato
        Assert.assertEquals(1, recursos.size());
        Assert.assertEquals("Água", recursos.get(0).getNome());
    }

    @Test
    public void deveExplorarMultiplosPlanetasComPrioridadePorValorTotal() {
        Nave nave = new Nave(90); // suficiente para 6 posições ida/volta

        Planeta p1 = new Planeta(2, Arrays.asList(new Recurso("Ferro", 30, 32)));
        Planeta p2 = new Planeta(4, Arrays.asList(new Recurso("Ouro", 120, 25)));
        Planeta p3 = new Planeta(6, Arrays.asList(new Recurso("Oxigênio", 300, 2)));

        List<Planeta> planetas = Arrays.asList(p1, p2, p3);
        List<Recurso> recursos = nave.explorar(planetas,
                Comparator.comparingInt(Planeta::getValorTotal).reversed());

        Assert.assertEquals(0, nave.getPosicao());
        Assert.assertEquals(3, recursos.size());
        Assert.assertTrue(recursos.stream().anyMatch(r -> r.getNome().equals("Oxigênio")));
    }

    @Test
    public void deveFicarADerivaAposVisitarPrimeiroPlanetaComPrioridadePorPosicao() {
        Nave nave = new Nave(30);
        Planeta p1 = new Planeta(3, Arrays.asList(new Recurso("Água", 180, 10)));
        Planeta p2 = new Planeta(6, Arrays.asList(new Recurso("Ouro", 120, 25)));

        List<Recurso> recursos = nave.explorar(Arrays.asList(p1, p2), Comparator.comparingInt(Planeta::getPosicao));

        Assert.assertEquals(1, recursos.size());
        Assert.assertEquals("Água", recursos.get(0).getNome());
        Assert.assertTrue(nave.getPosicao() < 6);
    }

    @Test
    public void deveColetarNenhumRecursoSeNaoTiverCombustivel() {
        Nave nave = new Nave(0);
        Planeta planeta = new Planeta(1, Arrays.asList(new Recurso("Oxigênio", 300, 2)));

        List<Recurso> recursos = nave.explorar(planeta);

        Assert.assertTrue(recursos.isEmpty());
        Assert.assertEquals(0, nave.getQuantidadeDeCombustivel());
        Assert.assertEquals(0, nave.getPosicao());
    }

    @Test
    public void deveColetarRecursosDeTodosPlanetasSeCombustivelForSuficiente() {
        Nave nave = new Nave(120);
        Planeta p1 = new Planeta(2, Arrays.asList(new Recurso("Água", 180, 10)));
        Planeta p2 = new Planeta(4, Arrays.asList(new Recurso("Oxigênio", 300, 2)));
        Planeta p3 = new Planeta(6, Arrays.asList(new Recurso("Silício", 60, 16)));

        List<Planeta> planetas = Arrays.asList(p1, p2, p3);
        List<Recurso> recursos = nave.explorar(planetas, Comparator.comparingInt(Planeta::getPosicao));

        Assert.assertEquals(0, nave.getPosicao());
        Assert.assertEquals(3, recursos.size());
        Assert.assertTrue(recursos.stream().anyMatch(r -> r.getNome().equals("Oxigênio")));
    }

    @Test
    public void deveRetornarAoInicioSeCombustivelPermitir() {
        Nave nave = new Nave(18);
        Planeta planeta = new Planeta(3, new ArrayList<>());

        nave.explorar(planeta);
        Assert.assertEquals(0, nave.getPosicao());
        Assert.assertEquals(0, nave.getQuantidadeDeCombustivel());
    }

    @Test
    public void deveExplorarPlanetasComPrioridadePorValorPorPeso() {
        Nave nave = new Nave(100);

        Planeta p1 = new Planeta(2, Arrays.asList(new Recurso("Água", 180, 10))); // 18
        Planeta p2 = new Planeta(4, Arrays.asList(new Recurso("Oxigênio", 300, 2))); // 150
        Planeta p3 = new Planeta(6, Arrays.asList(new Recurso("Silício", 60, 16))); // 3.75

        List<Planeta> planetas = Arrays.asList(p1, p2, p3);
        List<Recurso> recursos = nave.explorar(
                planetas, Comparator.comparingDouble(Planeta::getValorPorPeso).reversed()
        );

        Assert.assertEquals(0, nave.getPosicao());
        Assert.assertEquals(3, recursos.size());
        Assert.assertEquals("Oxigênio", recursos.get(0).getNome());
    }

    @Test
    public void deveExplorarSomentePlanetaComCombustivelParaIdaMasNaoParaVolta() {
        Nave nave = new Nave(12);
        Planeta planeta = new Planeta(2, Arrays.asList(new Recurso("Ferro", 30, 32)));

        List<Recurso> recursos = nave.explorar(planeta);

        Assert.assertEquals(0, nave.getPosicao());
        Assert.assertEquals(0, nave.getQuantidadeDeCombustivel());
        Assert.assertEquals(1, recursos.size());
        Assert.assertEquals("Ferro", recursos.get(0).getNome());
    }

    @Test
    public void deveIrAteOndeConseguirSeNaoTiverCombustivelSuficienteParaIda() {
        Nave nave = new Nave(5);
        Planeta planeta = new Planeta(3, Arrays.asList(new Recurso("Ouro", 120, 25)));

        List<Recurso> recursos = nave.explorar(planeta);

        Assert.assertEquals(1, nave.getPosicao());
        Assert.assertEquals(2, nave.getQuantidadeDeCombustivel());
        Assert.assertTrue(recursos.isEmpty());
    }


}
