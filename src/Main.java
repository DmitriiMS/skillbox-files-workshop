import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ForkJoinPool;

public class Main {


    public static void main(String[] args) {
        try {
            ParametersBag bag = new ParametersBag(args);
            String folderPath = bag.getPath();
            long sizeLimit = bag.getLimit();
            File folder = new File(folderPath);
            Node root = new Node(folder, sizeLimit);


            long start = System.nanoTime();

            FolderSizeCalculator calculator = new FolderSizeCalculator(root);
            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(calculator);
            System.out.println(root);

            System.out.println(System.nanoTime() - start + " ns");
        }catch (IllegalArgumentException | FileNotFoundException | ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
