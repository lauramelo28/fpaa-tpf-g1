package Algoritimos;

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
}
