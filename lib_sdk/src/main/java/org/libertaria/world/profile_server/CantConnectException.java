package org.libertaria.world.profile_server;

/**
 * Created by mati on 22/11/16.
 */
public class CantConnectException extends Exception {

    public CantConnectException(String s) {
        super(s);
    }

    public CantConnectException(String s, Exception exception) {
        super(s,exception);
    }
}
