/*******************************************************************************
 * Copyright (c) 2013. Meghraj Choudhary
 ******************************************************************************/

package com.mraj.webtrimmer.gui.views;

import com.mraj.core.events.EventHandler;
import com.mraj.webtrimmer.gui.MainApplication;
import com.mraj.webtrimmer.gui.views.windows.AbstractWindow;
import com.mraj.webtrimmer.server.main.WebTrimServer;
import com.mraj.webtrimmer.server.main.WebTrimServerStateChange;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

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
public class MainApplicationWindow {
    /**
     * main application window is home page of application that have the gridlayout and placeholders for adding composites
     * <p/>
     * having following items in this windows
     * --- tow composites
     * -------composite1
     * ---------have tabfolder placeholder
     * -------composite2
     * ----------have status bar, and placeholder for add items
     * --------
     * thats all for now
     */

    // cores
    private Shell shell;
    private GridLayout layout;


    // main components
    private Menu menuBar;
    private Composite tabComposite;
    private Composite statusComposite;
    private TabFolder tabFolder;
    private Label statusLabel;


    public MainApplicationWindow(Shell shell) {
        this.shell = shell;
        layout = new GridLayout();
        layout.numColumns = 1;
        shell.setLayout(layout);

        // add a menus first
        menuBar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menuBar);
        this.fillMenuItems(menuBar);

        // Create a two composites now one for tabfolder and one for status bar
        this.addCompositetabFolder();

        // create a status bar
        this.addCompositeStatusBar();
    }

    public void OpenSubWindow(AbstractWindow window) {

    }


    /**
     * add tab item and return tab item, having a composite
     * @param title
     */
    public TabItem AddTab(String title) {
        TabItem tab = new TabItem(tabFolder, SWT.NONE);
        tab.setText(title);

        Composite composite = new Composite(tabFolder, SWT.NONE);
        tab.setControl(composite);
        return tab;
    }

    public Label updateStatus(String status, MouseAdapter ma) {
        if (statusLabel != null) {
            statusLabel.dispose();
        }
        if (status == null)
            return null;
        statusLabel = new Label(statusComposite, SWT.NONE);
        statusLabel.setText(status);
        statusLabel.setBounds(0, 0, 311, 15);
        if (ma != null)
            statusLabel.addMouseListener(ma);
        return statusLabel;
    }

    public Label updateStatus(String status) {
        return this.updateStatus(status, null);
    }

    public Label getCurrentLabel() {
        return statusLabel;
    }

    public void addStatusBarItems() {

    }

    public void setEnabled(boolean n) {
        tabComposite.setEnabled(n);
        statusComposite.setEnabled(n);
        menuBar.setEnabled(n);
    }


    private void addCompositeStatusBar() {
        statusComposite = new Composite(shell, SWT.NONE);
        statusComposite.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 3));
        this.updateStatus("");
//
//        Label lblNewLabel_1 = new Label(composite_4, SWT.NONE);
//        lblNewLabel_1.setBounds(0, 0, 55, 15);
//        lblNewLabel_1.setText("New Label");
    }

    private void addCompositetabFolder() {
        // lets add composite view for tabs and add add folder in it
        tabComposite = new Composite(shell, SWT.NONE);
        tabComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
        GridData gd_composite_3 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_composite_3.heightHint = 208;
        tabComposite.setLayoutData(gd_composite_3);


        tabFolder = new TabFolder(tabComposite, SWT.NONE);
    }

    private void fillMenuItems(Menu menu) {
        // Server Menu
        MenuItem mntmServer = new MenuItem(menu, SWT.CASCADE);
        mntmServer.setText("Server");

        Menu serverMenu = new Menu(mntmServer);
        mntmServer.setMenu(serverMenu);

        // "Start Server" Menu Item
        final MenuItem mntmServerStart = new MenuItem(serverMenu, SWT.NONE);
        mntmServerStart.setText("Start Server");
        mntmServerStart.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                MainApplication.getMain().getServer().start();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {
                MainApplication.getMain().getServer().stop();
            }
        });


        // "Stop Server" Menu Item
        final MenuItem mntmServerStop = new MenuItem(serverMenu, SWT.NONE);
        mntmServerStop.setText("Stop Server");
        mntmServerStop.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                MainApplication.getMain().getServer().stop();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {
                MainApplication.getMain().getServer().stop();
            }
        });

        // control for both of two items above
        MainApplication.getMain().getServer().getEvStateChangeBroker().on(new EventHandler<WebTrimServerStateChange>() {
            @Override
            public void handle(final WebTrimServerStateChange e) {

                MainApplication.getUiRunner().runAsync(new Runnable() {
                    @Override
                    public void run() {
                        Display.getCurrent().beep();
                        if (e.newState == WebTrimServer.STATE_RUNNING) {
                            mntmServerStart.setEnabled(false);
                            mntmServerStop.setEnabled(true);
                        } else if (e.newState == WebTrimServer.STATE_NORMAL) {
                            mntmServerStart.setEnabled(true);
                            mntmServerStop.setEnabled(false);
                        }
                    }
                });
            }
        });

    }
}
