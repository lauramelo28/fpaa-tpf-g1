import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import Algoritimos.Backtracking;
import Algoritimos.GeradorDeProblemas;
import java.util.Scanner;


public class App {
    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
      
        do {
            opcao = menu();
            limparTela();
            switch (opcao) {
                case 1:
                    //rodarBacktracking();
                    System.out.println("BT ");
                    break;
                case 2:
                    //rodarDivisaoConquista();
                    System.out.println("DC ");
                    break;
                case 3:
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

    public static int menu() {
        limparTela();
        int opcao=-1;
        do{
            System.out.println("Menu ");
            System.out.println("=================================================");
            System.out.println("1 - Rodar Backtracking");
            System.out.println("2 - Rodar Divisao e Conquista");
            System.out.println("3 - Rodar Programacao Dinamica");
            System.out.println("0 - Sair");
            System.out.println("=================================================");
            System.out.print("\nDigite sua opção: ");
            try {
                opcao = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opcao invalida.");
                continue;
            }
        }while(!(opcao>=0  && opcao <=3));
        return opcao;
    }

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
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