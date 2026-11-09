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

public class Generator {

    private File f;
    private BufferedWriter writer;
    
    private String[] toWrite;
    private final int LENGTH;
    
    public Generator(Calculator calc) {
        LENGTH = calc.getRowsLength() + calc.getOperationsAmt();
        this.writer = null;
    }

    public void createFileFromPath(String folderRelPath) {
        f = new File(folderRelPath);

        try {
            if (f.exists()) f.delete();
            f.createNewFile(); 
        } catch (IOException e) { 
            System.out.println("vvv Something went wrong! vvv\n" + e.getMessage()); 
        }
    }

    public void writeToFile(String relPathToFile) {
        Path p = Path.of(relPathToFile);
        System.out.println("Path: " + p.toString());

        try {
            // Not exactly sure if this null check works, as its up to the garbage collector to remove it from memory when its closed.
            if (this.writer != null) {
                this.writer.close(); 
                System.out.println("Writer already open");
            }
        
            System.out.println("Writer not created, creating a writer...");
            this.writer = Files.newBufferedWriter(p, StandardOpenOption.WRITE);
        } catch (IOException e) {
            System.out.println("vvv Something went wrong with creating writer! vvv\n" + e.getMessage()); 
        }
    }

    public void closeWriter() {
        try {
            this.writer.close();
        } catch (IOException e) {
            System.out.println("vvv Something went wrong with closing writer! vvv\n\n" + e.getMessage());
        }
    }

    public void generateMain() {
        try {
            writer.write("package results;");
            writer.newLine();
            writer.newLine();
            writer.write("import java.util.Scanner;");
            writer.newLine();
            writer.newLine();
            writer.write("public class Main {");
            writer.newLine();
            writer.newLine();
            writer.write("private static Scanner s = new Scanner(System.in);");
            writer.newLine();
            writer.newLine();
            writer.write("private static int a;");
            writer.write("private static int b;");
            writer.write("private static String op;");
            writer.newLine();
            writer.newLine();
            writer.write("    public static void main(String[] args) {");
            writer.newLine();
            writer.write("        System.out.print(\"Input operand #1: \");");
            writer.newLine();
            writer.write("        a = Integer.getInteger(s.nextLine());"); // Assuming this translates the string '1' into value 1, and doesnt just cast it to an integer
            writer.newLine();
            writer.write("        System.out.print(\"Input operand #2: \");");
            writer.newLine();
            writer.write("        b = Integer.getInteger(s.nextLine());");
            writer.newLine();
            writer.write("        System.out.print(\"Choose operator (add, sub, mul, div): \");");
            writer.newLine();
            writer.write("        op = s.nextLine();");
            writer.newLine();
            writer.write("        System.out.println(\"Calculating...\");");
            writer.newLine();
            writer.newLine();
            writer.write("        new HardcodedCalculator(int a, int b, String op);");
            writer.newLine();
            writer.write("    }");
            writer.newLine();
            writer.write("}");
        } catch(IOException e) {
            System.out.println("vvv Something went wrong with generating main! vvv " + e.getMessage());
        }
        

    }

    public void generateBoilerPlate() {

    }
    /**
     * Defines the length of the internal array used for storing each line of text based on set parameters
     */
    public void setTextResources(int operators, int operands) {

    }

    public String[] initText() {
        toWrite = new String[LENGTH];

        for (int i = 0; i < toWrite.length; i++) {
            toWrite[i] = "ADDITION";
        }

        return toWrite;
    }

    public void write() {
        for (int i = 0; i < toWrite.length; i++) {
            try {
                writer.write(toWrite[i]);
                writer.newLine();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
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
