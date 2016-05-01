/** HelloWorld.java **/
package Estoque;

import java.rmi.*;
import java.util.ArrayList;

public interface Estoque extends Remote {
   public String receberProduto(ArrayList<Produto> produto) throws RemoteException;
   public boolean produtoExiste(int codigoProduto) throws RemoteException;
   //public void AdicionaProduto(Produto produto);
   //public void IncrementaQtdProduto(Produto produto);
   
}
