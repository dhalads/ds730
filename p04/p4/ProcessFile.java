import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.File;

public class ProcessFile {

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

    public void process() {
        Scanner myReader = null;
        String line = null;
        try {

            myReader = new Scanner(this.getFile());
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                System.out.println(line);
                this.processLine(line);
            }
            myReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            myReader.close();
        }
        System.out.println(output);
    }//end method

    public void processLine(String line){
        Scanner myLine = null;
        String word = null;
        try {
            line = line.trim();
            myLine = new Scanner(line);
            while(myLine.hasNext()){
                word = myLine.next();
                //System.out.println(word);
                processWord(word);
            }


        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    public void processWord(String word){
        int length = 0;
        String indexes = null;
        try {
            word = word.trim();
            length = word.length();
            if(this.pageCharCount + length > this.getPageSize()){
                this.pageNumber = this.pageNumber + 1;
                this.pageCharCount = 0;
            }//end if
            indexes = output.get(word);
            if(indexes == null){
                output.put(word, Integer.toString(this.pageNumber));
            } else {
                indexes = indexes + ", " + Integer.toString(this.pageNumber);
                output.put(word, indexes);
            }
            this.pageCharCount = this.pageCharCount + length;

        } catch (Exception e) {
            e.printStackTrace();
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
