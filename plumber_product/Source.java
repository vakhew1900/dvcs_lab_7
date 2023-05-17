package model.plumber_product;

import org.jetbrains.annotations.NotNull;
import model.Cell;
import model.Direction;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Source extends PlumbingProduct {

    /**
     * Конструктор
     * @param ends - множество концов
     * @param cell - клетка, в которой будет расположен наш объект
     */
    private Source(Set<Direction> ends, Cell cell){
        super(ends, cell);

        if(ends.size() != 1){
            throw new IllegalArgumentException("illegal exception");
        }

        if(ends.contains(null)){
            throw new IllegalArgumentException("illegal exception");
        }

    }

    /**
     * Конструктор
     * @param end - конец источника
     * @param cell - клетка, в которой будет расположен наш объект
     */
    protected Source(@NotNull  Direction end, Cell cell){ // commit 9
        this(Stream.of(end).collect(Collectors.toSet()), cell);
    }
}
