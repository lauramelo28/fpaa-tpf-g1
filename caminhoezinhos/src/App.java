import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Algoritimos.Backtracking;
import Algoritimos.GeradorDeProblemas;

public class App {

      public static void main(String[] args) {
        Backtracking backtracking = new Backtracking();
        int numCaminhoes = 3;
        int numConjuntos = 10;
        double dispersao = 0.5;
        int totalExecucoes = 0;
        int qtdRotas=6;
        int tamConjunto=10;

        int[] melhorResultado = null;
        int melhorDiferenca = Integer.MAX_VALUE;

        List<int[]> conjuntosDeRotas = GeradorDeProblemas.geracaoDeRotas(qtdRotas, tamConjunto, dispersao);

        for (int i = 0; i < numConjuntos; i++) {
            long startTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis();

            int[] rotaAtual = conjuntosDeRotas.get(i);

            int[] resultado = backtracking.distribuirRotas(rotaAtual, numCaminhoes);
           

            System.out.println("--------------------------------------------");
            System.out.print("Conjunto " + (i + 1) + ":");
            System.out.println(" " + Arrays.toString(rotaAtual));

            for (int j = 0; j < numCaminhoes; j++) {
                System.out.println("Caminhão " + (j + 1) + ": rotas" + obterRotasPorCaminhao(resultado, j, rotaAtual));
            }

            /*int diferenca = calcularDiferenca(resultado, conjuntosDeRotas.get(i), numCaminhoes);
            System.out.println("Diferença total: " + diferenca + " km");
            System.out.println("Tempo de execução: " + (endTime - startTime) + " ms");
            totalExecucoes += (endTime - startTime);

            // Atualiza o melhor resultado se a diferença for menor ou igual
            if (diferenca <= melhorDiferenca) {
                melhorDiferenca = diferenca;
                melhorResultado = Arrays.copyOf(resultado, resultado.length);
            }

            System.out.println("\n************** MELHOR CONJUNTO ENCONTRADO ****************");
            System.out.println("Rota:" + Arrays.toString(conjuntosDeRotas.get(melhorDiferenca)));

            for (int k = 0; k < numCaminhoes; k++) {
                System.out.println("Caminhão " + (k + 1) + ": rotas" + obterRotasPorCaminhao(melhorResultado, k, conjuntosDeRotas.get(melhorDiferenca)));
            }*/
        }

        

        double tempoMedioExecucao = (double) totalExecucoes / numConjuntos;
        System.out.println("Média de tempo de execução: " + tempoMedioExecucao + " ms");
        System.out.println("*********************************************************");

        // Imprimir todas as rotas geradas
        System.out.println("\nRotas Geradas:");
        for (int i = 0; i < numConjuntos; i++) {
            System.out.println("Conjunto " + (i + 1) + ": " + Arrays.toString(conjuntosDeRotas.get(i)));
        }
    }

    private static int calcularDiferenca(int[] distribuicao, int[] rotas, int numCaminhoes) {
        int[] somaPorCaminhao = new int[numCaminhoes];
        for (int i = 0; i < rotas.length; i++) {
            somaPorCaminhao[distribuicao[i]] += rotas[i];
        }

        int maxSoma = Arrays.stream(somaPorCaminhao).max().getAsInt();
        int minSoma = Arrays.stream(somaPorCaminhao).min().getAsInt();

        return maxSoma - minSoma;
    }

    private static String obterRotasPorCaminhao(int[] distribuicao, int caminhao, int[] rotas) {
        StringBuilder rotasCaminhao = new StringBuilder();
        int totalKm = 0;

        rotasCaminhao.append("Caminhão ").append(caminhao + 1).append(": rotas ");
        for (int i = 0; i < distribuicao.length; i++) {
            if (distribuicao[i] == caminhao) {
                rotasCaminhao.append(rotas[i]).append(", ");
                totalKm += rotas[i];
            }
        }

        // Verifica se há rotas antes de tentar ajustar o comprimento da string
        if (rotasCaminhao.length() > 0) {
            rotasCaminhao.setLength(rotasCaminhao.length() - 2); // Remover a última vírgula e espaço
        }

        rotasCaminhao.append(" - total ").append(totalKm).append("km");

        return rotasCaminhao.toString();
    }
}