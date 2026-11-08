package src.tools.calculator;

import java.text.DecimalFormat;

public class Calculator {

    private final int MIN;
    private final int MAX;
    private final int TOTAL_OPERATIONS;
    private final int ROWS_LENGTH;
    private final int COLUMNS_LENGTH;

    private double[][] calculations;
    private int currentPlace;
    
    /**
     * 
     * @param min The minimum operand
     * @param max The maximum operand
     */
    public Calculator(int min, int max) {
        this.MIN = min;
        this.MAX = max;
        this.TOTAL_OPERATIONS =4;
        this.ROWS_LENGTH = MAX*MAX;
        this.COLUMNS_LENGTH = 3;

        // An array with size that can contain a single set of results from one operator and the minimum and maximum operands.
        // 3 to hold the two operands and their result after performing the designated operation.
        calculations = new double[ROWS_LENGTH][COLUMNS_LENGTH]; 
    }

    /**
     * 
     * @return The amount of rows in the 2D array calculations
     */
    public int getRowsLength() {
        return ROWS_LENGTH;
    }

    /**
     * 
     * @return The amount of columns in the 2D array calculations
     */
    public int getColumnsLength() {
        return COLUMNS_LENGTH;
    }

    /**
     * 
     * @return The amount of operations which can be performed.
     */
    public int getOperationsAmt() {
        return TOTAL_OPERATIONS;
    }

    /**
     * 
     * @return The fully generated 2D array of calculations consisting of the sets in the rows, and the operands/results in the columns.
     */
    public double[][] getCalculationsArray() {
        return calculations;
    }

    /**
     * 
     * @param op an enum of the operation to calculate upon
     * @return
     */
    public double[][] calculateOperation(Operations op) {
        currentPlace = 0;

        switch (op) {
            case ADDITION:
                for (double i = MIN; i <= MAX; i++) {
                    for (double j = MIN; j <= MAX; j++) {
                        calculations[currentPlace][0] = i; // First operand
                        calculations[currentPlace][1] = j; // Second operand
                        calculations[currentPlace][2] = i + j; // Result
                        currentPlace++;
                    }
                }

            return calculations;

            case SUBTRACTION:
                for (double i = MIN; i <= MAX; i++) {
                    for (double j = MIN; j <= MAX; j++) {
                        calculations[currentPlace][0] = i;
                        calculations[currentPlace][1] = j;
                        calculations[currentPlace][2] = i - j;
                        currentPlace++;
                    }
                }

            return calculations;

            case MULTIPLICATION:
                for (double i = MIN; i <= MAX; i++) {
                    for (double j = MIN; j <= MAX; j++) {
                        calculations[currentPlace][0] = i;
                        calculations[currentPlace][1] = j;
                        calculations[currentPlace][2] = i * j;
                        currentPlace++;
                    }
                }

            return calculations;

            case DIVISION:
                for (double i = MIN; i <= MAX; i++) {
                    for (double j = MIN; j <= MAX; j++) {
                        calculations[currentPlace][0] = i;
                        calculations[currentPlace][1] = j;
                        calculations[currentPlace][2] = i / j;
                        currentPlace++;
                    }
                }

            return calculations;

            default:
                System.out.println("Incorrect operation name. Check calculateOperation() method.");
            return null;
        }
    }

    public enum Operations {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION,
    }

}
