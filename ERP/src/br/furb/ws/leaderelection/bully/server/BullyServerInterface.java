package br.furb.ws.leaderelection.bully.server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.furb.ws.leaderelection.Server;

@WebService
@SOAPBinding(style = Style.RPC)
public interface BullyServerInterface {

    @WebMethod
    public void addServer(Server server);

    @WebMethod
    public Server getLeader();

    @WebMethod
    public void electServer(Server server, Server currentlyLeader);
}
