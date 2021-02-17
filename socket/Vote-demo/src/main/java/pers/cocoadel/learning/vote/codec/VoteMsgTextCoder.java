package pers.cocoadel.learning.vote.codec;

import pers.cocoadel.learning.vote.bean.VoteMsg;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 基于文本的编解码实现
 * Write Format "VOTEPROTO" <"v"|"i"> [<RESPFLAG>]<CANDIDATE> [<VOTECNT>]
 */
public class VoteMsgTextCoder implements VoteMsgCoder {
    public static final int MIN_WIRE_LENGTH = 4;

    public static final int MAX_WIRE_LENGTH = 2000;

    public static final String MAGIC = "Voting";

    public static final String VOTE_STR = "v";

    public static final String INQ_STR = "i";

    public static final String RESPONSE_STR = "R";

    public static final String CHARSET_NAME = StandardCharsets.UTF_8.name();

    public static final String DELIMIT_STR = " ";

    @Override
    public byte[] encode(VoteMsg voteMsg) throws IOException {
        String sb = MAGIC + DELIMIT_STR +
                (voteMsg.isInquiry() ? INQ_STR : VOTE_STR) + DELIMIT_STR +
                (voteMsg.isResponse() ? RESPONSE_STR + DELIMIT_STR : "") +
                voteMsg.getCandidateId() + DELIMIT_STR +
                voteMsg.getVoteCount();
        return sb.getBytes(CHARSET_NAME);
    }

    @Override
    public VoteMsg decode(byte[] input) throws IOException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(input);
             Scanner scanner = new Scanner(new InputStreamReader(bis,CHARSET_NAME));) {
            boolean isInquiry;
            boolean isResponse = false;
            int candidateId;
            long voteCount;

            String token = scanner.next();
            if(!MAGIC.equals(token)){
                throw new IOException("Bad magic string: " + token);
            }

            token = scanner.next();
            if(VOTE_STR.equals(token)){
                isInquiry = false;
            }else if(INQ_STR.equals(token)){
                isInquiry = true;
            }else{
                throw new IOException("Bad vote/inq indicator: " + token);
            }

            token = scanner.next();
            if(RESPONSE_STR.equals(token)){
                isResponse = true;
                token = scanner.next();
            }

            candidateId = Integer.parseInt(token);

            if(isResponse){
                token = scanner.next();
                voteCount = Long.parseLong(token);
            }else{
                voteCount = 0;
            }
            return new VoteMsg(isInquiry,isResponse,candidateId,voteCount);
        }catch (Exception e){
            throw new IOException("parse exception " + e.getClass().getName() +":"+e.getMessage(),e.getCause());
        }
    }
}
