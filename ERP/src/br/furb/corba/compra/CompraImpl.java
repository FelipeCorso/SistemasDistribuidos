package br.furb.corba.compra;

import java.rmi.RemoteException;
import java.time.LocalTime;

import br.furb.common.Produto;
import br.furb.common.UpdateServerTime;
import br.furb.rmi.estoque.Estoque;
import br.furb.rmi.estoque.client.ClientEstoque;
import br.furb.ui.UiServer;
import br.furb.ws.leaderelection.Server;

public class CompraImpl extends CompraPOA {

    private LocalTime serverTime = LocalTime.now();
    private UiServer uiServer;
    private Server server;
    private ClientEstoque clientEstoque;

    public CompraImpl(Server server) {
        this.server = server;
        runUiServer();
        clientEstoque = new ClientEstoque();
        uiServer.addServerLog("Servidor aguardando requisicoes ....");
    }

    @Override
    public boolean recebeNota(int aCodigo, String aNome, int aQuantidade, double aValorUnitario) {
        adicionaLog("Compras recebeu nota fiscal de entrada");
        Produto produto = new Produto();
        produto.setCodigoProduto(aCodigo);
        produto.setDescricaoProduto(aNome);
        produto.setQtdProduto(aQuantidade);
        produto.setValorUnitario(aValorUnitario);
        return comunicaEstoque(produto);
    };

    private boolean comunicaEstoque(Produto umProduto) {
        try {
            adicionaLog("Servidor compras comunicando servidor estoque");
            Estoque estoque = clientEstoque.retornaComunicacaoServer();
            adicionaLog("Retorno Estoque: " + estoque.receberProduto(umProduto));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void adicionaLog(String log) {
        uiServer.addServerLog(log);
    }

    @Override
    public void checkIfLeaderIsAlive() {
        UpdateServerTime.checkIfLeaderIsAlive(uiServer, server);
    }

    @Override
    public void updateServerTime() {
        //        try {
        //            BullyClient bullyClient = new BullyClient();
        //            Server leader = bullyClient.getLeader();
        //            if (this.server.equals(leader)) {
        //                UpdateServerTime.updateServerTime(uiServer);
        //            }
        //        } catch (MalformedURLException | InvalidName | NotFound | CannotProceed
        //                | org.omg.CosNaming.NamingContextPackage.InvalidName | RemoteException | NotBoundException e) {
        //            e.printStackTrace();
        //        }
    }

    @Override
    public long getServerTime() {
        return serverTime.toSecondOfDay();
    }

    @Override
    public void setServerTime(long newServerTime) {
        serverTime = LocalTime.ofSecondOfDay(newServerTime);
        uiServer.setCurrentTime(serverTime);
    }

    public void runUiServer() {
        uiServer = new UiServer(serverTime);
        uiServer.setVisible(true);
        uiServer.setTitle("Server Compras");
    }

    @Override
    public String getIp() {
        return server.getIp();
    }

    @Override
    public int getPort() {
        return server.getPort();
    }

    @Override
    public int getTypeServer() {
        return server.getTypeServer().getCode();
    }

    @Override
    public int getStatus() {
        return server.getStatus().getStatusCode();
    }

}
