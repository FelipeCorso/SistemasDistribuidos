/** HelloClient.java **/
package br.furb.rmi.estoque.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;

import br.furb.common.Produto;
import br.furb.rmi.estoque.Estoque;
import br.furb.ws.leaderelection.Server;
import br.furb.ws.status.Status;

public class EstoqueClient {
    private static final String URL_ESTOQUE_RMI = "//127.0.0.1:9191/Estoque";

    public static void main(String[] args) {
	try {
	    Produto produto = new Produto();
	    produto.setCodigoProduto(1);
	    produto.setQtdProduto(11);
	    Estoque obj = (Estoque) Naming.lookup(URL_ESTOQUE_RMI);
	    System.out.println("Mensagem do Servidor: " + obj.retirarProduto(produto));
	} catch (Exception ex) {
	    System.out.println("Exception: " + ex.getMessage());
	}
    }

    public LocalTime getServerTime() throws MalformedURLException, RemoteException, NotBoundException {
	Estoque estoqueServer = (Estoque) Naming.lookup(URL_ESTOQUE_RMI);
	return estoqueServer.getServerTime();
    }

    public void setServerTime(LocalTime rmiTime) throws MalformedURLException, RemoteException, NotBoundException {
	Estoque estoqueServer = (Estoque) Naming.lookup(URL_ESTOQUE_RMI);
	estoqueServer.setServerTime(rmiTime);
    }

    public Status getServerStatus(Server leader) throws MalformedURLException, RemoteException, NotBoundException {
	Estoque estoqueServer = (Estoque) Naming.lookup(URL_ESTOQUE_RMI);
	return Status.INTERNAL_SERVER_ERROR;
    }
}
