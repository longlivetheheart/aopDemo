package tx;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Joe
 * @date 2023/5/4
 */
public class TransactionUtils {

    public static void doAfterTransaction(DoTransactionCompletion doTransactionCompletion) {
        // 判断当前是否已经激活事务
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            // 把DoTransactionCompletion这个spi的实现注册到当前的事务
            TransactionSynchronizationManager.registerSynchronization(doTransactionCompletion);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void doTx() {
        // start tx

        TransactionUtils.doAfterTransaction(new DoTransactionCompletion(() -> {
            // 只有在事务成功执行之后才会去执行这个回调函数
            // send MQ...RPC...
        }));

        // end tx
    }


}

class DoTransactionCompletion implements TransactionSynchronization {

    private final Runnable runnable;

    public DoTransactionCompletion(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void afterCompletion(int status) {
        // 判断事务是否成功提交
        if (TransactionSynchronization.STATUS_COMMITTED == status) {
            this.runnable.run();
        }
    }
}
