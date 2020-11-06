import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.io.File;
import java.io.FileWriter;

public class ProcessFile extends Thread{

    File file = null;
    String outputFolder = null;
    int pageSize = 0;

    int pageNumber = 1;
    int pageCharCount = 0;
    TreeMap<String, String> output = new TreeMap<>();

    public ProcessFile() {
    }

    public ProcessFile(File file, String outputFolder, int pageSize) {
        this.file = file;
        this.outputFolder = outputFolder;
        this.pageSize = pageSize;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File myFile) {
        this.file = myFile;
    }

    public String getOutputFolder() {
        return this.outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ProcessFile file(File file) {
        this.file = file;
        return this;
    }

    public ProcessFile outputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
        return this;
    }

    public ProcessFile pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public void run() {
        Scanner myReader = null;
        String line = null;
        try {

            myReader = new Scanner(this.getFile());
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                // System.out.println(line);
                this.processLine(line);
            }
            this.processOutput();
            myReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myReader.close();
        }
        // System.out.println(output);
    }// end method

    public void processLine(String line) {
        Scanner myLine = null;
        String word = null;
        try {
            line = line.trim();
            myLine = new Scanner(line);
            while (myLine.hasNext()) {
                word = myLine.next();
                // System.out.println(word);
                processWord(word);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void processWord(String word) {
        int length = 0;
        String indexes = null;
        String[] splitIndexes = null;
        String pageNumberString = null;
        try {
            word = word.trim();
            word = word.toLowerCase();
            length = word.length();
            if (this.pageCharCount + length > this.getPageSize()) {
                this.pageNumber = this.pageNumber + 1;
                this.pageCharCount = 0;
            } // end if
            indexes = output.get(word);
            pageNumberString = Integer.toString(this.pageNumber);
            if (indexes == null) {
                output.put(word, pageNumberString);
            } else {
                splitIndexes = indexes.split(",");
                if (!splitIndexes[splitIndexes.length - 1].equals(pageNumberString)) {
                    indexes = indexes + ", " + pageNumberString;
                    output.put(word, indexes);
                }
            }
            this.pageCharCount = this.pageCharCount + length;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processOutput() {
        FileWriter myWriter = null;
        String filename = null;
        String writeLine = null;
        int periodIndex = -1;
        try {
            filename = this.getFile().getName();
            periodIndex = filename.lastIndexOf(".");
            if (periodIndex > 0) {
                filename = filename.substring(0, periodIndex) + "_output" + filename.substring(periodIndex);
            } else {
                filename = filename + "_output.txt";
            }
            filename = this.outputFolder + "/" + filename;
            myWriter = new FileWriter(filename);
            for (Entry<String, String> entry : output.entrySet()) {
                writeLine = entry.getKey() + " " + entry.getValue() + "\n";
                myWriter.write(writeLine);
            }
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                myWriter.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProcessFile)) {
            return false;
        }
        ProcessFile processFile = (ProcessFile) o;
        return Objects.equals(file, processFile.file) && Objects.equals(outputFolder, processFile.outputFolder)
                && pageSize == processFile.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, outputFolder, pageSize);
    }

    @Override
    public String toString() {
        return "{" + " file='" + getFile() + "'" + ", outputFolder='" + getOutputFolder() + "'" + ", pageSize='"
                + getPageSize() + "'" + "}";
    }

}
