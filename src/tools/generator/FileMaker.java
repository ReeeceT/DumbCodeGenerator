package src.tools.generator;

import src.tools.calculator.Calculator;
import src.tools.calculator.Calculator.Operations;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.io.IOException;

/*
    TODO:
    Builder design pattern is ideal because this class will generate multiple files with multiple different parameters and configurations.

        FileMaker.java
    Which file is being written to is controlled by this class, not the driver. This is because it can easily be automated.
    For example you can set the file youre editing with .onFile(), then finish and finally wirte to it with .generate()

    Make a single array which will contain everything

    Builder design pattern which will plug in certain things into specific areas, like the boilerplate
    Something will scan through the array to find when non boilerplate code can begin and where it will end


        Files.java
    Creation of files

    Eventually want to swap out the strings representing paths for ACTUAL path types?
    Figure out how to adhere to the DRY principle, as some code is absolutely being unnecessarily written twice here.

    Want to be able to iterate through all created files. This is why things are stored in an array list.
*/

// Manage creation of files and putting text in there
public class FileMaker {
    private final Path defaultRootDir;
    private Path rootDir;
    private Path workingDir;

    private File f;
    private BufferedWriter writer;
    
    

    private ArrayList<File> registeredFiles;

    public FileMaker() {
        defaultRootDir = Path.of("C:/Users/rtill/Program/Personal Proj/Java2026/DumbCodeGenerator/generation");
        workingDir = defaultRootDir;

        f = null;
        writer = null;
        registeredFiles = new ArrayList<File>();
    }

    /**
     * 
     * Creates a file from a given path.
     * 
     * @param relPath - The path to the file which will be created. This is relative to this program's root directory
     * @param suffix - The type of file. (.txt, .java, .jav)
     * 
     * @return A reference to the created file
     */
    public File createFile(Path relPathToFolder, String name) {
        f = Path.of(relPathToFolder.toString(), name).toFile();
        workingDir = Path.of(relPathToFolder.toString());
        registeredFiles.add(f);

        try {
            if (f.exists()) {
                System.out.println("File already exists. Overwriting instead.");
                f.delete();
            }
            f.createNewFile();
            return f;
        } catch (IOException e) { 
            System.out.println("vvv Could not create a file or overwrite an existing one vvv\n\n" + e.getMessage()); 
            return f;
        }
    }

    public File createFile(String relPathToFolder, String name) {
        return null;
    }

    /**
     * 
     * @param f - The file to delete
     */
    public void deleteFile(File f) {
        if (f.exists()) {
            workingDir = Path.of(f.getParent());
            f.delete();
            System.out.println("File \"" + f.toString() + "\" has been successfully deleted");
        } else {
            System.out.println("File \"" + f.toString() + "\" does not exist");
        }
    }

    /**
     * 
     * TODO: Actually add implementation to take in a relative path too.
     * If a relative path is not used, this method checks the current working directory.
     * 
     * @param name - The name or relative path of the file to delete
     */
    public void deleteFile(String name) {
        // If using internal working directory
        deleteFile(Path.of(workingDir.toString(), name).toFile());

        // If using a relative path
    }

    /**
     * 
     * Finds and returns a file based on the current working directory.
     * TODO: Need to make this method also work with relative paths
     * 
     * @param name - The name of the file
     * @return - A reference to the file
     */
    public File getFile(String name) {
        // If not relative path
        Path p = Path.of(workingDir.toString(), name);

        // If relative path
        // set Path p = Path.of(relPath);

        if (p.toFile().exists()) {
            return p.toFile();
        } else {
            System.out.println("File \"" + name + "\" does not exist in current working directory " + workingDir);
            return p.toFile();
        }
    }



    /**
     * 
     * Writes to a specified file
     * 
     * @param f - The file to be written to
     * @param text - The text to be written. Each index of the ArrayList writes to a new line in the file.
     */
    public void writeToFile(File f, ArrayList<String> text) {
        Path p = f.toPath();

        try {
            if (writer != null) {
                System.out.println("Writer already open.");

                writer.close(); // close any old writers
                writer = Files.newBufferedWriter(p, StandardOpenOption.WRITE);
            } else {        
                System.out.println("Writer not created, creating a writer...");
                writer = Files.newBufferedWriter(p, StandardOpenOption.WRITE);
                System.out.println("Successfully created writer!");
            }
        } catch (IOException e) {
            System.out.println("vvv Something went wrong with creating writer! vvv\n" + e.getMessage()); 
            return;
        }

        for (int i = 0; i < text.size(); i++) {
            try {
                writer.write(text.get(i));
            } catch (IOException e) {
                System.out.println("vvv Failed to write to file " + f.toString() + "! vvv" + e.getMessage());
            }
        }

        // Close it so the text actually gets moved from memory into the file
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("vvv Failed to close the writer! vvv" + e.getMessage());
        }
    }

    /**
     * 
     * TODO: Can rewrite this to just delegate it to the above method. Reduces code significantly by not duplicating methods.
     * Writes to a specified file. 
     * 
     * 
     * @param f - The file to be written to
     * @param text - The text to be written
     */
    public void writeToFile(File f, String text) {
        writeToFile(f, toStringList(text));
    }

    /**
     * 
     * Each space in the text will be interpreted as a new index in the string list.
     * TODO: Currently does not work if a word is larger than 50 characters long.
     * 
     * @param text - The text to convert to an array list.
     * @return - An array list of the given string
     */
    private ArrayList<String> toStringList(String text) {
        final char[] chars = text.toCharArray();
        final int maxCapacity = 50;
        int wordLength = 0;

        StringBuilder word = new StringBuilder(maxCapacity);
        ArrayList<String> stringList = new ArrayList<String>();

        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case ' ':
                    stringList.add(word.toString() + " ");
                    word.delete(0, 50);
                    wordLength = 0;
                    break;
                case '\n':
                    stringList.add(word.toString());
                    word.delete(0, 50);
                    wordLength = 0;

                    stringList.add("\n");
                    word.delete(0, 50);
                    break;
                default:
                    wordLength++;
                    word.append(chars[i]);
                    break;
            }

            if (wordLength >= maxCapacity) {
                System.out.println("ERR: Word length greater than " + maxCapacity + " not allowed");
                break;
            }
        }

        stringList.add(word.toString());
        word.delete(0, 50);

        return stringList;
    }

    /**
     * 
     * @return The path of the folder an operation was last done in. An operation could be creating a file, writing a file, deleting a file, etc.
     */
    public Path getWorkingDir() {
        return workingDir;
    }

    /**
     * 
     * @param relPath - The relative path to the file
     * @return - A reference to the file
     */
    public File getFile(Path relPath) {
        return relPath.toFile();
    }

    /**
     * 
     * @return - The ArrayList in which all newly-created files are registered.
     */
    public ArrayList<File> getRegisteredFiles() {
        return registeredFiles;
    }

    /**
     * 
     * @param p - The path to set as the working directory
     */
    public void setWorkingDir(Path p) {
        workingDir = p;
    }

    /**
     * 
     * @param p - The absolute directory all other paths will be relative to
     */
    public void setRootDir(String p) {

    }

    // Manage creating the arrays of text which the writer will write.
    public class TextBuilder {
        private final int BOILER_PLATE_LINES = 11;
        private ArrayList<String> textBuffer; 

        public TextBuilder() {
            textBuffer = new ArrayList<String>(BOILER_PLATE_LINES);
        }

        // Builder design pattern which constructs the text for a file.
    }   
}
