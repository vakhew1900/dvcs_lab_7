package model;

import model.events.GameFinishedActionEvent;
import model.events.GameFinishedActionListener;
import model.events.WaterStoppedActionEvent;

import model.events.WaterStoppedActionListener;
import model.plumber_product.Drain;
import model.plumber_product.Pipe;
import model.plumber_product.PlumbingProduct;
import model.plumber_product.Source;

import java.util.ArrayList;
import java.util.List;

public class Game implements WaterStoppedActionListener {

    /**
     * Статус игры - идет
     */
    public final static int RUNNING = 0; // commit 2


    /**
     * Статус игры - проигрыш
     */
    public final static int LOSE = 2; // commit 2

    /**
     * статус игры - победа
     */
    public final static int WIN = 3; // commit 2

    public final static int WATER_START = 4; // commit 2

    /**
     * Статус игры
     */
    private int status = RUNNING;

    /**
     * Игровое поле
     */
    private GameField gameField;

    /**
     * Сантехник, что настраивает поле
     */
    private Plumber plumber;

    /**
     * Вода
     */
    private Water water;

    /**
     * Размер поля
     */
    private final int SIZE = 8;

    /**
     * Конструктор
     */
    public Game() {

        init();
    }


    /**
     * Получить игровое поле
     * @return игровое поле
     */
    public GameField gamefield() {
        return gameField;
    }

    /**
     * Инициализация игры
     */
    private void init() {

        gameField = new GameField(SIZE, SIZE);
        plumber = new Plumber(gameField);
        plumber.configure();
        this.water = source().water();

        water.addWaterStoppedActionListener(this);
       // drain().addWaterStoppedActionListener(this);

    }


    /**
     * Получить источник с игрового поля
     * @return источник
     */
    Source source() {

        for (int i = 0; i < gameField.height(); i++) {

            PlumbingProduct plumbingProduct = gameField.cell(i, 0).getPlumbingProduct();
            if (plumbingProduct instanceof Source) {
                return (Source) plumbingProduct;
            }

        }

        return null;
    }

    /**
     * Получить слив с игрового поля
     * @return источник
     */
    Drain drain() {
        for (int i = 0; i < gameField.height(); i++) {

            PlumbingProduct plumbingProduct = gameField.cell(i, gameField.width() - 1).getPlumbingProduct();
            if (plumbingProduct instanceof Drain) {
                return (Drain) plumbingProduct;
            }

        }

        return null;
    }

    /**
     * запустить движение воды
     */
    public void flowWater() {
        water.start();
    }

    /**
     * Остановить игру
     * @param obj
     */
    private void finish(Object obj) {
        status = (obj instanceof Drain)? WIN : LOSE;
        fireGameFinishedAction();
    }


    /**
     * Получить игровой статус
     * @return
     */
    public int status() {
        return status;
    }

    /**
     * Повернуть трубу в клетке с координатами (row; col)
     * @param row - строка
     * @param col - колонка
     */
    public void rotate(int row, int col) {

        if (row < 0 || col < 0 || gamefield().height() <= row || gamefield().width() <= col) {
            throw new IllegalArgumentException("Illegal row or col");
        }

        PlumbingProduct plumbingProduct = gameField.cell(row, col).getPlumbingProduct();

        if (plumbingProduct != null) {
            ((Pipe) plumbingProduct).rotate();
        }

    }

    /**
     * Напечатать все обстановку на поле
     */
    public void printAllPipeline() {

        for (int i = 0; i < this.gamefield().height(); i++) {
            for (int j = 0; j < this.gamefield().height(); j++) {
                PlumbingProduct plumbingProduct = this.gamefield().cell(i, j).getPlumbingProduct();
                if (plumbingProduct != null) {
                    System.out.println(plumbingProduct);
                }
            }
        }
    }

    /**
     * Обработка события течения воды
     * @param event - евент
     */
    @Override
    public void waterStopped(WaterStoppedActionEvent event) {
        finish(((Water) event.getSource()).getLastFillingPlumbingProduct());
    }
    
    
    
    //----------------------------- Cлушатели

    List<GameFinishedActionListener> gameFinishedListeners = new ArrayList<>();

    // присоединяет слушателя
    public void addGameFinishedActionListener(GameFinishedActionListener l) {

        if (gameFinishedListeners.contains(l) == false)
            gameFinishedListeners.add(l);
    }

    // отсоединяет слушателя
    public void removegameFinishedListener(GameFinishedActionListener l) {
        if (gameFinishedListeners.contains(l)) {
            gameFinishedListeners.remove(l);
        }
    }

    // оповещает слушателей о событии
    protected void fireGameFinishedAction() {
        for (GameFinishedActionListener gameFinishedListener : gameFinishedListeners) {
            gameFinishedListener.gameFinished(new GameFinishedActionEvent(this));
        }
    }
}
