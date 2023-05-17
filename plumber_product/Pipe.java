package model.plumber_product;

import model.Cell;
import model.Direction;
import model.Water;

import java.util.HashSet;
import java.util.Set;

public class Pipe extends PlumbingProduct {

    public Pipe(Set<Direction> ends, Cell cell) {

        super(ends, cell);

        if (ends.size() != 2){
            cell.clear();
            throw new IllegalArgumentException("illegal argument for pipe");
        }
    }

    public void rotate(){
        if (isFilled()){
            return;
        }

        Set<Direction> newEnds = new HashSet<>();

        for(Direction end : getEnds()){
            newEnds.add(end.clockwise());
        }

        setEnds(newEnds);
    }

    @Override
    public void fill(Water water) {
        super.fill(water);
    }

}
