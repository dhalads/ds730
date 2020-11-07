import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
import java.io.File;

public class ProcessFolder {
    String inputFolder = null;
    String outputFolder = null;
    int pageSize = 0;
    String searchPattern = "";
    ArrayList<File> files = null;
    boolean useThreads = false;
    boolean useSingleFileOutput = false;

    TreeMap<String, TreeSet<Integer>[]> output = null;

    public boolean isUseThreads() {
        return this.useThreads;
    }

    public void setUseThreads(boolean useThreads) {
        this.useThreads = useThreads;
    }

    public boolean isUseSingleFileOutput() {
        return this.useSingleFileOutput;
    }

    public void setUseSingleFileOutput(boolean useSingleFileOutput) {
        this.useSingleFileOutput = useSingleFileOutput;
    }

    public ProcessFolder searchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
        return this;
    }

    public String getSearchPattern() {
        return this.searchPattern;
    }

    public void setSearchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
    }

    public ProcessFolder() {
    }

    public ProcessFolder(String inputFolder, String outputFolder, int pageSize) {
        this.inputFolder = inputFolder;
        this.outputFolder = outputFolder;
        this.pageSize = pageSize;
    }

    public String getInputFolder() {
        return this.inputFolder;
    }

    public void setInputFolder(String inputFolder) {
        this.inputFolder = inputFolder;
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

    public ProcessFolder inputFolder(String inputFolder) {
        this.inputFolder = inputFolder;
        return this;
    }

    public ProcessFolder outputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
        return this;
    }

    public ProcessFolder pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProcessFolder)) {
            return false;
        }
        ProcessFolder processFolder = (ProcessFolder) o;
        return Objects.equals(inputFolder, processFolder.inputFolder)
                && Objects.equals(outputFolder, processFolder.outputFolder) && pageSize == processFolder.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputFolder, outputFolder, pageSize);
    }

    @Override
    public String toString() {
        return "{" + " inputFolder='" + getInputFolder() + "'" + ", outputFolder='" + getOutputFolder() + "'"
                + ", pageSize='" + getPageSize() + "'" + "}";
    }

    public void process() {
        File myFile = null;
        ProcessFile pf = null;
        long start = 0;
        long end = 0;
        ArrayList<ProcessFile> workers = new ArrayList<>();
        try {
            start = System.currentTimeMillis();
            this.files = Index.listFilesForFolder(new File(this.getInputFolder()), false, this.searchPattern);
            for (int i = 0; i < this.files.size(); ++i) {
                myFile = this.files.get(i);
                pf = new ProcessFile();
                pf.setFile(myFile);
                pf.setOutputFolder(this.getOutputFolder());
                pf.setPageSize(this.getPageSize());
                pf.setUseSinglePageOutput(this.isUseSingleFileOutput());
                workers.add(pf);
                if (useThreads) {
                    pf.start();
                } else {
                    pf.run();
                }
            } // end for
            if (useThreads) {
                for (ProcessFile pft : workers) {
                    if (pft.isAlive()) {
                        pft.join(); // make sure to wait for all threads to finish
                    }
                }
            }
            if (this.isUseSingleFileOutput()) {
                this.output = new TreeMap<>();
                for (ProcessFile pft : workers) {

                }
            } // end if
            end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void addToOutput(ProcessFile pf) {
        try {
            

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}// end class
