package br.furb.ws.venda.server;

import java.time.LocalTime;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.furb.common.Produto;
import br.furb.ui.UiServer;

@WebService
@SOAPBinding(style = Style.RPC)
public interface VendaServerInterface {

	@WebMethod
	boolean efetuarVendaProduto(Produto produto);

	@WebMethod
	public void updateServerTime();

	@WebMethod
	public LocalTime getServerTime();

	@WebMethod
	public void setServerTime(LocalTime localTime);

	// @WebMethod
	public UiServer getUiServer();

	// public void setLeader(Server server);
	//
	// public Server getLeader();

}
