package pers.cocoadel.learning.netty4.common;

public abstract class Operation extends MessageBody{

    public abstract OperationResult executor();
}
