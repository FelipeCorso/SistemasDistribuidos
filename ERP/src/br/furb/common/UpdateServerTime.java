package br.furb.common;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import br.furb.corba.compra.client.CompraClient;
import br.furb.rmi.estoque.client.EstoqueClient;
import br.furb.ui.UiServer;
import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.bully.BullyAlgorithm;
import br.furb.ws.leaderelection.bully.client.BullyClient;
import br.furb.ws.status.Status;
import br.furb.ws.venda.client.VendaClient;

public final class UpdateServerTime {

    public static void updateServerTime(UiServer uiServer, Server server) throws MalformedURLException, InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, RemoteException, NotBoundException {
        BullyClient bullyClient = new BullyClient();
        Server leader = bullyClient.getLeader();
        // Apenas atualiza se o server for o líder
        if (server.equals(leader)) {

            uiServer.addServerLog("Inicializando sincronização de relógios.");

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
            long idealTimeL = (wsTime.getMillisOfDay() + corbaTime.getMillisOfDay() + rmiTime.getMillisOfDay()) / amountServers;
            LocalTime idealTime = LocalTime.fromMillisOfDay(idealTimeL);

            int differenceWSIdealTime = Seconds.secondsBetween(wsTime, idealTime).getSeconds();
            int differenceCorbaIdealTime = Seconds.secondsBetween(corbaTime, idealTime).getSeconds();
            int differenceRMIIdealTime = Seconds.secondsBetween(rmiTime, idealTime).getSeconds();

            wsTime = wsTime.plusSeconds(differenceWSIdealTime);
            corbaTime = corbaTime.plusSeconds(differenceCorbaIdealTime);
            rmiTime = rmiTime.plusSeconds(differenceRMIIdealTime);

            uiServer.addServerLog("Atualizando hora do módulo venda(WS)");
            wsClient.setServerTime(wsTime);
            uiServer.addServerLog("Atualizando hora do módulo compra(Corba)");
            corbaClient.setServerTime(corbaTime);
            uiServer.addServerLog("Atualizando hora do módulo estoque(rmi)");
            rmiClient.setServerTime(rmiTime);

            uiServer.addServerLog("Finalizando sincronização dos relógios...");
        }
    }

    public static void checkIfLeaderIsAlive(UiServer uiServer, Server server) {
        while (true) {
            try {
                Thread.sleep(5000);
                uiServer.addServerLog("Verificando se o líder está ativo.");
                BullyAlgorithm bullyAlgorithm = new BullyAlgorithm();
                Status leaderStatus = bullyAlgorithm.getLeaderStatus(server);
                if (leaderStatus != Status.OK) {
                    BullyClient bullyClient = new BullyClient();
                    bullyClient.electServer(server);
                    uiServer.addServerLog("Servidor definido como líder.");
                }

                updateServerTime(uiServer, server);
            } catch (InterruptedException | MalformedURLException | InvalidName | NotFound | CannotProceed
                    | org.omg.CosNaming.NamingContextPackage.InvalidName | RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        LocalTime wsTime = new LocalTime(3, 0);
        LocalTime corbaTime = new LocalTime(2, 50);
        LocalTime rmiTime = new LocalTime(3, 25);

        int amountServers = 3;
        long idealTimeL = (wsTime.getMillisOfDay() + corbaTime.getMillisOfDay() + rmiTime.getMillisOfDay()) / amountServers;
        LocalTime idealTime = LocalTime.fromMillisOfDay(idealTimeL);

        int differenceWSIdealTime = Seconds.secondsBetween(wsTime, idealTime).getSeconds();
        int differenceCorbaIdealTime = Seconds.secondsBetween(corbaTime, idealTime).getSeconds();
        int differenceRMIIdealTime = Seconds.secondsBetween(rmiTime, idealTime).getSeconds();

        wsTime = wsTime.plusSeconds(differenceWSIdealTime);
        corbaTime = corbaTime.plusSeconds(differenceCorbaIdealTime);
        rmiTime = rmiTime.plusSeconds(differenceRMIIdealTime);
    }

}
