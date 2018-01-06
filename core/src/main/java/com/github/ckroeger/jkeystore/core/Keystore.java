package com.github.ckroeger.jkeystore.core;

/**
 *
 */
public interface Keystore<V> {

    V get(String key);

    V put(String key, V value);

    void persist() throws KeystoreException;
}
