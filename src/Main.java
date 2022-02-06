import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {


    public static void main(String[] args) {
        String folderPath = "/tmp/";
        File folder = new File(folderPath);
        Node root = new Node(folder);

        long start = System.nanoTime();

        FolderSizeCalculator calculator = new FolderSizeCalculator(root);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(calculator);
        System.out.println(root);

        System.out.println(System.nanoTime() - start + " ns");
        System.out.println("\n--------------------\n");
        start = System.nanoTime();
        System.out.println(SizeCalculator.getHumanReadableSize(SizeCalculator.getFolderSize(folder)));
        System.out.println(System.nanoTime() - start + " ns");
    }
}
