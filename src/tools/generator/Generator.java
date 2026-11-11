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
    TODO:
    Builder design pattern is ideal because this class will generate multiple files with multiple different parameters and configurations.

        Generator.java
    Which file is being written to is controlled by this class, not the driver. This is because it can easily be automated.
    For example you can set the file youre editing with .onFile(), then finish and finally wirte to it with .generate()

    Make a single predefined size array which will contain everything

    Builder design pattern which will plug in certain things into specific areas, like the boilerplate
    Something will scan through the array to find when non boilerplate code can begin and where it will end


        Files.java
    Creation of files

*/

public class Generator {
    private File f;
    private BufferedWriter writer;
    private int nonBPBegin;
    private int nonBPEnd;
    
    private String[] fLines;
    private String[] fLinesNonBP;

    private int writtenImports = 0;
    
    public Generator(Calculator calc) {
        this.writer = null;
        this.f = null;
        this.fLines = null;
        this.nonBPBegin = 0;
        this.nonBPEnd = 0;

        clearFilesFromFolder("generation");
    }

    /**
     * Eventually need to make it so the lines get an additional amount to account for boilerplate.
     * Should also make it so that it creates a new array and keeps the old one in case we want to go back and modify an old file.
     * 
     * @param folderRelPath Specifies what the path of the created file will be. This is relative to the root directory.
     * @param lines Specifies how many lines of text this file will contain. This is because we are using predefined arrays rather than lists.
     * This should also only include non-boilerplate code.
     */
    public void createFileFromPath(String folderRelPath, int lines) {
        this.f = new File(folderRelPath);
        this.fLines = new String[lines + 10]; // check this magic number later. Should equate to the total lines of boilerplate.
        this.fLinesNonBP = new String[lines];

        try {
            if (this.f.exists()) f.delete();
            this.f.createNewFile(); 
        } catch (IOException e) { 
            System.out.println("vvv Something went wrong! vvv\n" + e.getMessage()); 
        }
    }

    /**
     * 
     * Upon setting the file to be written to, a new writer is either created 
     * or re-created for that file, depending on whether it already existed.
     * 
     * @param relPathToFile The file to write to
     */
    public void setWritingToFile(String relPathToFile) {
        Path p = Path.of(relPathToFile);
        System.out.println("Current File: " + p.toString());

        try {
            // Not exactly sure if this null check works consistently, as its up to the garbage collector to remove it from memory when its closed.
            if (this.writer != null) {
                System.out.println("Writer already open.");
                // Had an invisible bug for like an hour lol. Couldn't write to more than one file and it took
                // me awhile to realize its because my computer was still looking at the old write buffer and not the new one.
                // Closing it temporarily removes any current write buffer my computer was looking at
                // and then when i create a new one, my computer switches to that one.
                writer.close(); // close old writer to make way for a new one on a new file
                this.writer = Files.newBufferedWriter(p, StandardOpenOption.WRITE);
            } else {        
                System.out.println("Writer not created, creating a writer...");
                this.writer = Files.newBufferedWriter(p, StandardOpenOption.WRITE);
                System.out.println("Successfully created writer!");
            }
        } catch (IOException e) {
            System.out.println("vvv Something went wrong with creating writer! vvv\n" + e.getMessage()); 
        }
    }

    /**
     * 
     * What will be written here is currently hardcoded, so the array will be created by the class rather than the driver.
     * This should be changed later to use the generateBoilerPlate() method once it is defined properly.
     * 
     * @deprecated
     */
    public void writeMain() {
        fLines[0] =  "package results;\n";
        fLines[1] =  "\n";
        fLines[2] =  "import java.util.Scanner;\n";
        fLines[3] =  "\n";
        fLines[4] =  "public class Main {\n";
        fLines[5] =  "\n";
        fLines[6] =  "    private static Scanner s = new Scanner(System.in);\n";
        fLines[7] =  "\n";
        fLines[8] =  "    private static int a;\n";
        fLines[9] =  "    private static int b;\n";
        fLines[10] = "    private static String op;\n";
        fLines[11] = "\n";
        fLines[12] = "    public static void main(String[] args) {\n";
        fLines[13] = "        System.out.print(\"Input operand #1: \");\n";
        fLines[14] = "        a = Integer.getInteger(s.nextLine());\n";
        fLines[15] = "        System.out.print(\"Input operand #2: \");\n";
        fLines[16] = "        b = Integer.getInteger(s.nextLine());\n";
        fLines[17] = "        System.out.print(\"Choose operator (add, sub, mul, div): \");\n";
        fLines[18] = "        op = s.nextLine();\n";
        fLines[19] = "        System.out.println(\"Calculating...\")\n";
        fLines[20] = "\n";
        fLines[21] = "        new HardcodedCalculator(int a, int b, String op);\n";
        fLines[22] = "    }\n";
        fLines[23] = "}\n";

        // Set the actual code
        for (int i = 0; i < fLines.length; i++) {
            fLines[i + nonBPBegin] = "gur";
        }
        
        // Write ALL of it
        for (int i = 0; i < fLines.length; i++) {
            try {
                // Only write if there is actually something defined for that line.
                if (fLines[i] != null) {
                    writer.write(fLines[i]);
                }
            } catch(IOException e) {
                System.out.println("vvv Something went wrong with generating main! vvv " + e.getMessage());
            }
        }

        closeWriter();
    }


    /**
     * 
     * This method will alter certain lines in an array with a predefined size, so ensure that the array
     * is big enough to also fit the configurations you set in the parameters.
     * 
     * Could also destroy old array and create a new one depending on whether the configuration in the parameters requires
     * a larger size.
     * 
     * @param packageName Name of the package the file is in. Note that this should match the relative directory this file sits in.
     * @param numImports How many classes are you importing
     * @param className What is the name of the class. Note that this must have the same name as the file.
     * @param isMain Should this generate the entry point boilerplate?
     */
    public void generateBoilerPlate(String packageName, int numImports, String className, boolean isMain) {
        // Currently handles import amounts and whether its main
        // Other method does not handle imports and shouldnt, but it should handle whether its main too

        fLines[0] = "package " + packageName + ";\n";
        fLines[1] = "\n";

        // IMPORTS
        for (int i = 0; i < numImports; i++) {
            fLines[2+i] = "IMPORT HERE\n";
        }

        if (isMain) {
            fLines[3 + numImports] = "\n";
            fLines[4 + numImports] = "public class " + className + " {\n";
            fLines[5 + numImports] = "    public static void main(String[] args) {\n";
            fLines[6 + numImports] = "\n";
            fLines[7 + numImports] = "    }\n";
            
            nonBPBegin = 9 + numImports;
            for (int i = 8; i < (fLines.length - 4); i++) {
                fLines[i] = "\n";
            }
        } else {
            nonBPBegin = 3 + numImports;
            for (int i = 3; i < (fLines.length - 4); i++) {
                fLines[i] = "\n";
            }
        }

        // NON-BOILERPLATE
        for (int i = (8 + numImports); i < (fLines.length - 4); i++) {
            fLines[i] = "\n";
        }

        nonBPEnd = fLines.length - 4;
        fLines[fLines.length - 4] = "    public " + className + "() {\n";
        fLines[fLines.length - 3] = "\n";
        fLines[fLines.length - 2] = "    }\n";
        fLines[fLines.length - 1] = "}";
    }

    /**
     * 
     * This method will alter certain lines in an array with a predefined size, so ensure that the array
     * is big enough to also fit the configurations you set in the parameters.
     * 
     * Change this and the other one to use the relative path instead of packageName and className. It can do the work
     * of getting those itself
     * 
     * @param packageName Name of the package the file is in. Note that this should match the relative directory this file sits in.
     * @param className What is the name of the class. Note that this must have the same name as the file.
     * @param isMain Should this generate the entry point boilerplate?
     */
    public void generateBoilerPlate(String packageName, String className, boolean isMain) {
        fLines[0] = "package " + packageName + ";\n";
        fLines[1] = "\n";
        fLines[2] = "public class " + className + " {\n";
        
        if (isMain) {
            fLines[4] = "    public static void main(String[] args) {\n";
            fLines[5] = "\n";
            fLines[6] = "    }\n";

            for (int i = 8; i < (fLines.length - 4); i++) {
                fLines[i] = "\n";
            }
        } else {
            for (int i = 3; i < (fLines.length - 4); i++) {
                fLines[i] = "\n";
            }
        }
       
        nonBPBegin = 9;

        // NON-BOILERPLATE
        for (int i = 8; i < (fLines.length - 4); i++) {
            fLines[i] = "\n";
        }

        nonBPEnd = fLines.length - 4;
        fLines[fLines.length - 4] = "    public " + className + "() {\n";
        fLines[fLines.length - 3] = "\n";
        fLines[fLines.length - 2] = "    }\n";
        fLines[fLines.length - 1] = "}";
    }

    public void beginWriting() {
        for (int i = 0; i < fLines.length; i++) {
            try {
                if (fLines[i] != null) {
                    writer.write(fLines[i]);
                } else {
                    System.out.println("Line #" + (i+1) + " is null!");
                    writer.write("~~~~\n");
                }
            } catch(IOException e) {
                System.out.println("vvv Something went wrong with writing! vvv\n" + e.getMessage()); 
            }
        }

        closeWriter();
        System.out.println("Finished writing to the file! Closing writer.");
    }

    // Util
    public String[] getTextBuffer() {
        return fLines;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public int getNonBPBegin() {
        return nonBPBegin;
    }

    public int getNonBPEnd() {
        return nonBPEnd;
    }

    /**
     * Currently does not work
     * @param pathToFolder The folder in which all files will be cleared from.
     */
    public void clearFilesFromFolder(String pathToFolder) {
        Path p = Path.of(pathToFolder);

        
    }

    /**
     * Normally, this method should not have to be called by any other class other than its own.
     * It is public however to account for unforeseen circumstances in which I do need to explicitly close the writer.
     */
    public void closeWriter() {
        try {
            this.writer.close();
        } catch (IOException e) {
            System.out.println("vvv Something went wrong with closing the writer! vvv\n\n" + e.getMessage());
        }
    }

}
