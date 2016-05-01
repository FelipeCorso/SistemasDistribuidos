/** HelloServer.java **/
package Estoque;

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.rmi.registry.*;

public class EstoqueServer extends UnicastRemoteObject implements Estoque {
	public EstoqueServer() throws RemoteException {
		super();
	}

	// main()
	public static void main(String[] args) {
		try {
			EstoqueServer obj = new EstoqueServer();
			Naming.rebind("//localhost/Estoque", obj);
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
	}

	public String receberProduto(ArrayList<Produto> produto) throws RemoteException {

		for (Produto umProduto: produto){	
			if (produtoExiste(umProduto.getCodigoProduto())) 
	           IncrementaQtdProduto(umProduto);
			else
			   AdicionaProduto(umProduto);							
		}
		System.out.println("Executando receberProduto()");

		return "Produto Adicionado no Estoque";
	}

	public boolean produtoExiste(int codigoProduto) throws RemoteException {
		System.out.println("Executando produtoExiste()");
		// verifica se o produto já existe na base.
		return true;
	}

	public void AdicionaProduto(Produto produto) {
		System.out.println("Executando AdicionaProduto()");
		// Cria um produto novo no estoque		
	}

	public void IncrementaQtdProduto(Produto produto) {
		System.out.println("Executando IncrementaQtdProduto()");
		System.out.println(produto.getCodigoProduto());
		System.out.println(produto.getDescricaoProduto());
		System.out.println(produto.getQtdProduto());
		
		// Incrementa a quantidade de um produto já existente.
	}

}
