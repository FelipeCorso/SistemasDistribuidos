/** HelloClient.java **/
package br.furb.rmi.estoque.client;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.joda.time.LocalTime;

import br.furb.rmi.estoque.Estoque;
import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.TypeServer;
import br.furb.ws.status.Status;

public class EstoqueClient {

    //    public static void main(String[] args) {
    //        try {
    //            Produto produto = new Produto();
    //            produto.setCodigoProduto(1);
    //            produto.setQtdProduto(11);
    //            Estoque obj = (Estoque) Naming.lookup(URL_ESTOQUE_RMI);
    //            System.out.println("Mensagem do Servidor: " + obj.retirarProduto(produto));
    //        } catch (Exception ex) {
    //            System.out.println("Exception: " + ex.getMessage());
    //        }
    //    }

    public static void main(String[] args) {

        //        String host = (args.length < 1) ? null : args[0];
        try {
            //            Registry registry = LocateRegistry.getRegistry(host);
            Server server = new Server("localhost", 1099, TypeServer.RMI);
            Registry registry = LocateRegistry.getRegistry(server.getIp(), server.getPort());
            Estoque stub = (Estoque) registry.lookup("Estoque");
            String response = stub.getServerTime().toString();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private Estoque getEstoque() throws RemoteException, NotBoundException, AccessException {
        Server server = new Server("localhost", 1099, TypeServer.RMI);
        Registry registry = LocateRegistry.getRegistry(server.getIp(), server.getPort());
        Estoque stub = (Estoque) registry.lookup("Estoque");
        return stub;
    }

    public LocalTime getServerTime() {
        try {
            return getEstoque().getServerTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LocalTime.now();
    }

    public void setServerTime(LocalTime rmiTime) throws MalformedURLException, RemoteException, NotBoundException {
        try {
            getEstoque().setServerTime(rmiTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Status getServerStatus(Server leader) throws MalformedURLException, RemoteException, NotBoundException {
        try {
            return getEstoque().getServer().getStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Status.INTERNAL_SERVER_ERROR;
    }
}
