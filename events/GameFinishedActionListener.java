package model.events;

import java.util.EventListener;

public interface GameFinishedActionListener extends EventListener {

    void gameFinished(GameFinishedActionEvent event);
}
