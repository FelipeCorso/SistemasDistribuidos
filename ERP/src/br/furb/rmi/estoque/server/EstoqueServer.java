/** HelloServer.java **/
package br.furb.rmi.estoque.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import org.joda.time.LocalTime;

import br.furb.common.Produto;
import br.furb.corba.compra.Compra;
import br.furb.corba.compra.client.CompraClient;
import br.furb.rmi.estoque.ArquivoEstoque;
import br.furb.rmi.estoque.Estoque;
import br.furb.ui.UiServer;
import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.TypeServer;
import br.furb.ws.leaderelection.bully.BullyAlgorithm;
import br.furb.ws.leaderelection.bully.client.BullyClient;

public class EstoqueServer implements Estoque {

    private LocalTime serverTime = new LocalTime();
    private UiServer uiServer;
    private Server server; // O próprio servidor
    private ArquivoEstoque controleEstoque;
    private int qtdCompraProduto = 2;

    public EstoqueServer(Server server) throws RemoteException {
        this.server = server;
        runUiServer();
        controleEstoque = new ArquivoEstoque();
        uiServer.addServerLog("Servidor aguardando requisicoes ....");
    }

    //    public static void main(String[] args) {
    //        try {
    //
    //            RemoteInterface v=new RemoteObject();
    //            java.rmi.Naming.rebind("//:5099/count", v);
    //            System.setProperty("java.security.policy", "./server.policy");
    //            System.setProperty("java.security.policy", "file:./security.policy");
    //            -Djava.security.manager -Djava.security.policy=src/br/furb/rmi/estoque/server/server.policy
    //            System.setSecurityManager(new java.rmi.RMISecurityManager());
    //            Server server = new Server("localhost", 1099, TypeServer.RMI);
    //            java.rmi.registry.LocateRegistry.createRegistry(server.getPort());
    //            EstoqueServer estoqueServer = new EstoqueServer(server);
    //            Naming.rebind("//" + server.getIp() + "/Estoque", estoqueServer);
    //            Naming.rebind("//" + server.getIp() + "/Estoque", estoqueServer);
    //            estoqueServer.checkIfLeaderIsAlive();
    //        } catch (Exception ex) {
    //            ex.printStackTrace();
    //        }
    //    }

    public static void main(String args[]) {

        try {
            Server server = new Server("localhost", 1099, TypeServer.RMI);
            EstoqueServer obj = new EstoqueServer(server);
            Estoque stub = (Estoque) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry(server.getIp(), server.getPort());
            registry.bind("Estoque", stub);
            BullyClient.getInstance().addServer(server);
            stub.checkIfLeaderIsAlive();

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public String receberProduto(Produto umProduto) throws RemoteException {
        try {
            uiServer.addServerLog("Servidor executando receberProduto()");
            controleEstoque.addProdutoEstoque(umProduto);
            return "Produto recebido";
        } catch (Exception e) {
            return "Produto não recebido";
        }
    }

    @Override
    public String retirarProduto(Produto produtoRetornado) throws RemoteException {
        uiServer.addServerLog("Servidor executando retirarProduto()");
        Produto produtoBackup = new Produto(produtoRetornado);

        String retorno = retirarProdutoInterno(produtoRetornado);
        if (retorno == "Produto retirado com sucesso") {
            return retorno;
        } else {
            return solicitaCompraProduto(produtoBackup);
        }
    }

    private String retirarProdutoInterno(Produto umProduto) {
        umProduto = controleEstoque.retiraProdutoEstoque(umProduto);
        if (umProduto != null) {
            return "Produto retirado com sucesso";
        } else {
            umProduto = null;
            return "Não foi possível retirar o Produto";
        }
    }

    @Override
    public ArrayList<Produto> retornarProdutos() {
        return controleEstoque.retornarProdutoArquivo();
    }

    private String solicitaCompraProduto(Produto umProduto) throws RemoteException {
        CompraClient clientCompra = new CompraClient();
        String[] args = new String[2];
        Compra compra = clientCompra.retornaClientCompras(args);
        compra.recebeNota(umProduto.getCodigoProduto(), umProduto.getDescricaoProduto(), umProduto.getQtdProduto() * qtdCompraProduto, umProduto.getValorUnitario());

        return retirarProdutoInterno(umProduto);
    }

    @Override
    public void checkIfLeaderIsAlive() {
        BullyAlgorithm.checkIfLeaderIsAlive(uiServer, server);
    }

    @Override
    public void updateServerTime() {
        //        try {
        //            BullyClient bullyClient = new BullyClient();
        //            Server leader = bullyClient.getLeader();
        //            if (this.getServer().equals(leader)) {
        //                UpdateServerTime.updateServerTime(uiServer);
        //            }
        //        } catch (MalformedURLException | InvalidName | NotFound | CannotProceed
        //                | org.omg.CosNaming.NamingContextPackage.InvalidName | RemoteException | NotBoundException e) {
        //            e.printStackTrace();
        //        }
    }

    @Override
    public LocalTime getServerTime() {
        return serverTime;
    }

    @Override
    public void setServerTime(LocalTime newServerTime) {
        serverTime = newServerTime;
        uiServer.setCurrentTime(serverTime);
    }

    public void runUiServer() {
        uiServer = new UiServer(serverTime);
        uiServer.setVisible(true);
        uiServer.setTitle("Server Estoque");
    }

    @Override
    public Server getServer() {
        return server;
    }
}
