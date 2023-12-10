import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Algoritimos.Backtracking;
import Algoritimos.DivisaoConquista;
import Algoritimos.GeradorDeProblemas;
import java.util.Scanner;


public class App {
    static Scanner teclado = new Scanner(System.in);
    private static List<int[]> conjuntosDeRotasParaTestes = new ArrayList<>();
    private static List<int[]> conjuntosDeRotasComMaiorTamanho = new ArrayList<>();

    public static void main(String[] args) {
        int opcao;
      
        do {
            opcao = menu();
            limparTela();
            switch (opcao) {
                case 1:
                    rodarBacktracking();
                    System.out.println("Rodar Backtracking até atingir limite de 30 segundos iniciando com 6 rotas ");
                    break;
                case 2:
                    System.out.println("Rodar Backtracking com número de elementos a escolha ");
                    break;
                case 3:
                    //rodarProgramacaoDinamica();
                    System.out.println("Rodar Divisao e Conquista com os mesmos conjuntos de tamanho T do backtracking ");
                    break;
                case 4:
                    rodarDivisaoConquistaElementosLivres();
                    System.out.println("Rodar Divisao e Conquista com número de elementos a escolha ");
                    break;
                case 5:
                    //rodarProgramacaoDinamica();
                    System.out.println("PD ");
                    break;
                default:
                    break;
            }
            pausa();
        } while (opcao != 0);
        System.out.println("Obrigado por utilizar nosso sistema! Ate breve :)");
    }

    private static int menu() {
        limparTela();
        int opcao=-1;
        do{
            System.out.println("Menu ");
            System.out.println("==================================================================================");
            System.out.println("1 - Rodar Backtracking até atingir limite de 30 segundos iniciando com 6 rotas");
            System.out.println("2 - Rodar Backtracking com número de elementos a escolha");
            System.out.println("3 - Rodar Divisao e Conquista com os mesmos conjuntos de tamanho T do backtracking");
            System.out.println("4 - Rodar Divisao e Conquista com número de elementos a escolha");
            System.out.println("5 - Rodar Programacao Dinamica");
            System.out.println("0 - Sair");
            System.out.println("=================================================");
            System.out.print("\nDigite sua opção: ");
            try {
                opcao = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opcao invalida.");
                continue;
            }
        }while(!(opcao>=0  && opcao <=5));
        return opcao;
    }

    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void pausa() {
        System.out.println("Enter para continuar.");
        teclado.nextLine();
    }

    private static void rodarBacktracking() {

        Backtracking backtracking = new Backtracking();
        int numCaminhoes = 3;
        double numConjuntos = 10;
        double dispersao = 0.5;
        int totalExecucoes = 0;
        int qtdRotas=6;
        int tamConjunto=10;
        double tempoTotal = 0;
        double tempoMedioExecucao = 0;
        int tempoMaxExecucao = 30000; // 30 segundos

        for (int k = 6; k < numConjuntos; qtdRotas++) {
            List<int[]> conjuntosDeRotas = GeradorDeProblemas.geracaoDeRotas(qtdRotas, tamConjunto, dispersao);
        
            for (int i = 0; i < numConjuntos; i++) {
                long startTime = System.currentTimeMillis();
                int[] rotaAtual = conjuntosDeRotas.get(i);
                
                conjuntosDeRotasParaTestes.add(rotaAtual);

                int[] resultado = backtracking.distribuirRotas(rotaAtual, numCaminhoes);
                long endTime = System.currentTimeMillis();

                long tempoExecucao = endTime - startTime;

                System.out.println("--------------------------------------------");
                System.out.print("Conjunto " + (i + 1) + ":");
                System.out.println(" " + Arrays.toString(rotaAtual));

                for (int j = 0; j < numCaminhoes; j++) {
                    System.out.println("-" + obterRotasPorCaminhao(resultado, j, rotaAtual));
                }
            
                System.out.println("Tempo de execução: " + tempoExecucao + " ms");
                tempoTotal += tempoExecucao;
            }
            tempoMedioExecucao = tempoTotal / numConjuntos;

            System.out.println("*********************************************");
            System.out.println("Tempo médio de execução: " + tempoMedioExecucao + " ms");

            // Imprimir todas as rotas geradas
            System.out.println("\n*********************************************");
            System.out.println("Rotas Geradas:");
            for (int j = 0; j < numConjuntos; j++) {
                System.out.println("Conjunto " + (j + 1) + ": " + Arrays.toString(conjuntosDeRotas.get(j)));
            }

            if(tempoMedioExecucao > tempoMaxExecucao){
                System.out.println("\n------------------------------------------");
                System.out.println("Tempo máximo de execução atingido!");
                System.out.println("Tamanho máximo obtido: " + qtdRotas + " rotas");
                System.out.println("------------------------------------------");
                break;
            }
        }

        obterElementosDeMaiorValorLista();
        var x = 1;
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

    private static void rodarDivisaoConquistaElementosLivres(){
        int numeroCaminhoes = 3;
        int tamanhoConjunto = 2;

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

            DivisaoConquista.encontrarMelhorRotaParaCadaCaminhao(rota, 0, rota.length - 1, rotasCaminhoes);

            long finalExecucao = System.nanoTime();

            for (int i = 0; i < rotasCaminhoes.size(); i++) {
                System.out.println("Caminhão " + (i + 1) + ": " + rotasCaminhoes.get(i) +
                    ", Total Distância: " + DivisaoConquista.calcularSomatorioDeQuilometragensPorRota(rotasCaminhoes.get(i)));
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

    private static List<int[]> obterElementosDeMaiorValorLista(){
        if(conjuntosDeRotasParaTestes.isEmpty()){
            return null;
        }

        int[] maiorConjunto = conjuntosDeRotasParaTestes.get(0);

        for (int[] conjuntoAtual : conjuntosDeRotasParaTestes) {
            if (conjuntoAtual.length > maiorConjunto.length) {
                maiorConjunto = conjuntoAtual;
            }
        }

        conjuntosDeRotasComMaiorTamanho.add(maiorConjunto);

        for (int[] conjuntoAtual : conjuntosDeRotasParaTestes) {
            if (conjuntoAtual.length == maiorConjunto.length) {
                conjuntosDeRotasComMaiorTamanho.add(conjuntoAtual);
            }
        }
        return conjuntosDeRotasComMaiorTamanho;
    }
}