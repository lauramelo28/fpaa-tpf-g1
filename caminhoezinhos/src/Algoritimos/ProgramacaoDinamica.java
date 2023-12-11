package Algoritimos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProgramacaoDinamica {
    
    static ProgramacaoDinamica programacaoDinamica = new ProgramacaoDinamica();

    public static List<Integer> tabelaDinamica(int[] conjuntoDeTeste, int[] limites){

        // definindo tamanho do array por tamanho do array de limites
        int[][] tabela = new int[conjuntoDeTeste.length][limites.length];

        for(int i = 0; i < conjuntoDeTeste.length; i++) {
            for(int j = 0; j < limites.length; j++) {
                if((i == 0 && conjuntoDeTeste[i] > j) || j == 0) {
                    tabela[i][j] = 0;
                } else if(i == 0 && conjuntoDeTeste[i] <= j) {
                    tabela[i][j] = conjuntoDeTeste[i];
                } else if((conjuntoDeTeste[i] <= limites[j]) && (j-conjuntoDeTeste[i]) >= 0 ) {
                    tabela[i][j] = Math.max(tabela[i-1][j], conjuntoDeTeste[i] + tabela[i-1][j-conjuntoDeTeste[i]]);
                } else {
                    tabela[i][j] = tabela[i-1][j];
                }
            }
        }

        // Exibi tabela formata
        // programacaoDinamica.imprimirFunction(tabela, conjuntoDeTeste.length, limites.length);

        List<Integer> ordemCaminhoes = programacaoDinamica.solucaoRotas(tabela, conjuntoDeTeste, (conjuntoDeTeste.length - 1), (limites.length - 1));
        
        return ordemCaminhoes;

    }

    public List<Integer> solucaoRotas(int[][] tabela, int[] conjuntoDeTeste, int linha, int coluna) {
        List<Integer> rotasSolucao = new ArrayList<>();
    
        while (linha >= 0 && coluna > 0) {
            if (linha == 0 && conjuntoDeTeste[linha] == tabela[linha][coluna]) {
                rotasSolucao.add(conjuntoDeTeste[linha]);
                break;
            } else if (linha == 0) {
                break;
            } else if (tabela[linha][coluna] != tabela[linha - 1][coluna]) {
                rotasSolucao.add(conjuntoDeTeste[linha]);
                coluna -= conjuntoDeTeste[linha];
                linha -= 1;
            } else {
                linha -= 1;
            }
        }
    
        return rotasSolucao;
    }

    public int[] tabelaLimite(int[] limitesOriginal, int qtdCaminhoes){
        int[] limitesFinal;
        int maximo = 0, soma = 0;
        ArrayList<Integer> limiteNovos = new ArrayList<>();

        for(int i =0; i<limitesOriginal.length; i++){
            limiteNovos.add(limitesOriginal[i]);
        }

        Collections.sort(limiteNovos);
        soma = limiteNovos.stream().mapToInt(Integer::intValue).sum();

        // limite máximo de valor por caminhão, mais 10% para valor máximo
        maximo = (int) ((soma/qtdCaminhoes)*1.05);

        // criando vetor deacordo com tamanho dos limites
        limitesFinal = new int[maximo];

        // preenche o valores entre o mínimo até o máximo
        for(int i = 0; i < maximo; i++) {
            limitesFinal[i] = i;
        }

        return limitesFinal;

    }

    public void imprimirFunction(int[][] tabela, int linha, int coluna){
        for(int i = 0; i < linha; i++) {
            System.out.println("========================================================== Linha "+ (i+1) +" ====================================================");
            for(int j = 0; j < coluna; j++) {
                System.out.print(tabela[i][j]+ ", ");
            }
            System.out.println("");
        }
    }

    public int[] removerRotas(int[] rotas, int[] rotasUsadas) {
        int[] rotasFinal = new int[rotas.length]; // O tamanho máximo possível é o mesmo que rotas
    
        int index = 0; // Índice para controlar onde adicionar elementos em rotasFinal
    
        // Lógica para remover rotas usadas
        for (int i = 0; i < rotas.length; i++) {
            boolean notValid = false;
    
            for (int rotaUsada : rotasUsadas) {
                if (rotas[i] == rotaUsada) {
                    notValid = true;
                    break; // Sai do loop interno quando uma correspondência é encontrada
                }
            }
    
            if (!notValid) {
                rotasFinal[index] = rotas[i];
                index++; // Incrementa o índice apenas quando uma rota válida é adicionada
            }
        }
    
        // Cria um novo array com o tamanho exato que contém apenas as rotas válidas
        int[] rotasFinaisValidas = new int[index];
        System.arraycopy(rotasFinal, 0, rotasFinaisValidas, 0, index);
    
        return rotasFinaisValidas;
    }

}
