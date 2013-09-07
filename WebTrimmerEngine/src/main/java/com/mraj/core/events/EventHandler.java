package com.mraj.core.events;

/**
 * **********************************************
 * <p/>
 * WebTrimmer
 * <p/>
 * this file is part of WebTrimmer project
 *
 * @author Meghraj Choudhary
 *         11:56 PM @ 8/19/13
 *         **********************************************
 */
public interface EventHandler<T> {

    public abstract void handle(T e);

}
