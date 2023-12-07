package Algoritimos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProgramacaoDinamica {
    
    List<int[]> conjuntoDeTeste = GeradorDeProblemas.geracaoDeRotas(10, 2, 4);

    public static List<int[]> tabelaDinamica(List<int[]> conjuntoDeTeste){

        List<int[]> ordemCaminhoes;

        int[][] tabela = new int[3][10];
        int[] limites;

        //return ordemCaminhoes;
        return null;

    }

    public int[] tabelaLimite(int[] limitesOriginal){
        int[] limitesFinal;
        int minimo = 0, maximo = 0, soma = 0;
        ArrayList<Integer> limiteNovos = new ArrayList<>();

        for(int i =0; i<limitesOriginal.length; i++){
            limiteNovos.add(limitesOriginal[i]);
        }

        Collections.sort(limiteNovos);
        soma = limiteNovos.stream().mapToInt(Integer::intValue).sum();

        // limite máximo de valor por caminhão, mais 10% para valor máximo
        maximo = (int) ((soma/3)*1.1);

        // valor mínimo dps do zero que um caminhão pode ter
        minimo = limiteNovos.get(0);

        // criando vetor deacordo com tamanho dos limites
        limitesFinal = new int[(maximo-minimo)+2];
        limitesFinal[0] = 0;
        limitesFinal[1] = limiteNovos.get(0);
        
        // posicao do vetor
        int posicao = 2;

        // preenche o valores entre o mínimo até o máximo
        for(int i = minimo+1; i <= maximo; i++) {
            limitesFinal[posicao] = i;
            posicao++;
        }

        return limitesFinal;

    }


}
