# 🌌 Exploração Espacial

Este projeto simula um cenário em que a humanidade, diante da escassez de recursos na Terra, precisa explorar novos planetas em busca de recursos essenciais.  
A implementação foi feita em **Java**, seguindo boas práticas de orientação a objetos e utilizando **JUnit** para testes unitários.

---

## ⚙️ Estrutura do Projeto

### 🔹 Recursos
Cada recurso possui **valor** e **peso** fixos (não podem ser alterados após criação):

- Água (valor: 180, peso: 10)
- Oxigênio (valor: 300, peso: 2)
- Silício (valor: 60, peso: 16)
- Ouro (valor: 120, peso: 25)
- Ferro (valor: 30, peso: 32)

---

### 🔹 Planeta
Um planeta possui:
- `posição` (int, fixa)
- `recursos` (lista imutável)

Métodos principais:
- **Valor total** = soma dos valores dos recursos.
- **Valor por peso** = soma de (valor ÷ peso) de cada recurso.

---

### 🔹 Nave
A nave inicia na posição `0` e recebe uma quantidade de combustível.  
Regras de movimentação:
- Cada posição percorrida consome **3L de combustível**.
- Possui um atributo `posição`.
- Métodos principais:
  - `getQuantidadeDeCombustivel()`
  - `explorar(Planeta)` ou `explorar(List<Planeta>)`

A exploração:
1. A nave viaja até o planeta (se houver combustível suficiente).
2. Coleta os recursos do planeta.
3. Continua para o próximo planeta da lista.
4. Quando terminar, tenta **retornar à posição inicial** (se houver combustível suficiente).
5. Caso falte combustível, a nave fica **à deriva** na última posição possível.

---

## 🧪 Testes Unitários

Os testes foram implementados com **JUnit**.  
Os seguintes testes obrigatórios foram incluídos:

- `deveFicarADerivaQuandoFaltarCombustivelParaIrAteUmPlaneta`
- `deveTerValorTotalZeradoQuandoNaoExistirNenhumRecurso`
- `deveTerValorTotalQuandoExistirRecursosNoPlaneta`
- `deveTerValorPorPesoZeradoQuandoNaoExistirNenhumRecurso`
- `deveTerValorPorPesoQuandoExistirRecursosNoPlaneta`

### ✅ Exemplo de Teste
```java
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
