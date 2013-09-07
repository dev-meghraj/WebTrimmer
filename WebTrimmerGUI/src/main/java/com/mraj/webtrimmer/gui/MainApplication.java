/*******************************************************************************
 * Copyright (c) 2013. Meghraj Choudhary
 ******************************************************************************/

package com.mraj.webtrimmer.gui;


import com.mraj.core.events.EventHandler;
import com.mraj.webtrimmer.gui.views.MainApplicationWindow;
import com.mraj.webtrimmer.gui.views.tabs.MonitorView;
import com.mraj.webtrimmer.gui.views.tabs.ServerView;
import com.mraj.webtrimmer.gui.views.windows.ServerLogWindow;
import com.mraj.webtrimmer.server.main.WebTrimServer;
import com.mraj.webtrimmer.server.main.WebTrimServerStateChange;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;


/**
 * @author Meghraj Choudhary
 */
public class MainApplication {


    /**
     * Handle all UI Relates things on our own thread for ease of functionality. one UIThread for whole application
     */
    private static UIRunner uiRunner;
    private static Thread uiThread;
    private static MainApplication mainApp;

    /**
     * @var mpw MainApplicationWindow this object will handle all events from server and watch over server and
     * render gui etc. its all main thing for gui
     * it utilize server instance on MainApplication object
     */
    private MainApplicationWindow mpw;
    private GridLayout layout;


    /**
     * @var server WebTrimmer server running on separate thread
     */
    private WebTrimServer server;


    public MainApplication(String[] arg) {
        mainApp = this;
        // Lets do some initializations
        this.initUI();
        this.initApplication();
    }


    /**
     * Shows the MainApplicationWindow now or hoot MainApplicationWindow in ui
     */
    public void run() {
        uiRunner.runAsync(new Runnable() {
            @Override
            public void run() {
                mpw = new MainApplicationWindow(uiRunner.getShell());
                final Label label = mpw.updateStatus("Please wait..");
                if (!(server.hasState(WebTrimServer.STATE_NORMAL) || server.hasState(WebTrimServer.STATE_RUNNING)))
                    mpw.setEnabled(false);

                server.getEvStateChangeBroker().on(new EventHandler<WebTrimServerStateChange>() {
                    @Override
                    public void handle(final WebTrimServerStateChange e) {

                        uiRunner.runAsync(new Runnable() {
                            @Override
                            public void run() {

                                if ((e.newState == WebTrimServer.STATE_NORMAL || e.newState == WebTrimServer.STATE_RUNNING)) {
                                    mpw.setEnabled(true);
                                    if (!label.isDisposed())
                                        label.setText("");
                                    if ((mpw.getCurrentLabel() != null && !mpw.getCurrentLabel().isDisposed() && mpw.getCurrentLabel().getText() == "Please wait.."))
                                        mpw.getCurrentLabel().setText("");
                                } else if (e.newState == WebTrimServer.STATE_LOCKED) {
                                    mpw.setEnabled(false);
                                    mpw.updateStatus("Please wait..");
                                } else {
                                    mpw.setEnabled(false);
                                    mpw.updateStatus("ERROR", new MouseAdapter() {
                                        @Override
                                        public void mouseDown(MouseEvent e) {
                                            mpw.OpenSubWindow(new ServerLogWindow(mpw));
                                        }
                                    });
                                }


                            }
                        });
                    }
                });


                // Add tabs to mpw
                new ServerView(mpw.AddTab("Server"));
                new MonitorView(mpw.AddTab("Monitor"));
            }
        });
    }


    /**
     * inti Ui Components and Let them run on a separate thread
     */
    private void initUI() {
        uiRunner = new UIRunner();
        uiThread = new Thread(uiRunner);
        uiThread.start();
        try {
            while (uiRunner.getDisplay() == null)
                Thread.sleep(100);
        } catch (Exception e) {
            // @todo is there any kind of Exception possible here
            System.err.println(e);
        }
    }

    /**
     * instantiating WebTrimServer and start initializing in background thread
     */
    public void initApplication() {
        server = new WebTrimServer();
        // all the server processing are going to run on separate thread
        server.init();
    }


    // Basic
    public WebTrimServer getServer() {
        return this.server;
    }

    public static UIRunner getUiRunner() {
        return uiRunner;
    }

    public static MainApplication getMain() {
        return mainApp;
    }
}
