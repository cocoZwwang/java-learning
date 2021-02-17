package pers.cocoadel.learning.vote.codec;

import pers.cocoadel.learning.vote.bean.VoteMsg;

import java.io.IOException;

/**
 * VoteMsg的编解码器
 */
public interface VoteMsgCoder {
    byte[] encode(VoteMsg voteMsg) throws IOException;

    VoteMsg decode(byte[] input) throws IOException;
}
