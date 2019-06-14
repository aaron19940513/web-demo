package com.sam.demo.helper;

import com.google.common.collect.Maps;


import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class ThreadPoolHelper {

    private static Map<String, Executor> executorMap = Maps.newConcurrentMap();

    /**
     * Database query executor executor.
     *
     * @param bizKeyEnum the biz key enum
     * @return the executor
     */
    public static Executor obtainThreadPool(AsyncConst.BizKeyEnum bizKeyEnum) {
        Executor executor = executorMap.get(bizKeyEnum.getBizKey());
        if (executor != null) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            if (threadPoolExecutor.getActiveCount() >= bizKeyEnum.getThreadPoolSize()) {
                return Executors.newSingleThreadExecutor(r -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                });
            }
        }
        Executor newFixedThreadPool = Executors.newFixedThreadPool(bizKeyEnum.getThreadPoolSize(), r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        executorMap.put(bizKeyEnum.getBizKey(), newFixedThreadPool);
        return newFixedThreadPool;
    }
}
