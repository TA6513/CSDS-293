package ring;
import java.util.stream.Stream;

public record Indexes(int row, int column) implements Comparable<Indexes> {
    
    public <S> S value(S[][] matrix) {
        return matrix[row][column];
    }

    public boolean areDiagonal() {
        return row == column;
    }

    public static Stream<Indexes> stream(Indexes from, Indexes to) {
        return Stream.iterate(from, i -> i.row < to.row || (i.row == to.row && i.column < to.column),
                i -> i.column == to.column ? new Indexes(i.row + 1, from.column) : new Indexes(i.row, i.column + 1));
    }

    public static Stream<Indexes> stream(Indexes size) {
        return stream(new Indexes(0, 0), size);
    }

    public static Stream<Indexes> stream(int rows, int columns) {
        return stream(new Indexes(rows - 1, columns - 1));
    }

    @Override
    public int compareTo(Indexes other) {
        int rowComparison = Integer.compare(row, other.row);
        return (rowComparison == 0) ? Integer.compare(column, other.column) : rowComparison;
    }
}