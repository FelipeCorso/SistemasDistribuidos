package br.furb.ws.venda.server;

import java.rmi.RemoteException;

import javax.jws.WebService;

import org.joda.time.LocalTime;

import br.furb.common.Produto;
import br.furb.common.UpdateServerTime;
import br.furb.rmi.estoque.Estoque;
import br.furb.rmi.estoque.client.ClientEstoque;
import br.furb.ui.UiServer;
import br.furb.ws.leaderelection.Server;

@WebService(endpointInterface = "br.furb.ws.venda.server.VendaServerInterface")
public class VendaServerImpl implements VendaServerInterface {

    private LocalTime serverTime = LocalTime.now();
    private UiServer uiServer;
    private Server server; // O próprio servidor

    public VendaServerImpl(Server server) {
        this.server = server;
        runUiServer();
        uiServer.addServerLog("Servidor aguardando requisicoes ....");
    }

    @Override
    public void checkIfLeaderIsAlive() {
        UpdateServerTime.checkIfLeaderIsAlive(uiServer, server);
    }

    private boolean comunicarFinancaVenda() {
        return true;// aqui ficará a integração do Vendas com o financeiro.
    }

    @Override
    public boolean efetuarVendaProduto(Produto produto) {
        try {
            uiServer.addServerLog("executando efetuarVendaProduto()");
            ClientEstoque estoqueClient = new ClientEstoque();
            Estoque estoque = estoqueClient.retornaComunicacaoServer();

            uiServer.addServerLog("comunicando Estoque server");
            uiServer.addServerLog("Estoque server retorno: " + estoque.retirarProduto(produto));
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
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
        uiServer.NomeServidor("Server Vendas");
    }

    public UiServer getUiServer() {
        return uiServer;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

}
