package com.github.ckroeger.jkeystore.core;

public class KeystoreException extends Exception {

    public KeystoreException(String message) {
        super(message);
    }

    public KeystoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
