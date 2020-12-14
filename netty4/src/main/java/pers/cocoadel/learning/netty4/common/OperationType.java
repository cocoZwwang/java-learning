package pers.cocoadel.learning.netty4.common;

import pers.cocoadel.learning.netty4.common.auth.AuthOperation;
import pers.cocoadel.learning.netty4.common.auth.AuthOperationResult;
import pers.cocoadel.learning.netty4.common.keeplive.KeepLiveOperation;
import pers.cocoadel.learning.netty4.common.keeplive.KeepLiveOperationResult;
import pers.cocoadel.learning.netty4.common.order.OrderOperation;
import pers.cocoadel.learning.netty4.common.order.OrderOperationResult;

import java.util.function.Predicate;
import java.util.stream.Stream;

public enum  OperationType {
    Auth(0, AuthOperation.class, AuthOperationResult.class),
    KeepLive(1, KeepLiveOperation.class, KeepLiveOperationResult.class),
    Order(2, OrderOperation.class, OrderOperationResult.class);
    private int opCode;

    private Class<? extends Operation> operationClazz;

    private Class<? extends OperationResult> operationResultClazz;

    OperationType(int opCode,Class<? extends Operation> operationClazz,
                  Class<? extends OperationResult> operationResultClazz){
        this.opCode = opCode;
        this.operationClazz = operationClazz;
        this.operationResultClazz = operationResultClazz;
    }

    public int getOpCode() {
        return opCode;
    }

    public Class<? extends Operation> getOperationClazz() {
        return operationClazz;
    }

    public Class<? extends OperationResult> getOperationResultClazz() {
        return operationResultClazz;
    }

    public static OperationType getOperationTypeFromOpCode(int opCode){
        return getOperationType(operationType -> operationType.opCode == opCode);
    }

    public static OperationType getOperationTypeFromOperationClazz(Class<? extends Operation> operationClazz){
        return getOperationType(operationType -> operationClazz == operationType.operationClazz);
    }

    private static OperationType getOperationType(Predicate<OperationType> predicate){
        OperationType[] operationTypes = OperationType.values();
        return Stream.of(operationTypes)
                .filter(predicate)
                .findFirst()
                .orElse(null);

    }
}
