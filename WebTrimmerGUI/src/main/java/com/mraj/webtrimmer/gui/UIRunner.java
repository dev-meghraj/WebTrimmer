package com.mraj.webtrimmer.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * **********************************************
 * <p/>
 * WebTrimmer
 * <p/>
 * this file is part of WebTrimmer project
 *
 * @author Meghraj Choudhary
 *         5:14 PM @ 8/21/13
 *         **********************************************
 */
public class UIRunner implements Runnable {

    private final static int WINDOW_MINIMUM_WIDTH = 596;
    private final static int WINDOW_MINIMUM_HEIGHT = 425;
    private final static String WINDOW_TITLE = "WebTrimmer 1.0";
    private final static int DEFAULT_SHELL_STYLE = SWT.CLOSE | SWT.MAX | SWT.MIN | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL | SWT.ON_TOP;


    /**
     * its means that UIRunner has started once and we have display and shell objects now
     */
    private boolean isStarted = false;

    private Display display;
    private Shell shell;

    public UIRunner() {

    }

    public void run() {
        // basic
        if (display == null)
            display = new Display();
        if (shell == null)
            shell = new Shell(display, UIRunner.DEFAULT_SHELL_STYLE);
        shell.setMinimumSize(new Point(400, 300));
        shell.setSize(496, 325);
        shell.setText(UIRunner.WINDOW_TITLE);
        shell.setTouchEnabled(true);
        shell.setModified(true);
        shell.setToolTipText(UIRunner.WINDOW_TITLE);
        shell.pack();

        // event loop
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }

    public Display getDisplay() {
        return display;
    }

    public Shell getShell() {
        return shell;
    }

    public void runAsync(Runnable runner1) {
        if (this.getDisplay() != null && !this.getDisplay().isDisposed())
            this.getDisplay().asyncExec(runner1);
    }
}
