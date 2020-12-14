package pers.cocoadel.learning.netty4.common.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pers.cocoadel.learning.netty4.common.Operation;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthOperation extends Operation {
    private String userName;

    private String password;

    @Override
    public AuthOperationResult executor() {
        AuthOperationResult result = new AuthOperationResult();
        result.setPass("cocoAdel".equals(userName) && "123456".equals(password));
        return result;
    }
}
