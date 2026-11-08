package src.tools.generator;

import src.tools.calculator.Calculator;
import src.tools.calculator.Calculator.Operations;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.io.IOException;

/*

Constructor does nothing but create the array


 */

public class CodeGenerator {

    private Path path;
    private File resultsFile;
    private BufferedWriter writer;

    private Calculator calc;
    
    private String[][] generated;
    private final int ROWS_LENGTH;
    private final int COLUMNS_LENGTH;
    
    public CodeGenerator(Calculator calc) {
        this.calc = calc;

        ROWS_LENGTH = (calc.getRowsLength() * calc.getOperationsAmt()) + calc.getOperationsAmt();
        COLUMNS_LENGTH = calc.getColumnsLength();
        generated = new String[ROWS_LENGTH][COLUMNS_LENGTH]; // Holds the full size of the generated file

        path = FileSystems.getDefault().getPath("results", "hardcodes.txt");
        resultsFile = new File(path.toString());
        writer = initWriting(resultsFile);

        initText();


        try {
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * 
     * @param f The file to initialize for writing
     * @return A writer for the initialized file
     */
    private BufferedWriter initWriting(File f) {
        // Create file if it does not exist
        try { 
            f.createNewFile(); 
        } catch (IOException e) { 
            System.out.println("vvv Something went wrong! vvv\n" + e); 
        }
        
        // return a buffered writer for writing to the file
        try {
            return Files.newBufferedWriter(f.toPath(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            System.out.println("vvv Something went wrong! vvv\n" + e); 
            return null;
        }
    }

    private void initText() {
        try {
            writer.write("Length of generated is " + generated.length);
            writer.newLine();
            writer.write("Length of calculations array is " + calc.getRowsLength());
            writer.newLine();
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

        for (int i = 0; i <= generated.length; i++) {
            if (i % (calc.getRowsLength() + 1) == 0 && i != generated.length) {
                try {
                    writer.write("INSERT OPERATOR TITLE HERE");
                    writer.newLine();
                } catch (IOException e) {
                    System.out.println(e);
                }
                i = i - 1;
            } else {
                try {
                    writer.write("Operation #: " + i);
                    writer.newLine();
                    
                } catch (IOException e) {
                    System.out.println(e);
                }
                
            }
        }
    }

    // private void writeResults(Operations op) {

    //     try {
    //         writer.write("======================");
    //         writer.newLine();
    //         writer.write("       " + op.toString() + "       ");
    //         writer.newLine();
    //         writer.write("======================");
    //         writer.newLine();
    //     } catch (IOException e) {
    //         System.out.println("vvv Something went wrong! vvv\n" + e);
    //     }

    //     for (int i = minRange; i <= maxRange; i++) {
    //         for (int j = minRange; j <= maxRange; j++) {
    //             try {
    //                 // i and j both start at 1 for the sake of printing out the operands
    //                 // if we also use them for getting the result by index, we are now get the index offset by 2
    //                 // this is why we do -2, to get the actual true result from the operands, and not the result from 2 lines down.
    //                 writer.write("    " + i + operatorSymbol + j + " = " + formatResults(calculations)[i + j - 2]); 
    //                 writer.newLine();
    //             } catch (IOException e) {
    //                 System.out.println("vvv Something went wrong at write step " + i + "! vvv\n" + e); 
    //             }
    //         }
    //     }
    // }

}
