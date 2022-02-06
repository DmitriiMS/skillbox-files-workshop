import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        String folderPath = "/home/dms/";
        File folder = new File(folderPath);


        long start = System.nanoTime();
        FolderSizeCalculator calculator = new FolderSizeCalculator(folder);
        ForkJoinPool pool = new ForkJoinPool();
        long size = pool.invoke(calculator);
        System.out.println(size);
        System.out.println(System.nanoTime() - start + " ns");
        System.out.println("\n--------------------\n");
        start = System.nanoTime();
        System.out.println(getFolderSize(folder));
        System.out.println(System.nanoTime() - start + " ns");
    }

    public static long getFolderSize(File folder){
        if(folder.isFile()) {
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        if(files != null) {
            for (File file : files) {
                sum += getFolderSize(file);
            }
        }
        return sum;
    }
}
