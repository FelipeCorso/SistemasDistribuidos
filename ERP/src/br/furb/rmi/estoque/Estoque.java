/** HelloWorld.java **/
package br.furb.rmi.estoque;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.ArrayList;
import br.furb.corba.compra.Produto;

public interface Estoque extends Remote {
	String receberProduto(Produto umProduto) throws RemoteException;

	String retirarProduto(Produto produtoRetornado) throws RemoteException;

	void updateServerTime() throws RemoteException;

	LocalTime getServerTime() throws RemoteException;

	void setServerTime(LocalTime localTime) throws RemoteException;

}
