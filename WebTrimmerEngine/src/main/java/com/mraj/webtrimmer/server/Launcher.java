package com.mraj.webtrimmer.server;

import com.mraj.webtrimmer.server.main.WebTrimServer;

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
public class Launcher {

    public static WebTrimServer server;

    public void main(String[] arg) {
        server = new WebTrimServer();
        server.start();
    }
}
