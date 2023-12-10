package Algoritimos;
import java.util.Arrays;

public class Backtracking {

    private int[] melhorDistribuicao; // Melhor distribuição encontrada
    private int minDiferenca = Integer.MAX_VALUE; // Menor diferença encontrada

    public int[] distribuirRotas(int[] rotas, int numCaminhoes) {
        // Ordena as rotas para potencialmente melhorar o desempenho do algoritmo
        Arrays.sort(rotas);

        int[] distribuicaoAtual = new int[rotas.length];
        distribuirRotasUtil(rotas, numCaminhoes, 0, distribuicaoAtual);
        return melhorDistribuicao;
    }

    private void distribuirRotasUtil(int[] rotas, int numCaminhoes, int indiceAtual, int[] distribuicaoAtual) {
        if (indiceAtual == rotas.length) {
            // Chegou ao final, verifica a diferença de quilometragem
            int[] somaPorCaminhao = new int[numCaminhoes];
            for (int i = 0; i < rotas.length; i++) {
                somaPorCaminhao[distribuicaoAtual[i]] += rotas[i];
            }

            // Encontrar a diferença entre a maior e a menor soma por caminhão
            int maxSoma = Arrays.stream(somaPorCaminhao).max().getAsInt();
            int minSoma = Arrays.stream(somaPorCaminhao).min().getAsInt();
            int diferenca = maxSoma - minSoma;

            // Atualiza a melhor distribuição se a diferença for menor ou igual
            if (diferenca <= minDiferenca) {
                minDiferenca = diferenca;
                melhorDistribuicao = Arrays.copyOf(distribuicaoAtual, distribuicaoAtual.length);
            }
            return;
        }

        // Tenta atribuir a rota atual a cada caminhão
        for (int i = 0; i < numCaminhoes; i++) {
            distribuicaoAtual[indiceAtual] = i;
            distribuirRotasUtil(rotas, numCaminhoes, indiceAtual + 1, distribuicaoAtual);
        }
    }
}
