package controlador;

import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class main {

    String directory = "";
    String finalDirectory = "";
    String extension = "";
    Scanner scanner;
    long fileCount = 0, startTime, endTime;

    public main() {
        scanner = new Scanner(System.in);
    }

    public void run() {

        System.out.println("Enter the extension: ");
        extension = scanner.nextLine();
        System.out.println("Enter the directory name: ");
        directory = scanner.nextLine();
        System.out.println("Enter the final directory: ");
        finalDirectory = scanner.nextLine();

        startTime = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        fileCount = pool.invoke(new searcher(directory, extension, finalDirectory));

        endTime = System.currentTimeMillis();
        System.out.println("Number of files: " + fileCount);
        System.out.println("Time spend: " + (endTime - startTime) + "ms");

    }

    public Long getFileCount() {
        return fileCount;
    }

    public static void main(String[] args) {

        System.out.println("[INFO] Starting...");
        
        main main = new main();
        main.run();

        System.out.println("[INFO] Finished!");
        System.out.println("[CREATOR] Heiko Morales -> Github: https://github.com/HeikoMorales");
    }
}
