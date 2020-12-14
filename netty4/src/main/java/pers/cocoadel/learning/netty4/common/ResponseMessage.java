package pers.cocoadel.learning.netty4.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseMessage extends Message<OperationResult>{
    @Override
    protected Class<? extends OperationResult> getMessageBodyClassByOpCode(int code) {
        return OperationType.getOperationTypeFromOpCode(code).getOperationResultClazz();
    }

    @Override
    public String toString() {
        return String.format("ResponseMessage{%s}",super.toString());
    }
}

