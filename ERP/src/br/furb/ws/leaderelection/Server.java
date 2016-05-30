package br.furb.ws.leaderelection;

import br.furb.ws.status.Status;

public class Server {

    private String ip;
    private int port;
    private TypeServer typeServer;
    private Status status = Status.INTERNAL_SERVER_ERROR;

    public Server() {

    }

    public Server(String ip, int port, TypeServer typeServer) {
        this.ip = ip;
        this.port = port;
        this.typeServer = typeServer;
        this.status = Status.OK;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("IP: ");
        sb.append(getIp());

        sb.append(" Port: ");
        sb.append(getPort());

        sb.append(" Type: ");
        sb.append(getTypeServer().toString());

        return sb.toString();
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
