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
        if (args != null) {
            TypeServer typeServer = TypeServer.valueOf(args[0]);
            List<String> params = new ArrayList<>();
            for (int i = 1; i < args.length; i++) {
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
                    if (args.length > 1) { // host
                        VendaClient.getInstance().setHost(args[1]);
                        if (args.length > 2) { // port
                            VendaClient.getInstance().setPort(Integer.parseInt(args[2]));
                        }
                    }
                    VendaServerPublisher.main(params.toArray(new String[0]));
                    break;
                case BULLY:
                    if (args.length > 1) { // host
                        BullyClient.getInstance().setHost(args[1]);
                        if (args.length > 2) {// port
                            BullyClient.getInstance().setPort(Integer.parseInt(args[2]));
                        }
                    }
                    BullyServerPublisher.main(params.toArray(new String[0]));
                    break;
                default:
                    throw new RuntimeException("TypeServer undefined");
            }
        }
    }

}
