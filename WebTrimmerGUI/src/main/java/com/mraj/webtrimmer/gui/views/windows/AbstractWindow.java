package com.mraj.webtrimmer.gui.views.windows;

import com.mraj.webtrimmer.gui.views.MainApplicationWindow;

/**
 * **********************************************
 * <p/>
 * WebTrimmer
 * <p/>
 * this file is part of WebTrimmer project
 *
 * @author Meghraj Choudhary
 *         9:30 PM @ 8/21/13
 *         **********************************************
 */
public abstract class AbstractWindow {

    private MainApplicationWindow mpw;

    public AbstractWindow(MainApplicationWindow mpw) {
        this.mpw = mpw;
    }
}
