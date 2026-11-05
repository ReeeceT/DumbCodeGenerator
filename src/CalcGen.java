package src;

import java.text.DecimalFormat;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.util.Arrays;

public class CalcGen {

    private int minRange;
    private int maxRange;
    private double[] calculations;
    private String[] strCalculations;
    private int currentPlace;
    
    private DecimalFormat df;
    private BufferedWriter bW;
    private File resultsFile;
    private Path path;

    private String operatorName = "Addition";
    private String operatorSymbol = "+";
    
    public CalcGen(int min, int max) {
        this.minRange = min;
        this.maxRange = max;
        calculations = new double[max*max];
        strCalculations = new String[max*max];
        df = new DecimalFormat("#0.######");
        path = FileSystems.getDefault().getPath("results", "hardcodes.txt");
        resultsFile = new File(path.toString());
        
        intialize(resultsFile);

        calculateOperation(min, max, "Addition");
        writeResults("Addition");
        
        calculateOperation(min, max, "Subtraction");
        writeResults("Subtraction");

        calculateOperation(min, max, "Multiplication");
        writeResults("Multiplication");
 
        calculateOperation(min, max, "Division");
        writeResults("Division");

        try {
            bW.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Creates a file based on the passed argument, then initalizes the bW member field for this file.
     * 
     */
    private void intialize(File f) {
        // Create file if it does not exist
        try { 
            f.createNewFile(); 
        } catch (IOException e) { 
            System.out.println("vvv Something went wrong! vvv\n" + e); 
        }
        
        // return a buffered writer for writing to the file
        try {
            bW = Files.newBufferedWriter(f.toPath(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            System.out.println("vvv Something went wrong! vvv\n" + e); 
        }
    }

    private double[] calculateOperation(int min, int max, String operation) {
        currentPlace = 0;

        switch (operation) {
            case "Addition":
                for (double i = min; i <= max; i++) {
                    for (double j = min; j <= max; j++) {
                        calculations[currentPlace] = i + j;
                        currentPlace++;
                    }
                }

            return calculations;

            case "Subtraction":
                for (double i = min; i <= max; i++) {
                    for (double j = min; j <= max; j++) {
                        calculations[currentPlace] = i + j;
                        currentPlace++;
                    }
                }

            return calculations;

            case "Multiplication":
                for (double i = min; i <= max; i++) {
                    for (double j = min; j <= max; j++) {
                        calculations[currentPlace] = i + j;
                        currentPlace++;
                    }
                }

            return calculations;

            case "Division":
                for (double i = min; i <= max; i++) {
                    for (double j = min; j <= max; j++) {
                        calculations[currentPlace] = i + j;
                        currentPlace++;
                    }
                }

            return calculations;

            default:
                System.out.println("Incorrect operation name. Check calculateOperation() method.");
            return null;
        }
    }

    private String[] formatResults(double[] toFormat) {
        for (int i = 0; i < strCalculations.length; i++) {
            strCalculations[i] = df.format(calculations[i]);
        }

        return strCalculations;
    }

    private void writeResults(String Operation) {
        operatorName = Operation;
        operatorSymbol = getOperatorSymbol(operatorName);

        try {
            bW.write("======================");
            bW.newLine();
            bW.write("       " + operatorName + "       ");
            bW.newLine();
            bW.write("======================");
            bW.newLine();
        } catch (IOException e) {
            System.out.println("vvv Something went wrong! vvv\n" + e);
        }

        for (int i = minRange; i <= maxRange; i++) {
            for (int j = minRange; j <= maxRange; j++) {
                try {
                    // i and j both start at 1 for the sake of printing out the operands
                    // if we also use them for getting the result by index, we are now get the index offset by 2
                    // this is why we do -2, to get the actual true result from the operands, and not the result from 2 lines down.
                    bW.write("    " + i + operatorSymbol + j + " = " + formatResults(calculations)[i + j - 2]); 
                    bW.newLine();
                } catch (IOException e) {
                    System.out.println("vvv Something went wrong at write step " + i + "! vvv\n" + e); 
                }
            }
        }
    }

    private String getOperatorSymbol(String operatorName) {
        switch (operatorName) {
            case "Addition":
                return "+";
            case "Subtraction":
                return "-";
            case "Multiplication":
                return "*";
            case "Division":
                return "/";
            default:
                return "?";
        }
    }
}
