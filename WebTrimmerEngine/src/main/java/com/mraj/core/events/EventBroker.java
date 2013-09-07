package com.mraj.core.events;


import java.util.Vector;


/**
 * **********************************************
 * <p/>
 * WebTrimmer
 * <p/>
 * this file is part of WebTrimmer project
 *
 * @author Meghraj Choudhary
 *         11:17 PM @ 8/19/13
 *         **********************************************
 */
public class EventBroker<T> {

    private final Vector<EventHandler<T>> vc;

    public EventBroker() {
        vc = new Vector<EventHandler<T>>();
        // is there something to do here
    }

    public void on(EventHandler<T> eslt) {
        vc.add(eslt);
    }

    public void trigger(T event) {
        this.trigger(event, false);
    }

    public void trigger(T event, boolean sync) {
        for (EventHandler<T> handler : vc) {
            handler.handle(event);
        }
    }
}