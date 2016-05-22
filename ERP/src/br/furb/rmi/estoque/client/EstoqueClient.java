/** HelloClient.java **/
package br.furb.rmi.estoque.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.ArrayList;

import br.furb.common.Produto;
import br.furb.rmi.estoque.Estoque;

public class EstoqueClient {
	public static void main(String[] args) {
		try {
			ArrayList<Produto> listaProduto = new ArrayList<Produto>();
			Produto produto = new Produto();
			produto.setCodigoProduto(1);
			produto.setDescricaoProduto("teste");
			produto.setQtdProduto(2);

			listaProduto.add(produto);
			Estoque obj = (Estoque) Naming.lookup("//localhost/Estoque");
			System.out.println("Mensagem do Servidor: " + obj.receberProduto(listaProduto));
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
	}

	public LocalTime getServerTime() throws MalformedURLException, RemoteException, NotBoundException {
		Estoque estoqueServer = (Estoque) Naming.lookup("//localhost/Estoque");
		return estoqueServer.getServerTime();
	}

	public void setServerTime(LocalTime rmiTime) throws MalformedURLException, RemoteException, NotBoundException {
		Estoque estoqueServer = (Estoque) Naming.lookup("//localhost/Estoque");
		estoqueServer.setServerTime(rmiTime);
	}
}
