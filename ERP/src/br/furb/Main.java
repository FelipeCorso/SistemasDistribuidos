package br.furb;

import java.util.ArrayList;
import java.util.List;

import br.furb.corba.compra.server.CompraServer;
import br.furb.rmi.estoque.server.EstoqueServer;
import br.furb.ws.leaderelection.TypeServer;
import br.furb.ws.leaderelection.bully.client.BullyClient;
import br.furb.ws.leaderelection.bully.server.BullyServerPublisher;
import br.furb.ws.venda.client.VendaClient;
import br.furb.ws.venda.server.VendaServerPublisher;

public class Main {

    public static void main(String[] args) {
        if (args != null && args.length > 0) {

            BullyClient.getInstance().setHost(args[0]);
            BullyClient.getInstance().setPort(Integer.parseInt(args[1]));

            TypeServer typeServer = TypeServer.valueOf(args[2]);
            List<String> params = new ArrayList<>();
            for (int i = 3; i < args.length; i++) {
                params.add(args[i]);
            }
            switch (typeServer) {
                case CORBA:
                    CompraServer.main(params.toArray(new String[0]));
                    break;
                case RMI:
                    EstoqueServer.main(params.toArray(new String[0]));
                    break;
                case WS:
                    VendaClient.getInstance().setHost(args[3]);
                    VendaClient.getInstance().setPort(Integer.parseInt(args[4]));
                    VendaServerPublisher.main(params.toArray(new String[0]));
                    break;
                case BULLY:
                    BullyClient.getInstance().setHost(args[3]);
                    BullyClient.getInstance().setPort(Integer.parseInt(args[4]));
                    BullyServerPublisher.main(params.toArray(new String[0]));
                    break;
            }
        } else {
            throw new RuntimeException("TypeServer undefined");
        }
    }

}
