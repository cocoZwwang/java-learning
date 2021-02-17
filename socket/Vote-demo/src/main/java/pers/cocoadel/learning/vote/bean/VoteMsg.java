package pers.cocoadel.learning.vote.bean;

public class VoteMsg {
    //true：表示消息是查询请求；false：表示消息是投票信息
    private boolean isInquiry;
    //true：表示消息是响应消息；false：表示消息是请求消息
    private boolean isResponse;
    //候选人ID
    private int candidateId;
    //所查询的候选人获得的总票数
    private long voteCount;

    public static final int MAX_CANDIDATE_ID = 1000;

    public VoteMsg(boolean isInquiry, boolean isResponse, int candidateId, long voteCount) throws IllegalArgumentException{
        if (voteCount != 0 && !isResponse) {
            throw new IllegalArgumentException("Request vote count must be 0");
        }
        if (candidateId < 0 || candidateId > MAX_CANDIDATE_ID) {
            throw new IllegalArgumentException("Bad Candidate Id: " + candidateId);
        }
        if (voteCount < 0) {
            throw new IllegalArgumentException("total vote count must be >= 0");
        }

        this.isInquiry = isInquiry;
        this.isResponse = isResponse;
        this.candidateId = candidateId;
        this.voteCount = voteCount;
    }

    public boolean isInquiry() {
        return isInquiry;
    }

    public void setInquiry(boolean inquiry) {
        isInquiry = inquiry;
    }

    public boolean isResponse() {
        return isResponse;
    }

    public void setResponse(boolean response) {
        isResponse = response;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        if(candidateId < 0 || candidateId > MAX_CANDIDATE_ID){
            throw new IllegalArgumentException("Bad candidate Id: " + candidateId);
        }
        this.candidateId = candidateId;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        if ((voteCount != 0 && !isResponse) || voteCount < 0) {
            throw new IllegalArgumentException("Bad vote count");
        }
        this.voteCount = voteCount;
    }

    public String toString() {
        String res = (isInquiry ? "inquiry" : "vote") + " for candidate " + candidateId;
        if(isResponse){
            res = "response to " + res + "  who now has " + voteCount + " vote(s)";
        }
        return res;
    }
}
