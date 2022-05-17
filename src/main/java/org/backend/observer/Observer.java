package org.backend.observer;

import java.util.ArrayList;
import java.util.List;

//Temporary solution
//Correction in the future
public class Observer {
    private List<IListener> listeners;

    public Observer()
    {
        listeners = new ArrayList<>();
    }

    public void update()
    {
        for (IListener listener:
             listeners) {
            listener.update();
        }
    }

    public void addListener(IListener listener)
    {
        listeners.add(listener);
    }

    public void deleteListener(IListener listener)
    {
        listeners.remove(listener);
    }
    public void deleteAllListeners()
    {
        listeners.clear();
    }
}
