package pers.cocoadel.java.learning.io;

import java.io.*;

/**
 * 通过对象序列化来进行深度拷贝
 * 这种深度拷贝的方式虽然很灵活，但是通常它会比显式地构建新对象并复制或者克隆数据域的克隆方法要 慢很多
 */
@SuppressWarnings("serial")
public class SerialCloneable implements Cloneable, Serializable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(this);
            }
            try (ByteArrayInputStream bas = new ByteArrayInputStream(bos.toByteArray())) {
                ObjectInputStream oin = new ObjectInputStream(bas);
                return oin.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            CloneNotSupportedException ce = new CloneNotSupportedException();
            ce.initCause(e);
            throw ce;
        }
    }
}
