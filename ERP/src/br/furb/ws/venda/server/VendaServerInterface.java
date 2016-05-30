package br.furb.ws.venda.server;

import java.time.LocalTime;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.furb.common.Produto;
import br.furb.ws.leaderelection.Server;

@WebService
@SOAPBinding(style = Style.RPC)
public interface VendaServerInterface {

    @WebMethod
    boolean efetuarVendaProduto(Produto produto);

    @WebMethod
    public void updateServerTime();

    @WebMethod
    public LocalTime getServerTime();
<<<<<<< HEAD

    @WebMethod
    public void setServerTime(LocalTime localTime);

    @WebMethod
    public Server getServer();

    @WebMethod
    public void setServer(Server server);

    void checkIfLeaderIsAlive();

=======

    @WebMethod
    public void setServerTime(LocalTime localTime);
>>>>>>> master
}
