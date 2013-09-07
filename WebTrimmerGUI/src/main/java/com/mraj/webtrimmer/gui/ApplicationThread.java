
package com.mraj.webtrimmer.gui;


public class ApplicationThread extends Thread implements Runnable {

    private MainApplication app;

    public ApplicationThread(MainApplication app) {
        this.app = app;
    }

    public void run() {
        app.run();
        while (true) {

        }
    }

}
