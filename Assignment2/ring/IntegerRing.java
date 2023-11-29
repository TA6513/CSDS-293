package ring;

import java.util.Objects;

//ring that supports the Integer type
public class IntegerRing implements Ring<Integer> {
    @Override
    public Integer zero() {
        return 0;
    }

    @Override
    public Integer identity() {
        return 1;
    }

    @Override
    public Integer sum(Integer x, Integer y) {
        if(x == null && y != null) {
            return y;
        }
        else if(y == null && x != null) {
            return x;
        }
        else if(x == null && y == null) {
            return 0;
        }
        else {
            return x + y;
        }
    }

    @Override
    public Integer product(Integer x, Integer y) { 
        if(x == null || y == null) {
            return 0;
        }
        else {
            return x * y;
        }
    }
}
