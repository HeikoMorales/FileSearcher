package controlador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class searcher extends RecursiveTask<Long> {

    String directory;
    String finalDirectory;
    String extension;
    long fileCount;

    public searcher(String directory, String extension, String finalDirectory) {
        this.directory = directory;
        this.extension = extension;
        this.finalDirectory = finalDirectory;

    }

    @Override
    protected Long compute() {
        fileCount = 0;

        List<RecursiveTask<Long>> tasks = new java.util.ArrayList<>();

        for (File subFile : new File(directory).listFiles()) {
            if (subFile.isDirectory()) {

                searcher sc = new searcher(subFile.getAbsolutePath(), extension, finalDirectory);
                tasks.add(sc);
                sc.fork();

            } else {
                if (subFile.getName().endsWith(extension)) {

                    fileCount++;
                    //System.out.println("[INFO] Name: " + subFile.getName() + " Path: " + subFile.getAbsolutePath());
                    copyFile(subFile, new File(finalDirectory));
                    //moveFile(subFile, new File(finalDirectory));

                }
            }
        }

        for (RecursiveTask<Long> task : tasks) {
            fileCount += task.join();
        }

        return fileCount;
    }

    private void moveFile(File subFile, File file) {
        try {
            Files.move(subFile.toPath(), file.toPath().resolve(subFile.getName()));
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("[ERROR] " + e.getMessage() + " File: " + subFile.getName());
        }

    }

    private void copyFile(File subFile, File file) {
        try {
            Files.copy(subFile.toPath(), file.toPath().resolve(subFile.getName()));
        } catch (IOException e) {
            System.out.println("[ERROR] " + e.getMessage() + " File: " + subFile.getName());
        }
    }

}
