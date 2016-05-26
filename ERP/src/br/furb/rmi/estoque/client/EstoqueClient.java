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
			Produto produto = new Produto();
            produto.setCodigoProduto(1);
            produto.setQtdProduto(11);
			Estoque obj = (Estoque) Naming.lookup("//localhost/Estoque");			
			System.out.println("Mensagem do Servidor: " + obj.retirarProduto(produto));
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
