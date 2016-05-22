package br.furb.common;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import br.furb.corba.compra.client.CompraClient;
import br.furb.rmi.estoque.client.EstoqueClient;
import br.furb.ui.UiServer;
import br.furb.ws.venda.client.VendaClient;

public final class UpdateServerTime {

	public static void update(UiServer uiServer) throws MalformedURLException, InvalidName, NotFound, CannotProceed,
			org.omg.CosNaming.NamingContextPackage.InvalidName, RemoteException, NotBoundException {
		uiServer.addServerLog("Inicializando sincronização de relógios...");

		uiServer.addServerLog("Obtendo hora do módulo venda(WS)");
		VendaClient wsClient = new VendaClient();
		LocalTime wsTime = wsClient.getServerTime();

		uiServer.addServerLog("Obtendo hora do módulo compra(Corba)");
		CompraClient corbaClient = new CompraClient();
		LocalTime corbaTime = corbaClient.getServerTime();

		uiServer.addServerLog("Obtendo hora do módulo estoque(rmi)");
		EstoqueClient rmiClient = new EstoqueClient();
		LocalTime rmiTime = rmiClient.getServerTime();

		int amountServers = 3;
		long idealTimeL = (wsTime.toSecondOfDay() + corbaTime.toSecondOfDay() + rmiTime.toSecondOfDay())
				/ amountServers;
		LocalTime idealTime = LocalTime.ofSecondOfDay(idealTimeL);

		long differenceWSIdealTime = Duration.between(wsTime, idealTime).get(ChronoUnit.SECONDS);
		long differenceCorbaIdealTime = Duration.between(corbaTime, idealTime).get(ChronoUnit.SECONDS);
		long differenceRMIIdealTime = Duration.between(rmiTime, idealTime).get(ChronoUnit.SECONDS);

		wsTime.plusSeconds(differenceWSIdealTime);
		corbaTime.plusSeconds(differenceCorbaIdealTime);
		rmiTime.plusSeconds(differenceRMIIdealTime);

		wsClient.setServerTime(wsTime);
		corbaClient.setServerTime(corbaTime);
		rmiClient.setServerTime(rmiTime);

		uiServer.addServerLog("Finalizando sincronização dos relógios...");
	}

}
