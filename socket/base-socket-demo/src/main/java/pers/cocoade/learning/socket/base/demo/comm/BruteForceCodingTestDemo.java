package pers.cocoade.learning.socket.base.demo.comm;

public class BruteForceCodingTestDemo {
    public static void main(String[] args) {
        byte byteVal = 101;
        short shortVal = 10001;
        int intVal = 100000001;
        long longVal = 1000000000001L;

        //Encode the fields int the target byte array
        byte[] msg = new byte[Byte.BYTES + Short.BYTES + Integer.BYTES + Long.BYTES];
        int offset = 0;
        offset = BruteForceCoding.encodeIntBigEndian(msg, byteVal, offset, Byte.BYTES);
        offset = BruteForceCoding.encodeIntBigEndian(msg, shortVal, offset, Short.BYTES);
        offset = BruteForceCoding.encodeIntBigEndian(msg, intVal, offset, Integer.BYTES);
        BruteForceCoding.encodeIntBigEndian(msg, longVal, offset, Long.BYTES);
        System.out.println("Encode msg: " + BruteForceCoding.byteArrayToDecimalString(msg));

        //Decode several fields
        long value = BruteForceCoding.decodeIntBigEndian(msg, Byte.BYTES, Short.BYTES);
        System.out.println("Decode Short Value: " + value);
        value = BruteForceCoding.decodeIntBigEndian(msg, Byte.BYTES + Short.BYTES + Integer.BYTES, Long.BYTES);
        System.out.println("Decode Long Value: " + value);

        //Demonstrate dangers of conversion
        //如果你从N个字节解码后希望得到一个有符号的数组，你必须将解码结果（长的结果）存入一个刚好占用N个字节的基本数字类型当中。
        //如果你希望得到一个无符号的数字，就必须把解码结果存入更长的基本整型中，该类型至少要占用N+1个字节。
        //在java中一般只考虑有符号整型
        offset = 4;
        value = BruteForceCoding.decodeIntBigEndian(msg,offset,Byte.BYTES);
        System.out.printf("Decode value (offset %s,size %s) = %s\n", offset, Byte.BYTES, value);
        byte bValue = (byte) BruteForceCoding.decodeIntBigEndian(msg,offset,Byte.BYTES);
        System.out.println("Same value as byte = " + bValue);
    }
}
