/*******************************************************************************
 * Copyright (c) 2013. Meghraj Choudhary
 ******************************************************************************/

package com.mraj.webtrimmer.server.main;

import com.mraj.core.events.EventBroker;
import com.mraj.core.utility.ExceptionHandler;
import com.mraj.core.utility.ResultHandler;
import com.mraj.core.utility.ThreadResultHandle;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

/**
 * **********************************************
 * <p/>
 * WebTrimmer
 * <p/>
 * this file is part of WebTrimmer project
 *
 * @author Meghraj Choudhary
 *         {TIME} @ 8/19/13
 *         **********************************************
 */
public class WebTrimServer {

    /**
     * WebTrimServer is main part of application, its handle all kind of plugins loaded into server, what kind of
     * service server will going to run based on xml files,
     * its start, stop method is always runs on new Threads
     */

    public static final byte STATE_LOCKED = 1;
    public static final byte STATE_NORMAL = 2;
    public static final byte STATE_RUNNING = 3;
    public static final byte STATE_ERROR = 4;


    private static final Logger logger = Logger.getLogger(WebTrimServer.class);
    private boolean running = false;
    private byte state = 1;

    private final EventBroker<WebTrimServerStateChange> evStateChange = new EventBroker<WebTrimServerStateChange>();


    public WebTrimServer() {
        // Load Main Components
        BasicConfigurator.configure();
        logger.debug("Hello world.");
        logger.info("What a beautiful day.");
    }

    public void init() {
        final WebTrimServer srvr = this;
        this.changeState(WebTrimServer.STATE_LOCKED);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Init Server components,config etc
                try {
                    Thread.sleep(4000);
                } catch (Exception e) {
                    logger.error("Exception from server Thread", e);
                }
                srvr.changeState(WebTrimServer.STATE_NORMAL);
            }
        }).start();
    }

    public boolean getIsInitialised() {
        return this.hasState(WebTrimServer.STATE_NORMAL);
    }


    /**
     * Trying to stop currently running server on a thread
     */
    public synchronized void start(final ResultHandler<Boolean> cb) {
        final WebTrimServer srvr = this;
        if (this.hasState(WebTrimServer.STATE_LOCKED)) {
            cb.handleResult(null);
            return;
        } else
            srvr.changeState(WebTrimServer.STATE_LOCKED);

        new ThreadResultHandle<Boolean>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                /// Start Stopping server here, its a new Thread
                try {
                    Thread.sleep(4000);
                } catch (Exception e) {
                    srvr.getLogger().error("Exception from server Thread", e);
                }
                srvr.changeState(WebTrimServer.STATE_RUNNING);


                return true;
            }
        }, cb, new ExceptionHandler() {
            @Override
            public void handleException(Exception e) {
                //@todo handle correct Exceptions here later
                cb.handleResult(false);
            }
        }
        );

    }

    public synchronized void start() {
        this.start(new ResultHandler<Boolean>() {
            @Override
            public void handleResult(Boolean e) {

            }
        });
    }

    /**
     * Trying to stop currently running server on a thread
     */
    public synchronized void stop(final ResultHandler<Boolean> cb) {
        final WebTrimServer srvr = this;
        if (this.hasState(WebTrimServer.STATE_LOCKED)) {
            cb.handleResult(null);
            return;
        } else
            srvr.changeState(WebTrimServer.STATE_LOCKED);

        new ThreadResultHandle<Boolean>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                /// Start Stopping server here, its a new Thread
                try {
                    Thread.sleep(4000);
                } catch (Exception e) {
                    srvr.getLogger().error("Exception from server Thread", e);
                }
                srvr.changeState(WebTrimServer.STATE_NORMAL);

                return true;
            }
        }, cb, new ExceptionHandler() {
            @Override
            public void handleException(Exception e) {
                //@todo handle correct Exceptions here later
                cb.handleResult(false);
            }
        }
        );

    }

    public synchronized void stop() {
        this.stop(new ResultHandler<Boolean>() {
            @Override
            public void handleResult(Boolean e) {

            }
        });
    }


    public byte getState() {
        return this.state;
    }

    public boolean hasState(byte stat) {
        return stat == this.state;
    }

    private synchronized void changeState(byte newState) {
        this.state = newState;
        this.getEvStateChangeBroker().trigger(
                new WebTrimServerStateChange(newState)
        );
    }

    public Logger getLogger() {
        return WebTrimServer.logger;
    }


    /**
     * Getters for all events
     */
    public EventBroker<WebTrimServerStateChange> getEvStateChangeBroker() {
        return evStateChange;
    }

}



