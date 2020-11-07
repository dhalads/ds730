import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
import java.io.File;
import java.io.FileWriter;

public class ProcessFolder {
    String inputFolder = null;
    String outputFolder = null;
    int pageSize = 0;
    String searchPattern = "";
    ArrayList<File> files = null;
    boolean useThreads = false;
    boolean useSingleFileOutput = false;

    TreeMap<String, ArrayList<TreeSet<Integer>>> output = null;

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
                this.output = new TreeMap<String, ArrayList<TreeSet<Integer>>>();
                this.addToOutput(workers);
                this.processOutput(workers);
            } // end if
            end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void addToOutput(ArrayList<ProcessFile> workers) {
        int numWorkers = workers.size();
        ProcessFile pf = null;
        String word = null;
        TreeSet<Integer> value = null;
        ArrayList<TreeSet<Integer>> index = null;
        try {
            for (int i = 0; i < numWorkers; ++i) {
                pf = workers.get(i);
                for (Map.Entry<String, TreeSet<Integer>> entry : pf.output2.entrySet()) {
                    word = entry.getKey();
                    value = entry.getValue();
                    index = this.output.get(word);
                    if (index == null) {
                        index = new ArrayList<TreeSet<Integer>>(numWorkers);
                        for (int j = 0; j < numWorkers; ++j) {
                            index.add(null);
                        }
                        index.set(i, value);
                        this.output.put(word, index);
                    } else {
                        index.set(i, value);
                        this.output.put(word, index);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processOutput(ArrayList<ProcessFile> workers) {
        int numWorkers = workers.size();
        StringBuffer header = null;
        ProcessFile pf = null;
        String word = null;
        TreeSet<Integer> value2 = null;
        ArrayList<TreeSet<Integer>> value = null;
        StringBuffer row = null;
        StringBuffer field = null;
        FileWriter myWriter = null;
        String filename = null;
        try {
            row = new StringBuffer();
            field = new StringBuffer();
            filename = this.outputFolder + "/output.txt";
            myWriter = new FileWriter(filename);
            header = new StringBuffer();
            header.append("Word");
            for (int i = 0; i < numWorkers; ++i) {
                pf = workers.get(i);
                header.append(", ");
                header.append(pf.getFile().getName());
            }
            header.append("\n");
            myWriter.write(header.toString());
            for (Map.Entry<String, ArrayList<TreeSet<Integer>>> entry : this.output.entrySet()) {
                word = entry.getKey();
                value = entry.getValue();
                row.delete(0, row.length());
                row.append(word);
                for (int i = 0; i < numWorkers; ++i) {
                    value2 = value.get(i);
                    if (value2 == null) {
                        row.append(",");
                    } else {
                        field.delete(0, field.length());
                        for (Integer wordIndex : value2) {
                            if (field.length() == 0) {
                                field.append(wordIndex);
                            } else {
                                field.append(":");
                                field.append(wordIndex);
                            } // end if
                        } // end for
                        row.append(",");
                        row.append(field.toString());
                    } // end if
                } // end for i
                row.append("\n");
                myWriter.write(row.toString());
            }// for Map.Entry
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

}// end class
