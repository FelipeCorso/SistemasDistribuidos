package br.furb.ws.leaderelection.bully;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import br.furb.common.UpdateServerTime;
import br.furb.corba.compra.client.CompraClient;
import br.furb.rmi.estoque.client.EstoqueClient;
import br.furb.ui.UiServer;
import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.bully.client.BullyClient;
import br.furb.ws.status.Status;
import br.furb.ws.venda.client.VendaClient;

public class BullyAlgorithm {

    private static final int NUMBER_OF_ATTEMPTS = 3;

    public static void checkIfLeaderIsAlive(UiServer uiServer, Server server) {
        while (true) {
            try {
                Thread.sleep(5000);
                uiServer.addServerLog("Verificando se o líder está ativo.");
                Server currentlyLeader = BullyClient.getInstance().getLeader();
                if (getLeaderStatus(currentlyLeader) != Status.OK) {
                    BullyClient.getInstance().electServer(server, currentlyLeader);
                    uiServer.addServerLog("Servidor definido como líder.");
                }

                UpdateServerTime.updateServerTime(uiServer, server);
            } catch (InterruptedException | MalformedURLException | InvalidName | NotFound | CannotProceed
                    | org.omg.CosNaming.NamingContextPackage.InvalidName | RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }

    }

    public static Status getLeaderStatus(Server leader) throws MalformedURLException {
        Status leaderStatus = Status.INTERNAL_SERVER_ERROR;
        try {
            int i = 0;
            while (i < NUMBER_OF_ATTEMPTS && leaderStatus == Status.INTERNAL_SERVER_ERROR) {
                Thread.sleep(5000);
                System.out.println(leader.toString());
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
                            leaderStatus = VendaClient.getInstance().getServerStatus(leader);
                            break;
                        default:
                            break;
                    }
                }
                i++;
            }
        } catch (InvalidName | NotFound | CannotProceed | org.omg.CosNaming.NamingContextPackage.InvalidName
                | RemoteException | NotBoundException | InterruptedException e) {
            e.printStackTrace();
        }
        return leaderStatus;
    }

}
