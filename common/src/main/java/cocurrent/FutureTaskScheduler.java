package cocurrent;


import lombok.extern.slf4j.Slf4j;
import util.ThreadUtil;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class FutureTaskScheduler
{
    static ThreadPoolExecutor mixPool = null;

    static {
        mixPool = ThreadUtil.getMixedTargetThreadPool();
    }

    private FutureTaskScheduler()
    {

    }

    /**
     * 添加任务
     *
     * @param executeTask
     */


    public static void add(Runnable executeTask) {
        mixPool.submit(()->{ executeTask.run(); });
    }

}
