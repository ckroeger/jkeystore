package com.github.ckroeger.jkeystore.xmlstore;

import com.github.ckroeger.jkeystore.core.KeystoreException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

import java.io.File;

import static org.testng.Assert.*;

public class SimpleXmlKeyStoreTest {

    private String filepath = null;

    private File tempFile;

    @BeforeMethod
    public void setUp() throws Exception {
        tempFile = File.createTempFile("dsa", "dssd");
        filepath = tempFile.getAbsolutePath();
        System.out.println("creating " + filepath);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        System.out.println("deleting file " + tempFile.getAbsolutePath());
        tempFile.delete();
    }

    @Test(expectedExceptions = KeystoreException.class)
    public void test_constructor_invalid_path_null() throws Exception {
        SimpleXmlKeyStore uut = new SimpleXmlKeyStore(null);
    }

    @Test(expectedExceptions = KeystoreException.class)
    public void test_constructor_invalid_path_empty() throws Exception {
        SimpleXmlKeyStore uut = new SimpleXmlKeyStore("");
    }

    @Test
    public void test_empty_get() throws Exception {
        SimpleXmlKeyStore uut = new SimpleXmlKeyStore(filepath);
        String any = uut.get("any");
        assertThat(any).isNullOrEmpty();
    }

    @Test(dependsOnMethods = "test_empty_get")
    public void test_put() throws Exception {
        SimpleXmlKeyStore uut = new SimpleXmlKeyStore(filepath);
        String any = uut.get("any");
        assertThat(any).isNullOrEmpty();
        String oldValue = uut.put("any", "anyval");
        assertThat(oldValue).isNullOrEmpty();
        oldValue = uut.put("any", "anyvalx");
        assertThat(oldValue).isEqualTo("anyval");
    }

    @Test(dependsOnMethods = "test_put")
    public void test_put_get() throws Exception {
        SimpleXmlKeyStore uut = new SimpleXmlKeyStore(filepath);
        String any = uut.get("any");
        assertThat(any).isNullOrEmpty();
        String oldValue = uut.put("any", "anyval");
        String val = uut.get("any");
        assertThat(val).isEqualTo("anyval");
    }

    @Test(dependsOnMethods = "test_put_get")
    public void test_put_get_persist() throws Exception {
        SimpleXmlKeyStore uut = new SimpleXmlKeyStore(filepath);
        uut.put("any", "anyval");
        uut.persist();
        SimpleXmlKeyStore uut2 = new SimpleXmlKeyStore(filepath);
        String val2 = uut.get("any");
        assertThat(val2).isEqualTo("anyval");
    }
}