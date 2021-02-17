package pers.cocoadel.learning.vote.service;

import pers.cocoadel.learning.vote.bean.VoteMsg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class VoteMsgService {
    private final Map<Integer, AtomicLong> voteCounts = new ConcurrentHashMap<>();

    public VoteMsg handleRequest(VoteMsg msg) {
        if (msg.isResponse()) {
            return msg;
        }
        msg.setResponse(true);
        int candidateId = msg.getCandidateId();
        AtomicLong autoMicVoteCount = voteCounts.computeIfAbsent(candidateId, k -> new AtomicLong(0));
        if (!msg.isInquiry()) {
            autoMicVoteCount.incrementAndGet();
        }
        long voteCount = autoMicVoteCount.get();
        msg.setVoteCount(voteCount);
        return msg;
    }
}
