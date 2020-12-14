package pers.cocoadel.learning.netty4.common.keeplive;

import pers.cocoadel.learning.netty4.common.Operation;
import pers.cocoadel.learning.netty4.common.OperationResult;

public class KeepLiveOperation extends Operation {

    private long time;

    public KeepLiveOperation(){
        this.time = System.currentTimeMillis();
    }

    /**
     * 本地业务操作
     */
    @Override
    public OperationResult executor() {
        KeepLiveOperationResult result = new KeepLiveOperationResult();
        result.setTime(time);
        return result;
    }
}
