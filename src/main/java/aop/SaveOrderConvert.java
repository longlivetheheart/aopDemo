package aop;

/**
 * @author Joe
 * @date 2023/4/25
 */
public class SaveOrderConvert implements Convert<SaveOrder> {
    @Override
    public OperateLogDO convert(SaveOrder saveOrder) {
        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOrderId(saveOrder.getId());
        return operateLogDO;
    }
}
