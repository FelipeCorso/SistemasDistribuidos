/** HelloWorld.java **/
package br.furb.rmi.estoque;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import br.furb.common.Produto;

public interface Estoque extends Remote {
   public String receberProduto(ArrayList<Produto> produto) throws RemoteException;
   public boolean produtoExiste(int codigoProduto) throws RemoteException;
   //public void AdicionaProduto(Produto produto);
   //public void IncrementaQtdProduto(Produto produto);
   
}
