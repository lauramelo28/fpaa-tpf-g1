package Algoritimos;

import java.util.ArrayList;
import java.util.List;

public class DivisaoConquista {

    private static List<Integer> maiorDistanciaMedia = new ArrayList<>();
    private static List<Integer> menorDistanciaMedia = new ArrayList<>();

    public static List<List<Integer>> encontrarMelhorRotaParaCadaCaminhao(List<Integer> listaDeRotas, List<List<Integer>> rotasCaminhoes, int numCaminhoes, double mediaDesejada) {
        
        for (int i = 0; i < numCaminhoes; i++) {
            encontrarMelhorRotaParaCadaCaminhao(listaDeRotas, 0, listaDeRotas.size() - 1, rotasCaminhoes,
                    mediaDesejada);

            int somaElementosListaMaisProximosMedia = menorDistanciaMedia.stream().mapToInt(Integer::intValue).sum();
            int somaElementosListaMaiorDistancia = maiorDistanciaMedia.stream().mapToInt(Integer::intValue).sum();

            int somaElementosMaiorMenorDistancia = somaElementosListaMaisProximosMedia
                    + somaElementosListaMaiorDistancia;

            // caso a soma dos elementos mais longes da média com os mais próximos seja menor ou igual a média adiciona todos ao conjunto de solução
            if (somaElementosMaiorMenorDistancia <= mediaDesejada) {
                rotasCaminhoes.get(i).addAll(menorDistanciaMedia);
                rotasCaminhoes.get(i).addAll(maiorDistanciaMedia);
            } else {
                rotasCaminhoes.get(i).addAll(menorDistanciaMedia);
            }

            for (int elemento : rotasCaminhoes.get(i)) {
                listaDeRotas.remove(Integer.valueOf(elemento));
            }

            maiorDistanciaMedia.clear();
            menorDistanciaMedia.clear();
        }

        if (listaDeRotas.size() != 0) {
            rotasCaminhoes.get(numCaminhoes - 1).addAll(listaDeRotas);
        }

        return rotasCaminhoes;
    }

    private static void encontrarMelhorRotaParaCadaCaminhao(List<Integer> quilometragens, int inicio, int fim,
            List<List<Integer>> rotasCaminhoes, double mediaDesejada) {

        if (inicio > fim) {
            return;
        }

        // Caso base quando existem 2 rotas a serem preenchidas
        if (fim - inicio <= 1) {
            if (inicio == fim) {
                encontrarMelhorLocalMenorLocalUmElemento(quilometragens.get(inicio), mediaDesejada);
            } else {
                encontrarMelhorLocalMenorLocalDoisElementos(quilometragens.get(inicio), quilometragens.get(fim),
                        mediaDesejada);
            }
            return;
        }

        int meio = inicio + (fim - inicio) / 2;
        encontrarMelhorRotaParaCadaCaminhao(quilometragens, inicio, meio, rotasCaminhoes, mediaDesejada); // Primeirametade das rotas
        encontrarMelhorRotaParaCadaCaminhao(quilometragens, meio + 1, fim, rotasCaminhoes, mediaDesejada); // Segunda metade dasrotas
    }

    private static void encontrarMelhorLocalMenorLocalDoisElementos(Integer quilometragemInicio,
            Integer quilometragemFim, double mediaDesejada) {
                
        double diferencaInicioMedia = Math.abs(mediaDesejada - quilometragemInicio);
        double diferencaFimMedia = Math.abs(mediaDesejada - quilometragemFim);
        double diferencaInicioFimMedia = Math.abs(mediaDesejada - (quilometragemInicio + quilometragemFim));

        if (maiorDistanciaMedia.size() == 0 && menorDistanciaMedia.size() == 0) {
            if (diferencaInicioFimMedia > 0) {
                menorDistanciaMedia.add(quilometragemFim);
                menorDistanciaMedia.add(quilometragemInicio);
            } else if (diferencaInicioMedia < diferencaFimMedia) {
                menorDistanciaMedia.add(quilometragemInicio);
            } else {
                menorDistanciaMedia.add(quilometragemFim);
            }
        } else {
            int somaElementosListaMaisProximosMedia = menorDistanciaMedia.stream().mapToInt(Integer::intValue).sum();
            double diferencaListaMaisProximosMedia = Math.abs(mediaDesejada - somaElementosListaMaisProximosMedia);

            int somaElementosListaMaiorDistancia = maiorDistanciaMedia.stream().mapToInt(Integer::intValue).sum();

            double diferencaListaMaisDistantesMedia = maiorDistanciaMedia.size() > 0 ? Math.abs(mediaDesejada - somaElementosListaMaiorDistancia) : 0;

            if ((somaElementosListaMaisProximosMedia + quilometragemInicio + quilometragemFim) <= mediaDesejada) {
                menorDistanciaMedia.add(quilometragemFim);
                menorDistanciaMedia.add(quilometragemInicio);
            } else if((quilometragemInicio + quilometragemFim + somaElementosListaMaisProximosMedia) <= mediaDesejada){
                menorDistanciaMedia.add(quilometragemFim);
                menorDistanciaMedia.add(quilometragemInicio);
            } else if (diferencaInicioFimMedia > 0 && diferencaInicioFimMedia < diferencaListaMaisProximosMedia) {
                menorDistanciaMedia.clear();
                menorDistanciaMedia.add(quilometragemFim);
                menorDistanciaMedia.add(quilometragemInicio);
            } else if((quilometragemInicio + somaElementosListaMaisProximosMedia) <= mediaDesejada){
                menorDistanciaMedia.add(quilometragemInicio);
            } else if (diferencaInicioMedia < diferencaListaMaisProximosMedia && diferencaInicioMedia < diferencaFimMedia) {
                menorDistanciaMedia.clear();
                menorDistanciaMedia.add(quilometragemInicio);
            } else if ((quilometragemFim + somaElementosListaMaisProximosMedia) <= mediaDesejada){
                menorDistanciaMedia.add(quilometragemFim);
            } else if (diferencaFimMedia < diferencaListaMaisProximosMedia && diferencaFimMedia < diferencaInicioMedia) {
                menorDistanciaMedia.clear();
                menorDistanciaMedia.add(quilometragemFim);
            } else if ((quilometragemInicio + somaElementosListaMaiorDistancia) <= mediaDesejada) {
                maiorDistanciaMedia.add(quilometragemInicio);
            } else if (diferencaInicioMedia > diferencaFimMedia && diferencaInicioMedia > diferencaListaMaisDistantesMedia) {
                maiorDistanciaMedia.clear();
                maiorDistanciaMedia.add(quilometragemInicio);
            } else if ((quilometragemFim + somaElementosListaMaiorDistancia) <= mediaDesejada) {
                maiorDistanciaMedia.add(quilometragemFim);
            } else if (diferencaFimMedia > diferencaInicioMedia && diferencaFimMedia > diferencaListaMaisDistantesMedia) {
                maiorDistanciaMedia.clear();
                maiorDistanciaMedia.add(quilometragemFim);
            }
        }
    }

    private static void encontrarMelhorLocalMenorLocalUmElemento(Integer quilometragemAtual, double mediaDesejada) {
        double diferencaElementoAtualDaMedia = Math.abs(mediaDesejada - quilometragemAtual);

        int somaElementosListaMaisProximosMedia = menorDistanciaMedia.stream().mapToInt(Integer::intValue).sum();
        int somaElementosListaMaiorDistancia = maiorDistanciaMedia.stream().mapToInt(Integer::intValue).sum();

        double diferencaListaMaisProximosMedia = Math.abs(mediaDesejada - somaElementosListaMaisProximosMedia);
        double diferencaListaMaisDistantesMedia = Math.abs(mediaDesejada - somaElementosListaMaiorDistancia);

        if((quilometragemAtual + somaElementosListaMaisProximosMedia) <= mediaDesejada){
            menorDistanciaMedia.add(quilometragemAtual);
        }
        else if (diferencaElementoAtualDaMedia < diferencaListaMaisProximosMedia) {
            menorDistanciaMedia.clear();
            menorDistanciaMedia.add(quilometragemAtual);
        }
        else if((quilometragemAtual + somaElementosListaMaiorDistancia) <= mediaDesejada){
            maiorDistanciaMedia.add(quilometragemAtual);
        }
        else if (diferencaElementoAtualDaMedia > diferencaListaMaisDistantesMedia) {
            maiorDistanciaMedia.clear();
            maiorDistanciaMedia.add(quilometragemAtual);
        }
    }

    // Calcula o somatório de distancias que existem no conjunto de rotas do
    // caminhão sendo passado como parâmetro
    public static int calcularSomatorioDeQuilometragensPorRota(List<Integer> rota) {
        int quilometragemTotal = 0;

        for (int i = 0; i < rota.size(); i++) {
            int quilometragem = rota.get(i);
            quilometragemTotal += quilometragem;
        }

        return quilometragemTotal;
    }
}
