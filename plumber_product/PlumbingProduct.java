package model.plumber_product;

import model.events.PlumberProductFilledActionEvent;
import model.events.PlumberProductFilledActionListener;
import model.events.WaterStoppedActionEvent;
import model.events.WaterStoppedActionListener;
import org.jetbrains.annotations.NotNull;
import model.Cell;
import model.Direction;
import model.Water;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class PlumbingProduct {

    /**
     * Множество концов
     */
    private Set<Direction> ends = new HashSet<>();

    /**
     * Клетка, в  которой расположен объет
     */
    private Cell cell;

    // ------------------ Конструктор ----------------------------------------------

    /**
     * геттер для ends
     *
     * @return ends - множество концов
     */
    public Set<Direction> getEnds() {
        return ends;
    }

    /**
     *  сеттер для ends
     * @param ends - множество концов
     */
    protected void setEnds(@NotNull Set<Direction> ends) {
        this.ends = ends;
    }

    /**
     * Конструктор
     * @param ends - множество концов
     * @param cell - клетка, в которой будет расположен наш объект
     */
    public PlumbingProduct(@NotNull Set<Direction> ends, Cell cell) {


        if (cell == null || ends == null || ends.size() == 0) {
            throw new IllegalArgumentException("Illegal argument for PlumbingProduct");
        }

        this.ends = new HashSet<Direction>(ends);
        this.cell = cell;
        cell.fill(this);
    }


    // --------------------- взаимодействие с водой ----------------------------------

    /** Вода, заполняющая сантехническое изделие*/
    private Water water = null;

    /**
     * Заполняет сантехническое изделие водой
     * @param water - вода
     */
    public void fill(@NotNull Water water) {


        if (isFilled() == false) {
            this.water = water;
            if (water.getLastFillingPlumbingProduct() == null || water.getLastFillingPlumbingProduct().equals(this) == false) {
                water.nextPlumbingProduct(this);
            }
        }

        firePlumberProductAction();
    }

    /**
     * Проверяет, заполнена ли сантехническое изделие водой
     * @return true - если сантехническое изделие заполнено водой, иначе false
     */
    public boolean isFilled() {
        return water != null;
    }

    /**
     * геттер для water
     * @return
     */
    public Water water() {
        return water;
    }

    // --------------------------- работа с  концами ------------------------------------

    /**
     * Проверяет есть ли у сантехнического изделия есть заданное направление конца
     * @param direction - заданное направление
     * @return true - если у сантехнического изделия есть заданное направление конца, иначе false
     */
    public boolean hasEnd(Direction direction) {
        return ends.contains(direction);
    }

    public PlumbingProduct neighbor(Direction direction) {

        Cell neighborCell = cell.neighbor(direction);

        if (neighborCell != null) {
            return neighborCell.getPlumbingProduct();
        }

        return null;
    }

    // ----------------------------- взаимодействие с трубой -----------------------------

    /**
     * проверяет возможно заполнить другого сантехническое изделие из заданногого.
     * @param other - другая труба
     * @return  true - если возможно заполнить другого сантехническое изделие из заданногого - false
     */
    public boolean isCanFilled(PlumbingProduct other) {
        return other != null && other.isFilled() == false && isConnected(other);
    }

    /**
     * Проверяет соедино ли данное сантехническое изделие с другим
     * @param other - другое сантехническое изделие
     * @return true - если  соедино ли данное сантехническое изделие с другим
     */
    public boolean isConnected(PlumbingProduct other) {

        if (other == null)
            return false;

        Direction direction = null;

        for (Direction elem : ends) {

            if (neighbor(elem) != null && neighbor(elem).equals(other)) {
                direction = elem;
            }
        }

        return direction != null && other.hasEnd(direction.opposite());
    }


    //------------------------ Системные ----------------------------



    @Override
    public String toString() {

        String res = this.getClass().toString() + ":" + "Cell :" + this.cell.toString() + " Directions :";

        for (Direction direction : getEnds()) {
            res += direction.toString() + " ";
        }
        return res;
    }

    //TODO    !!!
    List<PlumberProductFilledActionListener> plumberProductFilledActionListeners = new ArrayList<>();

    // присоединяет слушателя
    public void addPlumberProductFilledActionListener(PlumberProductFilledActionListener l) {

        if (plumberProductFilledActionListeners.contains(l) == false)
            plumberProductFilledActionListeners.add(l);
    }

    // отсоединяет слушателя
    public void removeFlowActionListener(WaterStoppedActionListener l) {
        if (plumberProductFilledActionListeners.contains(l)) {
            plumberProductFilledActionListeners.remove(l);
        }
    }

    // оповещает слушателей о событии
    protected void firePlumberProductAction() {
        for (PlumberProductFilledActionListener plumberProductFilledActionListener : plumberProductFilledActionListeners) {
            plumberProductFilledActionListener.plumberProductFilled(new PlumberProductFilledActionEvent(this));
        }
    }
}
