package com.mraj.core.utility;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * **********************************************
 * <p/>
 * WebTrimmer
 * <p/>
 * this file is part of WebTrimmer project
 *
 * @author Meghraj Choudhary
 *         8:26 PM @ 8/21/13
 *         **********************************************
 */
public class ThreadResultHandle<T> {

    public ThreadResultHandle(final Callable<T> runner, final ResultHandler<T> pusher, final ExceptionHandler ad) {
        new Thread(new Runnable() {
            @Override
            public void run() {


                ExecutorService es = Executors.newSingleThreadExecutor();
                Future<T> result = es.submit(runner);
                try {
                    T res = result.get();
                    pusher.handleResult(res);
                } catch (Exception e) {
                    ad.handleException(e);
                }

            }
        }).start();
    }
}
