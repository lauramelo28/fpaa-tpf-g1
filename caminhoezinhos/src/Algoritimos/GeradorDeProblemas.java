package Algoritimos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorDeProblemas {
    static Random aleatorio = new Random(42);
    static final int TAM_BASE = 13;

    /**
     * Gerador de rotas aleatórias para testes de FPAA. Recebe como parâmetros a
     * quantidade de rotas para um teste,
     * o tamanho do conjunto de testes (ou seja, quantos conjuntos de rotas daquele
     * tamanho serão gerados) e a dispersão.
     * A dispersão deve ser dada em porcentagem e indica a diferença possível entre
     * a menor rota e a maior rota. Supõe-se
     * que conjuntos com dispersões diferentes possam gerar resultados com
     * características muito diferentes. Este método
     * não está robusto contra nenhum tipo de erro de valores dos parâmetros.
     * 
     * @param quantRotas  A quantidade de rotas que será utilizada em um teste. Deve
     *                    ser um número inteiro positivo preferencialmente maior que
     *                    5.
     * @param tamConjunto O tamanho do conjunto de testes, isto é, quantos
     *                    conjuntos de rotas do tamanho acima serão gerados.
     * @param dispersao   A dispersão do tamanho das rotas em %. Por exemplo, se a
     *                    dipersão for 0.50 (50%), as rotas geradas estarão
     *                    entre 13 e 20. Uma dispersão de 1.0 (100%) gera conjuntos
     *                    de rotas entre 13
     *                    e 26.
     * @return Retorna uma lista de conjuntos de rotas. Cada conjunto de rotas é um
     *         vetor de números inteiros.
     */
    public static List<int[]> geracaoDeRotas(int quantRotas, int tamConjunto, double dispersao) {
        List<int[]> conjuntoDeTeste = new ArrayList<>(tamConjunto);
        int tam_max = (int) Math.ceil(TAM_BASE * (1 + dispersao));
        for (int i = 0; i < tamConjunto; i++) {
            int[] rotas = new int[quantRotas];
            for (int j = 0; j < quantRotas; j++) {
                //rotas[j] = aleatorio.nextInt(TAM_BASE, tam_max);
                rotas[j] = aleatorio.nextInt(tam_max);

            }
            conjuntoDeTeste.add(rotas);
        }
        return conjuntoDeTeste;
    }
}

