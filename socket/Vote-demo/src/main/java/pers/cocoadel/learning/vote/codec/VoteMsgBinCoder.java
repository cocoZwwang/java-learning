package pers.cocoadel.learning.vote.codec;

import pers.cocoadel.learning.vote.bean.VoteMsg;

import java.io.*;

/**
 *
 *  0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |     Magic       |Flags|      ZERO             |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |              Candidate ID                     |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                                               |
 * |          Vote Count (only in response         |
 * |                                               |
 * |                                               |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 */
public class VoteMsgBinCoder implements VoteMsgCoder{
    public static final int MIN_WIRE_LENGTH = 4;
    public static final int MAX_WIRE_LENGTH = 16;
    public static final int MAGIC = 0x5400;
    public static final int MAGIC_MASK = 0xfc00;
    public static final int MAGIC_SHIFT = 8;
    public static final int RESPONSE_FLAG = 0x200;
    public static final int REQUEST_FLAG = 0x100;
    @Override
    public byte[] encode(VoteMsg voteMsg) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(bos);) {
            //Magic + Flags + Zero使用一个short来表示
            short magicAndFlags = MAGIC;
            if (voteMsg.isInquiry()) {
                magicAndFlags |= REQUEST_FLAG;
            }

            if (voteMsg.isResponse()) {
                magicAndFlags |= RESPONSE_FLAG;
            }
            dos.writeShort(magicAndFlags);
            dos.writeShort(voteMsg.getCandidateId());
            if (voteMsg.isResponse()) {
                dos.writeLong(voteMsg.getVoteCount());
            }
            return bos.toByteArray();
        } catch (Exception e) {
            throw new IOException("encode exception: " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public VoteMsg decode(byte[] input) throws IOException {
        if (input.length < MIN_WIRE_LENGTH) {
            throw new IOException("Runt message");
        }
        try (ByteArrayInputStream bis = new ByteArrayInputStream(input);
             DataInputStream dis = new DataInputStream(bis);) {
            int magicAndFlags = dis.readShort();
            if ((magicAndFlags & MAGIC_MASK) != MAGIC) {
                throw new IOException("Bad magic #:" +((magicAndFlags & MAGIC_MASK) >> MAGIC_SHIFT));
            }
            boolean isInquiry = (magicAndFlags & REQUEST_FLAG) != 0;
            boolean isResponse = (magicAndFlags & RESPONSE_FLAG) != 0;
            int candidateId = dis.readShort();
            if (candidateId < 0 || candidateId > 1000) {
                throw new IOException("Bad candidateId: " + candidateId);
            }
            long voteCount = 0;
            if(isResponse){
                voteCount = dis.readLong();
                if(voteCount < 0){
                    throw new IOException("Bad vote count: " + voteCount);
                }
            }
            //忽略多余的data
            return new VoteMsg(isInquiry,isResponse,candidateId,voteCount);
        } catch (Exception e) {
            throw new IOException("decode exception:" + e.getMessage(), e.getCause());
        }
    }
}
