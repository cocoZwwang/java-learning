package pers.cocoadel.learning.netty4.common.order;

import com.google.common.util.concurrent.Uninterruptibles;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import pers.cocoadel.learning.netty4.common.Operation;
import pers.cocoadel.learning.netty4.common.OperationResult;

import java.util.concurrent.TimeUnit;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class OrderOperation extends Operation {
    private int tableId;
    private String dish;

    public OrderOperation(int tableId,String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }

    @Override
    public OperationResult executor() {
        log.info("order's executing startup with orderRequest: " + toString());
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        //execute order logic
        log.info("order's executing complete");
        OrderOperationResult orderOperationResult = new OrderOperationResult();
        orderOperationResult.setTableId(tableId);
        orderOperationResult.setDish(dish);
        orderOperationResult.setComplete(true);
        return orderOperationResult;
    }
}
