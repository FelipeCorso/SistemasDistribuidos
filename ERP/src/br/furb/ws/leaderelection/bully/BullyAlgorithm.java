package br.furb.ws.leaderelection.bully;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import br.furb.corba.compra.client.CompraClient;
import br.furb.rmi.estoque.client.EstoqueClient;
import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.bully.client.BullyClient;
import br.furb.ws.status.Status;
import br.furb.ws.venda.client.VendaClient;

public class BullyAlgorithm {

    private static final int NUMBER_OF_ATTEMPTS = 3;

    public Status getLeaderStatus(Server server) throws MalformedURLException {
        BullyClient bullyClient = new BullyClient();
        Status leaderStatus = Status.INTERNAL_SERVER_ERROR;
        try {
            int i = 0;
            while (i < NUMBER_OF_ATTEMPTS && leaderStatus == Status.INTERNAL_SERVER_ERROR) {
                Server leader = bullyClient.getLeader();
                if (leader.getStatus() != Status.INTERNAL_SERVER_ERROR) {
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
                i++;
            }
        } catch (InvalidName | NotFound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName
                | RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return leaderStatus;
    }

}
