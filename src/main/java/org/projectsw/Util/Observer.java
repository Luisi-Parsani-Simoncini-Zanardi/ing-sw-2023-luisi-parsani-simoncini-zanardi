package org.projectsw.Util;

import java.rmi.RemoteException;

/**
 * A class can implement the {@code Observer} interface when it
 * wants to be informed of changes in observable objects.
 *
 * @see     Observable
 *
 * @param <SubjectType> the type of the observable object
 *                     that this observer is observing
 * @param <Event> the enumeration of the event that this observer is observing
 *
 * @implNote
 * This class is a Generic Implementation of the deprecated {@link java.util.Observer}.
 */
public interface Observer<SubjectType extends Observable<Event>, Event extends Enum<Event>> {
    /**
     * This method is called whenever the observed object is changed. An
     * application calls an {@code Observable} object's
     * {@code notifyObservers} method to have all the object's
     * observers notified of the change.
     *
     * @param   o     the observable object.
     * @param   arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */

    void update(SubjectType o, Event arg) throws RemoteException;
}
