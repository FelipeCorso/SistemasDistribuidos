package br.furb.ws.leaderelection;

public class Server {
	private String ip;
	private int port;
	private TypeServer typeServer;
	private int status = 200;// HttpRequestStatus.OK;

	public Server(String ip, int port, TypeServer typeServer) {
		this.ip = ip;
		this.port = port;
		this.typeServer = typeServer;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public TypeServer getTypeServer() {
		return typeServer;
	}

	public void setTypeServer(TypeServer typeServer) {
		this.typeServer = typeServer;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Server)) {
			return false;
		}
		Server server = (Server) obj;
		if (this.getIp().equals(server.getIp())/**/
				&& this.getPort() == server.getPort()/**/
				&& this.getTypeServer().equals(server.getTypeServer())) {
			return true;
		}

		return false;
	}

}
