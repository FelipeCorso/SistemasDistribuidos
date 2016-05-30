package br.furb.ws.leaderelection;

public enum TypeServer {
    CORBA(0), RMI(1), WS(2);

    private final int code;

    private TypeServer(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * Convert a numerical status code into the corresponding Status
     * 
     * @param statusCode
     *            the numerical status code
     * @return the matching Status or null is no matching Status is defined
     */
    public static TypeServer fromCode(final int statusCode) {
        for (TypeServer s : TypeServer.values()) {
            if (s.code == statusCode) {
                return s;
            }
        }
        return null;
    }
}
