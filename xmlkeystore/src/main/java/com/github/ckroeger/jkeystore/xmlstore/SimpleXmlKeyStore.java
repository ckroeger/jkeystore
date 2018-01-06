package com.github.ckroeger.jkeystore.xmlstore;

import com.github.ckroeger.jkeystore.core.Keystore;
import com.github.ckroeger.jkeystore.core.KeystoreException;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class SimpleXmlKeyStore implements Keystore<String> {

    private Map<String, String> map;

    private String filepath = null;

    public SimpleXmlKeyStore(String filepath) throws KeystoreException {

        this.filepath = filepath;
        if (StringUtils.isBlank(filepath)) {
            throw new KeystoreException("Invalid filepath: '" + filepath + "'");
        }

        File storeFile = new File(filepath);
        boolean exists = storeFile.exists();
        if (!exists) {
            map = new HashMap<>();
        } else {
            doFileSanityChecks(storeFile);
            String xmldata = null;
            try {
                xmldata = FileUtils.readFileToString(storeFile, Charset.forName("UTF8"));
                if (xmldata.length() > 10) {
                    XStream xs = new XStream();
                    map = (Map<String, String>) xs.fromXML(xmldata);
                }
                else {
                    map = new HashMap<>();
                }

            } catch (IOException e) {
                throw new KeystoreException("Can not read keystore-file", e);
            }
        }
    }

    private void doFileSanityChecks(File storeFile) throws KeystoreException {

        boolean canRead = storeFile.canRead();
        if (!canRead) {
            throw new KeystoreException("Can not read file with given filepath '" + storeFile.getAbsolutePath() + "'.");
        }
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

    @Override
    public String put(String key, String value) {
        return map.put(key, value);
    }

    @Override
    public void persist() throws KeystoreException {
        try {
            File file = new File(filepath);
            XStream xs = new XStream();
            String data = xs.toXML(map);
            FileUtils.writeStringToFile(file, data, Charset.forName("UTF8"));
        } catch (IOException e) {
            throw new KeystoreException("Can persist file.", e);
        }
    }
}
