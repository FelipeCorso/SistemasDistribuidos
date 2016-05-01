/** HelloClient.java **/
package Estoque;

import java.rmi.Naming;
import java.util.ArrayList;

public class EstoqueClient {
   public static void main(String[] args) {
      try {
    	 ArrayList<Produto> listaProduto = new ArrayList<Produto>();
    	 Produto produto = new Produto();
    	 produto.setCodigoProduto(1);
    	 produto.setDescricaoProduto("teste");
    	 produto.setQtdProduto(2);
    	 		    	 
    	 listaProduto.add(produto);
         Estoque obj = (Estoque)Naming.lookup("//localhost/Estoque"); 
         System.out.println("Mensagem do Servidor: " + obj.receberProduto(listaProduto)); 
      } catch (Exception ex) {
         System.out.println("Exception: " + ex.getMessage());
      } 
   }
}
