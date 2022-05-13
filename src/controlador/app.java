package controlador;

import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class app {

    String directory = "";
    String finalDirectory = "";
    String extension = "";
    Scanner scanner;
    long fileCount = 0, startTime, endTime;
    int mode = -1;

    public app() {
        scanner = new Scanner(System.in);
    }

    public void run() {

        getSearcherMode();

        getUserInputs();

        startTime = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        fileCount = pool.invoke(new searcher(directory, extension, finalDirectory, mode));

        endTime = System.currentTimeMillis();
        System.out.println("Number of files: " + fileCount);
        System.out.println("Time spend: " + (endTime - startTime) + "ms");

    }

    private void getUserInputs() {

        scanner.nextLine();
        System.out.println("Enter the extension: ");
        extension = scanner.nextLine();
        System.out.println("Enter the source directory: ");
        directory = scanner.nextLine();

        if (mode != 3) {
            System.out.println("Enter the target directory: ");
            finalDirectory = scanner.nextLine();
        }

    }

    private void getSearcherMode() {

        do {
            System.out.println("Select the mode: ");
            System.out.println("1. Copy files");
            System.out.println("2. Move files");
            System.out.println("3. Delete files by extension");
            mode = scanner.nextInt();

            if (mode < 1 || mode > 3) {
                System.out.println("[ERROR] Invalid mode selected. Try again.");
            }
              
        } while (mode < 1 || mode > 3);

    }

    public static void main(String[] args) {

        System.out.println("[INFO] Starting...");

        app main = new app();
        main.run();

        System.out.println("[INFO] Finished!");
        System.out.println("[CREATOR] Heiko Morales -> Github: https://github.com/HeikoMorales");
    }
}
