package br.furb.ws.leaderelection.bully;

import java.net.MalformedURLException;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import br.furb.corba.compra.client.CompraClient;
import br.furb.rmi.estoque.client.EstoqueClient;
import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.bully.client.BullyClient;
import br.furb.ws.venda.client.VendaClient;

public class BullyAlgorithm {

	private static final int NUMBER_OF_ATTEMPTS = 3;

	public void checkIfLeaderIsAlive(Server server) throws MalformedURLException {
		BullyClient bullyClient = new BullyClient();
		int leaderStatus = 0;
		try {
			for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
				Server leader = bullyClient.getLeader();
				switch (leader.getTypeServer()) {
				case CORBA:
					CompraClient compraClient = new CompraClient();
					leaderStatus = compraClient.getServerStatus(leader);
					break;
				case RMI:
					EstoqueClient estoqueClient = new EstoqueClient();
					leaderStatus = estoqueClient.getServerStatus(leader);
					break;
				case WS:
					VendaClient client = new VendaClient();
					leaderStatus = client.getServerStatus(leader);
					break;
				}
			}
		} catch (InvalidName | NotFound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			e.printStackTrace();
		}
		if (leaderStatus != 200) {
			bullyClient.electServer(server);
		}
	}

}
