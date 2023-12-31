import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Algoritimos.Backtracking;
import Algoritimos.DivisaoConquista;
import Algoritimos.GeradorDeProblemas;
import Algoritimos.ProgramacaoDinamica;

import java.util.Scanner;
import java.util.stream.Collectors;

public class App {

    static Scanner teclado = new Scanner(System.in);
    private static List<int[]> conjuntosDeRotasParaTestes = new ArrayList<>();
    private static List<int[]> conjuntosDeRotasComMaiorTamanho = new ArrayList<>();

    static ProgramacaoDinamica programacaoDinamica = new ProgramacaoDinamica();
    static GeradorDeProblemas geradorDeProblemas = new GeradorDeProblemas();

    public static void main(String[] args) {
        int opcao;
      
        do {
            opcao = menu();
            limparTela();
            switch (opcao) {
                case 1:
                    rodarBacktracking();
                    break;
                case 2:
                    rodarBacktrackingElementosLivres();
                    break;
                case 3:
                    if(conjuntosDeRotasComMaiorTamanho.isEmpty()){
                        System.out.println("Executar primeiramente o item 1 para obter o conjunto para testes");
                        break;
                    }

                    rodarDivisaoConquistaConjuntoElementosPreDefinidos();
                    break;
                case 4:
                    rodarDivisaoConquistaElementosLivres();
                    break;
                case 5:
                    if(conjuntosDeRotasComMaiorTamanho.isEmpty()){
                        System.out.println("Executar primeiramente o item 1 para obter o conjunto para testes");
                        break;
                    }

                    rodarProgramacaoDinamicaConjuntoElementosPreDefinidos();
                    System.out.println("PD ");
                    break;
                case 6:
                    rodarProgramacaoDinamicaElementosLivres();
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
            System.out.println("5 - Rodar Programacao Dinamica com os mesmos conjuntos de tamanho T do backtracking");
            System.out.println("6 - Rodar Programacao Dinamica com número de elementos a escolha");
            System.out.println("0 - Sair");
            System.out.println("==================================================================================");
            System.out.print("\nDigite sua opção: ");
            try {
                opcao = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opcao invalida.");
                continue;
            }
        }while(!(opcao>=0  && opcao <=6));
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

    private static void rodarBacktrackingElementosLivres() {
        System.out.println("Digite o numero de caminhoes: ");
        int numCaminhoes = Integer.parseInt(teclado.nextLine());

        List<int[]> conjuntosDeRotas = gerarElementosAleatorios();      

        Backtracking backtracking = new Backtracking();

        double tempoTotal = 0;
        double tempoMedioExecucao = 0;
    
        for(int[] rota : conjuntosDeRotas) {
            long startTime = System.nanoTime();
            
            int[] resultado = backtracking.distribuirRotas(rota, numCaminhoes);
            long endTime = System.nanoTime();

            long tempoExecucao = endTime - startTime;

            System.out.println("--------------------------------------------");
            System.out.print("Conjunto: ");
            System.out.println(Arrays.toString(rota));

            for (int j = 0; j < numCaminhoes; j++) {
                System.out.println("-" + obterRotasPorCaminhao(resultado, j, rota));
            }
        
            System.out.println("Tempo de execução: " + tempoExecucao/1000000.0 + " ms");
            tempoTotal += tempoExecucao;
        }

        tempoMedioExecucao = tempoTotal / conjuntosDeRotas.size();
        double tempoExecucaoEmMilissegundos = tempoMedioExecucao/1000000.0;

        System.out.println("*********************************************");
        System.out.println("Tempo médio de execução: " + tempoExecucaoEmMilissegundos + " ms");
    }    
    
    private static void rodarBacktracking() {

        Backtracking backtracking = new Backtracking();
        int numCaminhoes = 3;
        double numConjuntos = 10;
        double dispersao = 0.5;
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
    }

    private static void rodarDivisaoConquistaConjuntoElementosPreDefinidos(){
        rodarDivisaoConquista(conjuntosDeRotasComMaiorTamanho, 3);
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
        System.out.println("Digite o numero de caminhoes: ");
        
        int numeroCaminhoes = Integer.parseInt(teclado.nextLine());

        List<int[]> rotasAleatorias = gerarElementosAleatorios();

        rodarDivisaoConquista(rotasAleatorias, numeroCaminhoes);
    }    

    private static void rodarDivisaoConquista(List<int[]> rotasAleatorias, int numeroCaminhoes){
        List<Double> temposExecucao = new ArrayList<>();

        System.out.println("Conjunto de rotas: ");

        for(int[] rota : rotasAleatorias) {
            Arrays.sort(rota);
            List<Integer> listaDeRotas = Arrays.stream(rota)
                .boxed()
                .collect(Collectors.toList());

            int distanciaTotalRota = listaDeRotas.stream().mapToInt(Integer::intValue).sum(); 
            double tolerancia = distanciaTotalRota * 0.08;
            double mediaDesejada = ((distanciaTotalRota + tolerancia) / numeroCaminhoes);            

            long inicioExecucao = System.nanoTime();

            List<List<Integer>> rotasCaminhoes = new ArrayList<>();

            for (int i = 0; i < numeroCaminhoes; i++) {
                rotasCaminhoes.add(new ArrayList<>());
            }

            System.out.println("Rota Aleatória: " + Arrays.toString(rota));

            rotasCaminhoes = DivisaoConquista.encontrarMelhorRotaParaCadaCaminhao(listaDeRotas, rotasCaminhoes, numeroCaminhoes, mediaDesejada);

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
        
        double tempoMedioExecucaoEmMili = somaTempos/rotasAleatorias.size();

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

    private static List<int[]> gerarElementosAleatorios(){
        System.out.println("Digite a quantidade de rotas: ");
        int qtdRotas = Integer.parseInt(teclado.nextLine());

        System.out.println("Digite o tamanho do conjunto: ");
        int tamanhoConjunto = Integer.parseInt(teclado.nextLine());

        System.out.println("Digite a dispersao [Ex: 0.50, 1.0]: ");
        double dispersao = Double.parseDouble(teclado.nextLine());

        return GeradorDeProblemas.geracaoDeRotas(qtdRotas, tamanhoConjunto, dispersao);
    }

     private static void rodarProgramacaoDinamicaElementosLivres(){
        System.out.println("Digite o numero de caminhoes: ");
        
        int numeroCaminhoes = Integer.parseInt(teclado.nextLine());

        //List<int[]> rotasAleatorias = gerarElementosAleatorios();

        List<int[]> rotasAleatorias = new ArrayList<>();
        int[] rotaFixa1 = {40,36,38,29,32,28,31,35,31,30,32,30,29,39,35,38,39,35,32,38,32,33,29,33,29,39,28};
        int[] rotaFixa2 = {32,51,32,43,42,30,42,51,43,51,29,25,27,32,29,55,43,29,32,44,55,29,53,30,24,27};

        rotasAleatorias.add(rotaFixa1);
        rotasAleatorias.add(rotaFixa2);

        rodarProgramacaoDinamica(rotasAleatorias, numeroCaminhoes);
    }

    private static void rodarProgramacaoDinamica(List<int[]> rotasAleatorias, int numeroCaminhoes) {
        //List<int[]> retorno = GeradorDeProblemas.geracaoDeRotas(10, 1, 5);
        //int[] valor = retorno.get(0);
        //List<int[]> listaFinal = new ArrayList<>();
        double tempoTotal = 0;
        double tempoMedioExecucao = 0;

        int numeroRota = 1;
        for (int[] rotasGerada : rotasAleatorias) {
             //int[] rotasGerada = {28, 28, 29, 29, 29, 29, 30, 30, 31, 31, 32, 32, 32, 32, 33, 33, 35, 35, 35, 36, 38, 38, 38, 39, 39, 39, 40};
             //int[] rotasGerada = {24, 25, 27, 27, 29, 29, 29, 29, 30, 30, 32, 32, 32, 32, 42, 42, 43, 43, 43, 44, 51, 51, 51, 53, 55, 55};
            Arrays.sort(rotasGerada);
            System.out.println("======================= Rotas do conjunto "+ (numeroRota) +" ===============================");
            System.out.println("Rotas aleatoria gerada: "+Arrays.toString(rotasGerada));
            List<int[]> listaFinal = new ArrayList<>();
            // pegando limites
            int[] limites = programacaoDinamica.tabelaLimite(rotasGerada, numeroCaminhoes);
            long startTime = System.currentTimeMillis();
            for(int caminhao=numeroCaminhoes; caminhao != 0; caminhao--){
                
                // convertendo de list para array
                List<Integer> listRotas = ProgramacaoDinamica.tabelaDinamica(rotasGerada, limites);
                int[] rotas = new int[listRotas.size()];
                for (int i = 0; i < listRotas.size(); i++) {
                    rotas[i] = listRotas.get(i);
                }
                listaFinal.add(rotas);
                
                rotasGerada = programacaoDinamica.removerRotas(rotasGerada, rotas);
            }
            if(rotasGerada.length > 0){
                listaFinal.sort((r1, r2) -> programacaoDinamica.comparadorRotas(r1,r2));
            }

            listaFinal.size();
            for (int i = 0; i<rotasGerada.length; i++) {
                listaFinal.remove(i);
                int[] novoVetor = adicionaItemVetor(listaFinal.get(i), rotasGerada[i]);
                listaFinal.add(i, novoVetor);
            }

            listaFinal.size();


            listaFinal.stream().forEach(v ->  System.out.println("Rota: " + Arrays.toString(v) + ", Soma das rotas: "+ programacaoDinamica.somaRotas(v)));  
            numeroRota++; 
            long endTime = System.currentTimeMillis();   
            long tempoExecucao = endTime - startTime;
            System.out.println("Tempo de execução: " + tempoExecucao + " ms");
            tempoTotal += tempoExecucao;

        }
        tempoMedioExecucao = tempoTotal / rotasAleatorias.size();

        System.out.println("*********************************************");
        System.out.println("Tempo médio de execução: " + tempoMedioExecucao + " ms");

    }

    private static void rodarProgramacaoDinamicaConjuntoElementosPreDefinidos() {
        rodarProgramacaoDinamica(conjuntosDeRotasComMaiorTamanho, 3);
    }

    public static int[] adicionaItemVetor(int[] vetorExistente, int novoElemento){
        // Criando um novo vetor com tamanho aumentado para adicionar o novo elemento
        int novoTamanho = vetorExistente.length + 1;
        int[] novoVetor = new int[novoTamanho];

        // Copiando os elementos do vetor existente para o novo vetor
        System.arraycopy(vetorExistente, 0, novoVetor, 0, vetorExistente.length);

        // Adicionando o novo elemento ao final do novo vetor
        novoVetor[novoTamanho - 1] = novoElemento;
        
        Arrays.sort(novoVetor);
        return novoVetor;
    }
}
