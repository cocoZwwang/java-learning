package pers.cocoadel.learning.netty4.common;


public class RequestMessage extends Message<Operation>{

    @Override
    protected Class<? extends Operation> getMessageBodyClassByOpCode(int code) {
        return OperationType.getOperationTypeFromOpCode(code).getOperationClazz();
    }

    public RequestMessage(){

    }

    public static RequestMessage createRequestMessage(long streamId,Operation operation){
        OperationType operationType = OperationType.getOperationTypeFromOperationClazz(operation.getClass());
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setStreamId(streamId);
        messageHeader.setOpCode(operationType.getOpCode());
        messageHeader.setVersion(1);

        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setMessageHeader(messageHeader);
        requestMessage.setMessageBody(operation);
        return requestMessage;
    }
}
