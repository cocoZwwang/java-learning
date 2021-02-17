package pers.cocoade.learning.socket.base.demo.comm;


/**
 * BigInteger 和 byte数组之间的相互转换
 */
public class BruteForceCoding {

    private final static int BYTE_MASK = 0xFF;

    /**
     * 把定数组中的每个字节作为一个无符号的十进制数拼成字符串返回
     */
    public static String byteArrayToDecimalString(byte[] bArray){
        StringBuilder rtn = new StringBuilder();
        for(byte b :bArray){
            rtn.append(b & BYTE_MASK).append(" ");
        }
        return rtn.toString();
    }

    /**
     * 按照Big-Endian模式对long类型数字进行编码
     * @return dest数组新的偏移量
     */
    public static int encodeIntBigEndian(byte[] dest,long val,int offset,int size){
        for (int i = 0; i < size; i++) {
            dest[offset++] = (byte) (val >> ((size - i - 1) * Byte.SIZE));
        }
        return offset;
    }

    /**
     * @return 返回解码后的long数字
     */
    public static long decodeIntBigEndian(byte[] val,int offset,int size){
        long rtn = 0;
        for(int i = 0; i < size; i++){
            rtn = (rtn << Byte.SIZE) | ((long) val[offset + i] & BYTE_MASK);
        }
        return rtn;
    }
}
