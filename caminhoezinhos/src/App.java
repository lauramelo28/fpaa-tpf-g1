import Algoritimos.ProgramacaoDinamica;

import java.util.ArrayList;
import java.util.List;

import Algoritimos.GeradorDeProblemas;

public class App {

    static ProgramacaoDinamica programacaoDinamica = new ProgramacaoDinamica();
    static GeradorDeProblemas geradorDeProblemas = new GeradorDeProblemas();
    public static void main(String[] args) throws Exception {
        
        // valor teste para verificar retorno
        //int[] valor = {12, 13, 32, 33};
        List<int[]> retorno = geradorDeProblemas.geracaoDeRotas(10, 1, 5);
        List<int[]> listaFinal = new ArrayList<>(3);
        //int[] valor = retorno.get(0);
        int[] valor = {35, 34, 33, 23, 21, 32, 35, 19, 26, 42};

        //int[] valores = programacaoDinamica.tabelaLimite(valor);
        //for (int i : valores) {
        //    System.out.println("valor "+i);
        //}

        // pegando limites
        int[] limites = programacaoDinamica.tabelaLimite(valor);

        for(int caminhao=3; caminhao != 0; caminhao--){
            
            int[] rotas = new int[4];
            rotas = programacaoDinamica.tabelaDinamica(valor, limites);
            listaFinal.add(rotas);

            System.out.println("");
            System.out.println("=====================================================");        
            System.out.println("");
    
            for (int i : rotas) {
                System.out.println("rotas "+i);
            }
            
            valor = programacaoDinamica.removerRotas(valor, rotas);
        }

        listaFinal.stream().forEach(System.out::print);
    }

}
