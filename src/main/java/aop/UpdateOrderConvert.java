package aop;

/**
 * @author Joe
 * @date 2023/4/25
 */
public class UpdateOrderConvert implements Convert<UpdateOrder>{
    @Override
    public OperateLogDO convert(UpdateOrder updateOrder) {
        OperateLogDO operateLogDO = new OperateLogDO();
        operateLogDO.setOrderId(updateOrder.getOrderId());
        return operateLogDO;
    }
}
