package cocurrent;



import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import util.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

@Slf4j
public class CallbackTaskScheduler {

    static ListeningExecutorService gPool = null;

    static {
        ExecutorService jPool = ThreadUtil.getMixedTargetThreadPool();
        gPool = MoreExecutors.listeningDecorator(jPool);
    }


    private CallbackTaskScheduler() {
    }

    /**
     * 添加任务
     *
     * @param executeTask
     */
    public static <R> void add(CallbackTask<R> executeTask) {
        ListenableFuture<R> future = gPool.submit(new Callable<R>() {
            @Override
            public R call() throws Exception {

                R r = executeTask.execute();
                return r;
            }

        });

        Futures.addCallback(future, new FutureCallback<R>() {
            @Override
            public void onSuccess(R r) {
                executeTask.onBack(r);
            }

            @Override
            public void onFailure(Throwable t) {
                executeTask.onException(t);
            }
        });
    }
}
