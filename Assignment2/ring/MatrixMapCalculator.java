package ring;

import java.util.Scanner;
import java.util.function.Function;

public class MatrixMapCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Matrix Calculator");
        System.out.print("Enter the number of rows: ");
        int rows = scanner.nextInt();

        System.out.print("Enter the number of columns: ");
        int columns = scanner.nextInt();

        System.out.println("Enter values for the first matrix:");
        MatrixMap<Integer> matrix1 = createMatrix(rows, columns, scanner);

        System.out.println("Enter values for the second matrix:");
        MatrixMap<Integer> matrix2 = createMatrix(rows, columns, scanner);

        System.out.println("Select an operation:");
        System.out.println("1. Addition");
        System.out.println("2. Multiplication");
        int choice = scanner.nextInt();

        MatrixMap<Integer> resultMatrix = null;
        switch (choice) {
            case 1:
                resultMatrix = matrix1.plus(matrix2, Integer::sum);
                break;
            case 2:
                resultMatrix = matrix1.times(matrix2, new IntegerRing());
                break;
            default:
                System.out.println("Invalid choice");
                scanner.close();
                return;
        }

        System.out.println("Result:");
        System.out.println(resultMatrix.toString());

        scanner.close();
    }

    private static MatrixMap<Integer> createMatrix(int rows, int columns, Scanner scanner) {
        Function<ring.Indexes, Integer> valueMapper = index -> {
            System.out.print("Enter value at row " + (index.row() + 1) + ", column " + (index.column() + 1) + ": ");
            return scanner.nextInt();
        };

        return MatrixMap.instance(rows, columns, valueMapper);
    }
}
