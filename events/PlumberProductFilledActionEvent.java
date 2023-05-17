package model.events;

import java.util.EventObject;

public class PlumberProductFilledActionEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public PlumberProductFilledActionEvent(Object source) {
        super(source);
    }
}
