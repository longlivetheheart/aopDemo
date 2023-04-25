package aop;

/**
 * @author Joe
 * @date 2023/4/25
 */
public interface Convert<PARAM> {

    /**
     * 将入参转换为标准的日志模型
     * @param param
     * @return
     */
    OperateLogDO convert(PARAM param);
}
