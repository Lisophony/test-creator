package com.lisa.testcreator;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private List<EventListener> listeners = new ArrayList<>();

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public void onTriggered() {
        for(EventListener listener : listeners) {
            listener.onTriggered();
        }
    }
}
