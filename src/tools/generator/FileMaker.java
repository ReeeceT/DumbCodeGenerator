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
import java.util.HashMap;

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
    Creation of files?

    Want to be able to iterate through all created files. This is why things are stored in an array list.
*/

// Manage creation of files and putting text in there
public class FileMaker {
    private Path workingDir; // Always an absolute path

    private File f;
    private BufferedWriter writer;
    
    private HashMap<String, File> registeredFiles;

    public FileMaker() {
        workingDir = Path.of("C:/Users/rtill/Program/Personal Proj/Java2026/DumbCodeGenerator");

        registeredFiles = new HashMap<String, File>();
        f = null;
        writer = null;
    }

    /**
     * 
     * Creates a file from a given path. Where the file is created depends on the set working directory.
     * 
     * @param p - The path of the file to be created.
     * 
     * @return A reference to the created file
     */
    public File createFile(Path p) {
        if (p.isAbsolute()) {
            f = p.toFile();
        } else {
            f = Path.of(workingDir.toString(), p.toString()).toFile();
        }

        registeredFiles.put(f.getName(), f);

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

    /**
     * 
     * Creates a file from a given path. Where the file is created depends on the set working directory.
     * 
     * @param name - The name of the file, including suffix.
     * 
     * @return A reference to the created file
     */
    public File createFile(String name) {
        return createFile(Path.of(name));
    }

    /**
     * 
     * @param f - The file to delete
     */
    public void deleteFile(File f) {
        if (f.exists()) {
            workingDir = Path.of(f.getParent()).toAbsolutePath();
            registeredFiles.remove(f.getName());
            f.delete();
            System.out.println("File \"" + f.toString() + "\" has been successfully deleted");
        } else {
            System.out.println("File \"" + f.toString() + "\" does not exist");
        }
    }

    /**
     * 
     * If a relative path is not used, this method checks the current working directory.
     * 
     * @param name - The name or relative path of the file to delete
     */
    public void deleteFile(String name) {
        Path p = Path.of(name);

        if (p.isAbsolute()) {
            deleteFile(Path.of(name).toFile());
        } else {
            deleteFile(Path.of(workingDir.toString(), name).toFile());
        }
    }

    /**
     * 
     * Writes to a specified file
     * 
     * @param f - The file to be written to
     * @param text - The text to be written. Each index of the ArrayList writes to a new line in the file.
     */
    public void writeToFile(File f, TextContent text) {
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
     * Writes to a specified file
     * 
     * @param path - The path of the file to be written to
     * @param text - The text to be written. 
     */
    public void writeToFile(Path path, TextContent text) {
        if (path.isAbsolute()) {
            writeToFile(path.toFile(), text);
        } else {
            writeToFile(Path.of(workingDir.toString(), path.toString()), text);
        }
    }

    /**
     * 
     * Writes to a specified file
     * 
     * @param path - The path of the file to be written to
     * @param text - The text to be written. 
     */
    public void writeToFile(String path, TextContent text) {
        if (Path.of(path).isAbsolute()) {
            writeToFile(Path.of(path).toFile(), text);
        } else {
            writeToFile(Path.of(workingDir.toString(), path).toFile(), text);
        }
    }


    /**
     * 
     * @param p - The absolute path to set as the working directory
     */
    public void cd(Path p) {
        if (p.isAbsolute()) {
            workingDir = p;
        } else {
            workingDir = Path.of(workingDir.toString(), p.toString()); // Can I just directly add them together into one path instead of turning them into strings first.
        }
    }

    /**
     * 
     * If ".." is passed, the working directory will be set back by one.
     * TODO: Add support for inputs like ../../../dir1/dir2
     * 
     * @param p - The absolute path to set as the working directory
     */
    public void cd(String p) {
        if (p.equals("..")) {
            workingDir = workingDir.getParent();
            return;
        }

        cd(Path.of(p));
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
    public File getFile(Path p) {
        if (p.isAbsolute()) {
            File f = Path.of(p.toString()).toFile();
        } else {
            File f = Path.of(workingDir.toString(), p.toString()).toFile(); 
        }
        
        if (f.exists()) {
            return f;
        } else {
            System.out.println("File \"" + f.getName() + "\" does not exist in current working directory " + workingDir);
            return f;
        }
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
        return getFile(Path.of(name));
    }

    /**
     * 
     * TODO: Make return a list of the absolute paths to all files being edited
     * 
     * @return - The ArrayList in which all newly-created files are registered.
     */
    public ArrayList<File> getRegisteredFiles() {
        return registeredFiles;
    }

    

    
}
