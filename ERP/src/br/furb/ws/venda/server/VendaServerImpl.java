package br.furb.ws.venda.server;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;

import javax.jws.WebService;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import br.furb.common.UpdateServerTime;
import br.furb.ui.UiServer;

@WebService(endpointInterface = "br.furb.ws.venda.server.VendaServerInterface")
public class VendaServerImpl implements VendaServerInterface {

	private LocalTime serverTime = LocalTime.now();
	private UiServer uiServer;

	public VendaServerImpl() {
		runUiServer();
	}

	private boolean comunicarFinancaVenda() {
		return true;// aqui ficará a integração do Vendas com o financeiro.
	}

	public boolean efetuarVendaProduto() {
		comunicarFinancaVenda();
		return true;
	}

	@Override
	public void updateServerTime() {
		try {
			UpdateServerTime.update(uiServer);
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
	}

}
