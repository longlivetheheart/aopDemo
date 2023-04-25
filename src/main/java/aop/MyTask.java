package aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Joe
 * @date 2023/4/25
 */
@Component
@EnableScheduling
public class MyTask {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0/3 * * * * ?")
    public void doTask() {
        UpdateOrder updateOrder = new UpdateOrder();
        updateOrder.setOrderId(4L);
        SaveOrder saveOrder = new SaveOrder();
        saveOrder.setId(3L);
        orderService.saveOrder(saveOrder);
        orderService.updateOrder(updateOrder);
    }
}
