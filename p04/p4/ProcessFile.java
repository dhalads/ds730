import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.io.File;
import java.io.FileWriter;

public class ProcessFile extends Thread {

    File file = null;
    String outputFolder = null;
    int pageSize = 0;
    boolean useSinglePageOutput = false;

    int pageNumber = 1;
    int pageCharCount = 0;
   TreeMap<String, TreeSet<Integer>> output = new TreeMap<String, TreeSet<Integer>>();

    public ProcessFile() {
    }

    public ProcessFile(File file, String outputFolder, int pageSize) {
        this.file = file;
        this.outputFolder = outputFolder;
        this.pageSize = pageSize;
    }

    public boolean isUseSinglePageOutput() {
        return this.useSinglePageOutput;
    }

    public void setUseSinglePageOutput(boolean useSinglePageOutput) {
        this.useSinglePageOutput = useSinglePageOutput;
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
        long start = 0;
        long end = 0;
        long startOutput = 0;
        long endOutput = 0;
        try {
            start = System.currentTimeMillis();
            myReader = new Scanner(this.getFile());
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                // System.out.println(line);
                this.processLine(line);
            }
            if (!this.isUseSinglePageOutput()) {
                startOutput = System.currentTimeMillis();
                this.processOutput();
                endOutput = System.currentTimeMillis();
            }
            //System.out.println(this.getFile().getName() + " output:" + (endOutput - startOutput));
            myReader.close();
            end = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myReader.close();
        }
        //System.out.println(this.getFile().getName() + ":" + (end - start));
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
            e.printStackTrace();
        }
    }

    public void processWord(String word) {
        int length = 0;
        TreeSet<Integer> indexes = null;
        try {
            word = word.trim();
            word = word.toLowerCase();
            length = word.length();
            if (this.pageCharCount + length > this.getPageSize()) {
                this.pageNumber = this.pageNumber + 1;
                this.pageCharCount = 0;
            } // end if
            //for output2
            indexes = output.get(word);
            if (indexes == null) {
                indexes = new TreeSet<Integer>();
                indexes.add(this.pageNumber);
                output.put(word, indexes);
            } else {
                if (! indexes.contains(this.pageNumber)) {
                    indexes.add(this.pageNumber);
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
        StringBuffer value = null;
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
            for (Entry<String, TreeSet<Integer>> entry : output.entrySet()) {
                value = new StringBuffer();
                for(Integer intValue: entry.getValue()){
                    if(value.length()>0){
                        value.append(", ");
                        value.append(intValue.toString());
                    }else{
                        value.append(intValue.toString());
                    }
                }
                
                writeLine = entry.getKey() + " " + value.toString() + "\n";
                myWriter.write(writeLine);
            }
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                myWriter.close();
            } catch (Exception e) {
                //Do Nothing
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
