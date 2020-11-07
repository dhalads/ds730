import java.nio.file.FileSystems;
import java.nio.file.Path;

public class GlobalRunner {
    public static void main(String args[]) throws InterruptedException {
        String inputFolder = null;
        String outputFolder = null;
        int pageSize = 0;
        ProcessFolder pf = null;
        try {
            Path path = FileSystems.getDefault().getPath(".").toAbsolutePath();
            System.out.println("path=" + path);
            inputFolder = args[0].trim();
            outputFolder = args[1].trim();
            pageSize = Integer.parseInt(args[2].trim());
            pf = new ProcessFolder();
            pf.setInputFolder(inputFolder);
            pf.setOutputFolder(outputFolder);
            pf.setPageSize(pageSize);
            pf.setUseThreads(true);
            pf.setUseSingleFileOutput(true);
            pf.process();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            // TODO: handle exception
        } // end try-catch

    }// end main
}
