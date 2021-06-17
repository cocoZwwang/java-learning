package pers.cocoadel.java.learning.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DataIO {

    public static void writeFixedString(String s, int size, DataOutput dataOutput) throws IOException {
        for (int i = 0; i < size; i++) {
            char ch = 0;
            if (i < s.length()) {
                ch = s.charAt(i);
            }
            dataOutput.writeChar(ch);
        }
    }

    public static String readFixedString(int size, DataInput dataInput) throws IOException {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        boolean done = false;
        while (i < size && !done) {
            char ch = dataInput.readChar();
            i++;
            if (ch == 0) {
                done = true;
            } else {
                sb.append(ch);
            }
        }
        dataInput.skipBytes(2 * (size - i));
        return sb.toString();
    }
}
