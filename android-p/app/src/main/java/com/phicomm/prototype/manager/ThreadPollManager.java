package com.phicomm.prototype.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理类
 * Created by qisheng.lv on 2017/4/19.
 */
public class ThreadPollManager {
    private static volatile ThreadPollManager mInstance;
    private ThreadPoolExecutor mExecutor;
    private int mCorePoolSize = 3;    // //核心线程数，除非allowCoreThreadTimeOut被设置为true，否则它闲着也不会死
    private int mMaximumPoolSize = 5;    //最大线程数，活动线程数量超过它，后续任务就会排队
    private long mKeepAliveTime = 5; //超出核心线程数的线程在空闲时的存活时间，单位秒

    /**
     * 执行Runnable任务
     * @param task
     */
    public void executeTask(Runnable task) {
        if (task != null) {
            initPool();
            mExecutor.execute(task);
        }
    }

    /**
     * 初始化线程池
     */
    private void initPool() {
        if (mExecutor == null || mExecutor.isShutdown()) {
            BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();// 实现了线程安全的先进先出的工作队列
            ThreadFactory threadFactory = Executors.defaultThreadFactory();//线程工厂
            RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();//线程运行异常捕获器

            mExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize, mKeepAliveTime,
                    TimeUnit.SECONDS, workQueue, threadFactory, handler);
        }
    }

    public void removeTask(Runnable task) {
        if (mExecutor != null) {
            mExecutor.getQueue().remove(task);
        }
    }

    private ThreadPollManager(){

    }

    public static ThreadPollManager getInstance() {
        if (mInstance == null) {
            synchronized (ThreadPollManager.class) {
                if (mInstance == null) {
                    mInstance = new ThreadPollManager();
                }
            }
        }
        return mInstance;
    }

}
