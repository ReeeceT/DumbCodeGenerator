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
    
    public CalcGen(int min, int max) {
        calculations = new double[max*max];
        strCalculations = new String[max*max];
        df = new DecimalFormat("#0.######");
        path = FileSystems.getDefault().getPath("results", "hardcodes.txt");
        resultsFile = new File(path.toString());
        
        intialize(resultsFile);

        calculateAddition(min, max);
        System.out.println(Arrays.toString(calculations));
        try {
            bW.write("======================");
            bW.newLine();
            bW.write("       ADDITION       ");
            bW.newLine();
            bW.write("======================");
            bW.newLine();
        } catch (IOException e) {
            System.out.println("vvv Something went wrong! vvv\n" + e);
        }

        for (int i = min; i <= max; i++) {
            for (int j = min; j <= max; j++) {
                try {
                    // i and j both start at 1 for the sake of printing out the operands
                    // if we also use them for getting the result by index, we are now get the index offset by 2
                    // this is why we do -2, to get the actual true result from the operands, and not the result from 2 lines down.
                    bW.write("    " + i + " + " + j + " = " + formatResults(calculations)[i + j - 2]); 
                    bW.newLine();
                } catch (IOException e) {
                    System.out.println("vvv Something went wrong at write step " + i + "! vvv\n" + e); 
                }
            }
        }   
        
        calculateSubtraction(min, max);
        try {
            bW.write("======================");
            bW.newLine();
            bW.write("     SUBTRACTION      ");
            bW.newLine();
            bW.write("======================");
            bW.newLine();
        } catch (IOException e) {
            System.out.println("vvv Something went wrong! vvv\n" + e);
        }

        for (int i = min; i < max; i++) {
            for (int j = min; j < max; j++) {
                try {
                    // i and j both start at 1 for the sake of printing out the operands
                    // if we also use them for getting the result by index, we are now get the index offset by 2
                    // this is why we do -2, to get the actual true result from the operands, and not the result from 2 lines down.
                    bW.write("    " + i + " - " + j + " = " + formatResults(calculations)[i + j - 2]); 
                    bW.newLine();
                } catch (IOException e) {
                    System.out.println("vvv Something went wrong at write step " + i + "! vvv\n" + e); 
                }
            }
        }   

        calculateMultiplication(min, max);
        try {
            bW.write("======================");
            bW.newLine();
            bW.write("    MULTIPLICATION    ");
            bW.newLine();
            bW.write("======================");
            bW.newLine();
        } catch (IOException e) {
            System.out.println("vvv Something went wrong! vvv\n" + e);
        }

        for (int i = min; i < max; i++) {
            for (int j = min; j < max; j++) {
                try {
                    // i and j both start at 1 for the sake of printing out the operands
                    // if we also use them for getting the result by index, we are now get the index offset by 2
                    // this is why we do -2, to get the actual true result from the operands, and not the result from 2 lines down.
                    bW.write("    " + i + " * " + j + " = " + formatResults(calculations)[i + j - 2]); 
                    bW.newLine();
                } catch (IOException e) {
                    System.out.println("vvv Something went wrong at write step " + i + "! vvv\n" + e); 
                }
            }
        }   

        calculateDivision(min, max);
        try {
            bW.write("======================");
            bW.newLine();
            bW.write("       DIVISION       ");
            bW.newLine();
            bW.write("======================");
            bW.newLine();
        } catch (IOException e) {
            System.out.println("vvv Something went wrong! vvv\n" + e);
        }

        for (int i = min; i < max; i++) {
            for (int j = min; j < max; j++) {
                try {
                    // i and j both start at 1 for the sake of printing out the operands
                    // if we also use them for getting the result by index, we are now get the index offset by 2
                    // this is why we do -2, to get the actual true result from the operands, and not the result from 2 lines down.
                    bW.write("    " + i + " / " + j + " = " + formatResults(calculations)[i + j - 2]); 
                    bW.newLine();
                } catch (IOException e) {
                    System.out.println("vvv Something went wrong at write step " + i + "! vvv\n" + e); 
                }
            }
        }

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

    private void writeResults(int Operation) {
        try {
            bW.write("======================");
            bW.newLine();
            bW.write("       DIVISION       ");
            bW.newLine();
            bW.write("======================");
            bW.newLine();
        } catch (IOException e) {
            System.out.println("vvv Something went wrong! vvv\n" + e);
        }

        for (int i = min; i < max; i++) {
            for (int j = min; j < max; j++) {
                try {
                    // i and j both start at 1 for the sake of printing out the operands
                    // if we also use them for getting the result by index, we are now get the index offset by 2
                    // this is why we do -2, to get the actual true result from the operands, and not the result from 2 lines down.
                    bW.write("    " + i + " / " + j + " = " + formatResults(calculations)[i + j - 2]); 
                    bW.newLine();
                } catch (IOException e) {
                    System.out.println("vvv Something went wrong at write step " + i + "! vvv\n" + e); 
                }
            }
        }
    }

    private String[] formatResults(double[] toFormat) {
        for (int i = 0; i < strCalculations.length; i++) {
            strCalculations[i] = df.format(calculations[i]);
        }

        return strCalculations;
    }

    private double[] calculateAddition(int min, int max) {
        currentPlace = 0;

        for (double i = min; i <= max; i++) {
            for (double j = min; j <= max; j++) {
                calculations[currentPlace] = i + j;
                currentPlace++;
            }
        }

        return calculations;
    }

    private double[] calculateSubtraction(int min, int max) {
        currentPlace = 0;

        for (double i = min; i <= max; i++) {
            for (double j = min; j <= max; j++) {
                calculations[currentPlace] = i - j;
                currentPlace++;
            }
        }

        return calculations;
    }

    private double[] calculateMultiplication(int min, int max) {
        currentPlace = 0;

        for (double i = min; i <= max; i++) {
            for (double j = min; j <= max; j++) {
                calculations[currentPlace] = i * j;
                currentPlace++;
            }
        }

        return calculations;
    }

    private double[] calculateDivision(int min, int max) {
        currentPlace = 0;

        for (double i = min; i <= max; i++) {
            for (double j = min; j <= max; j++) {
                calculations[currentPlace] = i / j;
                currentPlace++;
            }
        }

        return calculations;
    }
}
