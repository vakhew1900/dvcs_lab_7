package model.plumber_product;

import model.Cell;
import model.Direction;
import model.Water;

import java.util.HashSet;
import java.util.Set;

public class Pipe extends PlumbingProduct {

    public Pipe(Set<Direction> ends, Cell cell) {

        super(ends, cell);

        if (ends.size() != 3){ // commit 10
            cell.clear();
            throw new IllegalArgumentException("illegal argument for pipe");
        }
    }

    public void rotate(){
	
        if (isFilled() == false){ 	// commit 9
		// осуществляем поворот для каждого концаы 	// commit 9
			Set<Direction> newEnds = new HashSet<>(); 	// commit 9
                                                	// commit 9
			for(Direction end : getEnds()){  	// commit 9
				newEnds.add(end.clockwise()); 	// commit 9s
			} 	// commit 9
            	// commit 9
			setEnds(newEnds); 	// commit 9
		} 	// commit 9
    }

    @Override
    public void fill(Water water) {
        super.fill(water);
    }

}
