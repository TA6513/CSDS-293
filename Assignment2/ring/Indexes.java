package ring;
import java.util.stream.Stream;

// A record representing the indices of a matrix.
public record Indexes(int row, int column) implements Comparable<Indexes> {
    
    // Returns the value at these indices in a given 2D matrix.
    public <S> S value(S[][] matrix) {
        return matrix[row][column];
    }

    // Checks if the indices represent a position on the matrix's diagonal.
    public boolean areDiagonal() {
        return row == column;
    }

    // Generates a stream of indices from 'from' to 'to' inclusive.
    public static Stream<Indexes> stream(Indexes from, Indexes to) {
        return Stream.iterate(from,
            i -> i.row < to.row || (i.row == to.row && i.column < to.column),
            i -> i.column == to.column ? new Indexes(i.row + 1, from.column) : new Indexes(i.row, i.column + 1));
    }

    // Generates a stream of indices from (0, 0) to 'size' inclusive.
    public static Stream<Indexes> stream(Indexes size) {
        return stream(new Indexes(0, 0), size);
    }

    // Generates a stream of indices from (0, 0) to (rows - 1, columns - 1) inclusive.
    public static Stream<Indexes> stream(int rows, int columns) {
        return stream(new Indexes(rows - 1, columns - 1));
    }

    // Compares two sets of indices for ordering purposes.
    @Override
    public int compareTo(Indexes other) {
        int rowComparison = Integer.compare(row, other.row);
        return (rowComparison == 0) ? Integer.compare(column, other.column) : rowComparison;
    }
}
