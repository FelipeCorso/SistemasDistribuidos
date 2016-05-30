package br.furb.ws.leaderelection.bully.server;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import br.furb.ui.UiServer;
import br.furb.ws.leaderelection.Server;

@WebService(endpointInterface = "br.furb.ws.leaderelection.bully.server.BullyServerInterface")
public class BullyServerImpl implements BullyServerInterface {

    private LocalTime serverTime = LocalTime.now();
    private UiServer uiServer;
    private List<Server> servers = new ArrayList<>();
    private Server leader = new Server();

    public BullyServerImpl() {
        runUiServer();
        uiServer.addServerLog("Servidor aguardando requisicoes ....");
    }

    public void runUiServer() {
        uiServer = new UiServer(serverTime);
        uiServer.setVisible(true);
        uiServer.setTitle("Server Bully");
    }

    @Override
    public void addServer(Server server) {
        servers.add(server);
    }

    @Override
    public Server getLeader() {
        return leader;
    }

    @Override
    public void electServer(Server server) {
        synchronized(this.leader) {
            this.leader = server;
            uiServer.addServerLog("Novo líder definido, " + server.toString());
        }
    }

}
