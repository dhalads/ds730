import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Index {
    public static void main(String args[]) throws InterruptedException {
        String inputFolder = null;
        String outputFolder = null;
        int pageSize = 0;
        try {
            inputFolder = args[0].trim();
            outputFolder = args[1].trim();
            pageSize = Integer.parseInt(args[2].trim());
            listFilesForFolder2(new File(inputFolder));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            // TODO: handle exception
        } // end try-catch

    }// end main

    public static void listFilesForFolder2(final File folder) {
        String temp = null;
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                // System.out.println("Reading files under the folder
                // "+folder.getAbsolutePath());
                listFilesForFolder2(fileEntry);
            } else {
                if (fileEntry.isFile()) {
                    temp = fileEntry.getName();
                    if ((temp.substring(temp.lastIndexOf('.') + 1, temp.length()).toLowerCase()).equals("txt"))
                        System.out.println("File= " + folder.getAbsolutePath() + "\\" + fileEntry.getName());
                }

            }
        }
    }// end method

    public static ArrayList<File> listFilesForFolder(final File folder, final boolean recursivity,
            final String patternFileFilter) {

        // Inputs
        boolean filteredFile = false;

        // Ouput
        final ArrayList<File> output = new ArrayList<File>();

        // Foreach elements
        for (final File fileEntry : folder.listFiles()) {

            // If this element is a directory, do it recursivly
            if (fileEntry.isDirectory()) {
                if (recursivity) {
                    output.addAll(listFilesForFolder(fileEntry, recursivity, patternFileFilter));
                }
            } else {
                // If there is no pattern, the file is correct
                if (patternFileFilter.length() == 0) {
                    filteredFile = true;
                }
                // Otherwise we need to filter by pattern
                else {
                    filteredFile = Pattern.matches(patternFileFilter, fileEntry.getName());
                }

                // If the file has a name which match with the pattern, then add it to the list
                if (filteredFile) {
                    output.add(fileEntry);
                }
            }
        }

        return output;
    }

}// end class
