package model.events;

import java.util.EventListener;

public interface PlumberProductFilledActionListener extends EventListener {

    /** обрабатывает события остановки потока
     *
     * @param event - евент
     */
    void plumberProductFilled(PlumberProductFilledActionEvent plumberProductFilledActionEvent);
}
