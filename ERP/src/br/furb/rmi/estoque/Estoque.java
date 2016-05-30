/** HelloWorld.java **/
package br.furb.rmi.estoque;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import br.furb.common.LocalTime;
import br.furb.common.Produto;
import br.furb.ws.leaderelection.Server;

public interface Estoque extends Remote {

    String receberProduto(Produto umProduto) throws RemoteException;

    String retirarProduto(Produto produtoRetornado) throws RemoteException;

    ArrayList<Produto> retornarProdutos() throws RemoteException;

    void updateServerTime() throws RemoteException;

    LocalTime getServerTime() throws RemoteException;

    void setServerTime(LocalTime localTime) throws RemoteException;

    Server getServer() throws RemoteException;

    void checkIfLeaderIsAlive() throws RemoteException;

}
