package pers.cocoadel.learning.netty4.common.keeplive;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.cocoadel.learning.netty4.common.OperationResult;

@EqualsAndHashCode(callSuper = true)
@Data
public class KeepLiveOperationResult extends OperationResult {
    private long time;
}
