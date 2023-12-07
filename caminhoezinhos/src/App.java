import Algoritimos.ProgramacaoDinamica;

public class App {
    public static void main(String[] args) throws Exception {
        
        // valor teste para verificar retorno
        int[] valor = {12, 13, 32, 33};
        ProgramacaoDinamica programacaoDinamica = new ProgramacaoDinamica();
        int[] valores = programacaoDinamica.tabelaLimite(valor);
        for (int i : valores) {
            System.out.println("valor "+i);
        }

    }
}
