package pers.cocoadel.learning.netty4.common.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.cocoadel.learning.netty4.common.OperationResult;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderOperationResult extends OperationResult {

    private int tableId;

    private String dish;

    private boolean complete;
}
