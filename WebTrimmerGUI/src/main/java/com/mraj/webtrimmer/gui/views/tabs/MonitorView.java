/*******************************************************************************
 * Copyright (c) 2013. Meghraj Choudhary
 ******************************************************************************/

package com.mraj.webtrimmer.gui.views.tabs;

import com.mraj.webtrimmer.gui.MainApplication;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * **********************************************
 * <p/>
 * WebTrimmer
 * <p/>
 * this file is part of WebTrimmer project
 *
 * @author Meghraj Choudhary
 *         9:39 AM @ 8/25/13
 *         **********************************************
 */
public class MonitorView extends AbstractView {

    private Table table;

    public MonitorView(TabItem tb) {
        super(tb);
        MainApplication.getMain().getServer().getLogger().error(this.getComposite());

        GridLayout gl_composite_1 = new GridLayout(1, true);
        gl_composite_1.verticalSpacing = 10;
        gl_composite_1.marginTop = 5;
        gl_composite_1.marginRight = 5;
        gl_composite_1.marginLeft = 5;
        this.getComposite().setLayout(gl_composite_1);

        table = new Table(this.getComposite(), SWT.BORDER | SWT.FULL_SELECTION | SWT.HIDE_SELECTION | SWT.VIRTUAL | SWT.MULTI);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        TableColumn tblclmnNewColumn = new TableColumn(table, SWT.CENTER);
        tblclmnNewColumn.setMoveable(true);
        tblclmnNewColumn.setWidth(100);
        tblclmnNewColumn.setText("New Column");

        TableItem tableItem = new TableItem(table, SWT.NONE);
        tableItem.setChecked(true);
        tableItem.setText("New TableItem");

        TableColumn tblclmnHosy = new TableColumn(table, SWT.NONE);
        tblclmnHosy.setMoveable(true);
        tblclmnHosy.setWidth(100);
        tblclmnHosy.setText("Host");

        TableItem tableItem_1 = new TableItem(table, SWT.NONE);
        tableItem_1.setText("yryyr");

        TableItem tableItem_2 = new TableItem(table, SWT.NONE);
        tableItem_2.setText("New TableItem");
    }
}
