package br.furb.common;

/**
 * O RMI precisa que a classe possua um contrutor padrão, e a classe {@link java.time.LocalTime} não possui.
 * 
 * @author felipe.corso
 */
public class LocalTime {

    private java.time.LocalTime localTime = java.time.LocalTime.now();

    public LocalTime() {

    }

    public java.time.LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(java.time.LocalTime localTime) {
        this.localTime = localTime;
    }
}
