/*******************************************************************************
 * Copyright (c) 2013. Meghraj Choudhary
 ******************************************************************************/

package com.mraj.webtrimmer.gui.views.tabs;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabItem;

/**
 * **********************************************
 * <p/>
 * WebTrimmer
 * <p/>
 * this file is part of WebTrimmer project
 *
 * @author Meghraj Choudhary
 *         9:22 AM @ 8/25/13
 *         **********************************************
 */
public abstract class AbstractView  {
    private TabItem tb;
    private Composite comosite;

    public AbstractView(TabItem tb) {
        this.tb=tb;
        this.comosite=(Composite)tb.getControl();
    }

    public Composite getComposite(){
        return this.comosite;
    }
}
