package br.furb.ws.leaderelection.bully.server;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.joda.time.LocalTime;

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
        uiServer.addServerLog("Adicionado servidor: " + server.toString());
    }

    @Override
    public Server getLeader() {
        return leader;
    }

    @Override
    public void electServer(Server server) {
        synchronized(this.leader) {
            /*
            * Se chegou no método de eleição é porque o servidor não respondeu, então o remove da lista. 
            */
            int leaderIndex = servers.indexOf(this.leader);
            if (leaderIndex >= 0) {
                servers.remove(leaderIndex);
            }

            // Define como novo líder o servidor com o maior id
            int newLeaderIndex = servers.size() - 1;
            if (newLeaderIndex < 0) {
                this.leader = servers.get(0);
            } else {
                this.leader = servers.get(newLeaderIndex);
            }

            uiServer.addServerLog("Novo líder definido, " + this.leader.toString());
        }
    }

}
