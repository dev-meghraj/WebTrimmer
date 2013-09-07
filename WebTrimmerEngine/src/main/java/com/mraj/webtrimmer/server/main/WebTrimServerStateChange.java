package com.mraj.webtrimmer.server.main;

import com.mraj.core.events.Event;

/**
 * **********************************************
 * <p/>
 * WebTrimmer
 * <p/>
 * this file is part of WebTrimmer project
 *
 * @author Meghraj Choudhary
 *         1:59 AM @ 8/21/13
 *         **********************************************
 */
public class WebTrimServerStateChange extends Event {

    public final byte newState;

    public WebTrimServerStateChange(byte newState) {
        this.newState = newState;
    }
}
