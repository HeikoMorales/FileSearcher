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
    int mode;

    public searcher(String directory, String extension, String finalDirectory, int mode) {
        this.directory = directory;
        this.extension = extension;
        this.finalDirectory = finalDirectory;
        this.mode = mode;
    }

    @Override
    protected Long compute() {
        fileCount = 0;

        List<RecursiveTask<Long>> tasks = new java.util.ArrayList<>();

        for (File subFile : new File(directory).listFiles()) {
            if (subFile.isDirectory()) {

                searcher sc = new searcher(subFile.getAbsolutePath(), extension, finalDirectory, mode);
                tasks.add(sc);
                sc.fork();

            } else {

                if (subFile.getName().endsWith(extension)) {
                    fileCount++;
                    System.out.println("[INFO] Name: " + subFile.getName() + " Path: " + subFile.getAbsolutePath());

                    doAction(mode, subFile, finalDirectory);

                }
            }
        }

        for (RecursiveTask<Long> task : tasks) {
            fileCount += task.join();
        }

        return fileCount;
    }

    private void doAction(int mode, File subFile, String finalDirectory) {
        try {
            switch (mode) {
                case 1:
                    copyFile(subFile, new File(finalDirectory));
                    break;
                case 2:
                    moveFile(subFile, new File(finalDirectory));
                    break;
                case 3:
                    deleteFile(subFile);
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            System.out.println("[ERROR] " + e.getMessage() + " File: " + subFile.getName());
        }
    }

    private void moveFile(File subFile, File file) throws IOException {
        Files.move(subFile.toPath(), file.toPath().resolve(subFile.getName()));
    }

    private void copyFile(File subFile, File file) throws IOException {
        Files.copy(subFile.toPath(), file.toPath().resolve(subFile.getName()));
    }

    private void deleteFile(File subFile) throws IOException {
        Files.delete(subFile.toPath());
    }

}
