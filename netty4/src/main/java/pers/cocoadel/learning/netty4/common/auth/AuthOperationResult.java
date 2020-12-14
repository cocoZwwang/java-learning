package pers.cocoadel.learning.netty4.common.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.cocoadel.learning.netty4.common.OperationResult;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthOperationResult extends OperationResult {
    private boolean pass;
}
