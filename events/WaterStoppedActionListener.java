package model.events;

import java.util.EventListener;

public interface WaterStoppedActionListener extends EventListener {

    /** обрабатывает события остановки потока
     *
     * @param event - евент
     */
    void waterStopped(WaterStoppedActionEvent event);
}
