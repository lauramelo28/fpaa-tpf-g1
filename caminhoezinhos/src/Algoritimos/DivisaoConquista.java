package Algoritimos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DivisaoConquista {
    public static void encontrarMelhorRotaParaCadaCaminhao(int[] quilometragens, int inicio, int fim,
            List<List<Integer>> rotasCaminhoes) {
        if (inicio == fim) { //Caso base, quando só existe 1 rota a ser percorrida
            int indiceCaminhaoComMenorRota = encontrarCaminhaoComMenorQuilometragem(rotasCaminhoes);

            rotasCaminhoes.get(indiceCaminhaoComMenorRota).add(quilometragens[inicio]);
        } else {
            int meio = inicio + (fim - inicio) / 2;
            encontrarMelhorRotaParaCadaCaminhao(quilometragens, inicio, meio, rotasCaminhoes); // Primeira metade das rotas
            encontrarMelhorRotaParaCadaCaminhao(quilometragens, meio + 1, fim, rotasCaminhoes); // Segunda metade das rotas
        }
    }

    // Calcula a distancia total que existe no conjunto para cada caminhão e retorna o com menor distancia para adicionar a nova distancia
    private static int encontrarCaminhaoComMenorQuilometragem(List<List<Integer>> rotasCaminhoes) {
        int indiceMenorQuilometragem = 0;
        int menorQuilometragem = calcularSomatorioDeQuilometragensPorRota(rotasCaminhoes.get(0));

        for (int i = 1; i < rotasCaminhoes.size(); i++) {
            int quilometragemAtual = calcularSomatorioDeQuilometragensPorRota(rotasCaminhoes.get(i));
            if (quilometragemAtual < menorQuilometragem) {
                menorQuilometragem = quilometragemAtual;
                indiceMenorQuilometragem = i;
            }
        }

        return indiceMenorQuilometragem;
    }

    // Calcula o somatório de distancias que existem no conjunto de rotas do caminhão sendo passado como parâmetro
    public static int calcularSomatorioDeQuilometragensPorRota(List<Integer> rota) {
        int quilometragemTotal = 0;

        for (int i = 0; i < rota.size(); i++) {
            int quilometragem = rota.get(i);
            quilometragemTotal += quilometragem;
        }

        return quilometragemTotal;
    }

    public static void main(String[] args) {        
        int numeroCaminhoes = 3;
        int tamanhoConjunto = 10;

        List<Double> temposExecucao = new ArrayList<>();

        List<int[]> rotasAleatorias = GeradorDeProblemas.geracaoDeRotas(18, tamanhoConjunto, 1.0);

        System.out.println("Conjunto de rotas: ");

        for(int[] rota : rotasAleatorias) {
            long inicioExecucao = System.nanoTime();

            List<List<Integer>> rotasCaminhoes = new ArrayList<>();

            for (int i = 0; i < numeroCaminhoes; i++) {
                rotasCaminhoes.add(new ArrayList<>());
            }

            System.out.println("Rota Aleatória: " + Arrays.toString(rota));

            Arrays.sort(rota);

            for(int i = 0; i < rota.length / 2; i++)
            {
                int elementoAtual = rota[i];
                rota[i] = rota[rota.length - i - 1];
                rota[rota.length - i - 1] = elementoAtual;
            }

            encontrarMelhorRotaParaCadaCaminhao(rota, 0, rota.length - 1, rotasCaminhoes);

            long finalExecucao = System.nanoTime();

            for (int i = 0; i < rotasCaminhoes.size(); i++) {
                System.out.println("Caminhão " + (i + 1) + ": " + rotasCaminhoes.get(i) +
                    ", Total Distância: " + calcularSomatorioDeQuilometragensPorRota(rotasCaminhoes.get(i)));
            }

            long tempoExecucaoEmNanoSegundos = (finalExecucao - inicioExecucao);
            double tempoExecucaoEmMilissegundos = tempoExecucaoEmNanoSegundos/1000000.0;

            temposExecucao.add(tempoExecucaoEmMilissegundos);

            System.out.println("Tempo de execução: " + tempoExecucaoEmMilissegundos + " milissegundos");

            
            System.out.println("=======================================");            
        }

        double somaTempos = 0.0;

        for (double tempo : temposExecucao) {
            somaTempos += tempo;
        }

        double tempoMedioExecucaoEmMili = somaTempos/tamanhoConjunto;

        System.out.println("Tempo médio de execução do algoritmo: " + tempoMedioExecucaoEmMili + " milissegundos");
    }
}
