package br.furb.ws.status;

/**
 * Base interface for statuses used in responses.
 */
public interface StatusType {

    /**
     * Get the associated status code
     * 
     * @return the status code
     */
    public int getStatusCode();

    /**
     * Get the class of status code
     * 
     * @return the class of status code
     */
    public Status.Family getFamily();

    /**
     * Get the reason phrase
     * 
     * @return the reason phrase
     */
    public String getReasonPhrase();
}
