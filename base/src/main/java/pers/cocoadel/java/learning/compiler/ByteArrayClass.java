package pers.cocoadel.java.learning.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

public class ByteArrayClass extends SimpleJavaFileObject {

    private ByteArrayOutputStream bos;

    protected ByteArrayClass(String className) {
        super(URI.create("bytes:///" + className.replace('.', '/') + ".class"), Kind.CLASS);
    }

    public byte[] getCode() {
        return bos.toByteArray();
    }

    public OutputStream openOutputStream() {
        bos = new ByteArrayOutputStream();
        return bos;
    }
}
