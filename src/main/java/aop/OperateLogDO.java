package aop;

import lombok.Data;

/**
 * @author Joe
 */
@Data
public class OperateLogDO {

    private Long orderId;

    private String desc;

    private String result;

}
