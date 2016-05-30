/** HelloServer.java **/
package br.furb.rmi.estoque.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.ArrayList;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import br.furb.common.Produto;
import br.furb.common.UpdateServerTime;
import br.furb.corba.compra.Compra;
import br.furb.corba.compra.client.CompraClient;
import br.furb.rmi.estoque.ArquivoEstoque;
import br.furb.rmi.estoque.Estoque;
import br.furb.ui.UiServer;
import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.TypeServer;
import br.furb.ws.leaderelection.bully.BullyAlgorithm;
import br.furb.ws.leaderelection.bully.client.BullyClient;

public class EstoqueServer extends UnicastRemoteObject implements Estoque {

    private LocalTime serverTime = LocalTime.now();
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

    // main()
    public static void main(String[] args) {
	try {
	    Server server = new Server("127.0.0.1", 9191, TypeServer.RMI);
	    EstoqueServer estoqueServer = new EstoqueServer(server);
	    Naming.rebind("//" + server.getIp() + ":" + server.getPort() + "/Estoque", estoqueServer);
	    estoqueServer.checkIfLeaderIsAlive();
	} catch (Exception ex) {
	    System.out.println("Exception: " + ex.getMessage());
	}
    }

    public String receberProduto(Produto umProduto) throws RemoteException {
	try {
	    uiServer.addServerLog("Servidor executando receberProduto()");
	    controleEstoque.addProdutoEstoque(umProduto);
	    return "Produto recebido";
	} catch (Exception e) {
	    return "Produto não recebido";
	}
    }

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

    public ArrayList<Produto> retornarProdutos() {
	return controleEstoque.retornarProdutoArquivo();
    }

    private String solicitaCompraProduto(Produto umProduto) throws RemoteException {
	CompraClient clientCompra = new CompraClient();
	String[] args = new String[2];
	Compra compra = clientCompra.retornaClientCompras(args);
	compra.recebeNota(umProduto.getCodigoProduto(), umProduto.getDescricaoProduto(),
		umProduto.getQtdProduto() * qtdCompraProduto, umProduto.getValorUnitario());

	return retirarProdutoInterno(umProduto);
    }

    @Override
    public void checkIfLeaderIsAlive() {
	while (true) {
	    try {
		Thread.sleep(5000);
		BullyAlgorithm bullyAlgorithm = new BullyAlgorithm();
		bullyAlgorithm.checkIfLeaderIsAlive(getServer());
		updateServerTime();
	    } catch (InterruptedException | MalformedURLException e) {
		e.printStackTrace();
	    }
	}
    }

    @Override
    public void updateServerTime() {
	try {
	    BullyClient bullyClient = new BullyClient();
	    Server leader = bullyClient.getLeader();
	    if (this.getServer().equals(leader)) {
		UpdateServerTime.update(uiServer);
	    }
	} catch (MalformedURLException | InvalidName | NotFound | CannotProceed
		| org.omg.CosNaming.NamingContextPackage.InvalidName | RemoteException | NotBoundException e) {
	    e.printStackTrace();
	}
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
