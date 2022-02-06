import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static final String[] LETTERS = new String[]{"T", "G", "M", "K"};

    public static void main(String[] args) {
        String folderPath = "/tmp/";
        File folder = new File(folderPath);
        long start = System.nanoTime();
        FolderSizeCalculator calculator = new FolderSizeCalculator(folder);
        ForkJoinPool pool = new ForkJoinPool();
        long size = pool.invoke(calculator);
        System.out.println(getHumanReadableSize(size));
        System.out.println(System.nanoTime() - start + " ns");
        System.out.println("\n--------------------\n");
        start = System.nanoTime();
        System.out.println(getHumanReadableSize(getFolderSize(folder)));
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

    public static String getHumanReadableSize(long size) {
        Map<String, Long> multipliers = getMultipliers();
        for(String letter : LETTERS){
            if (size >= multipliers.get(letter)) {
                return (int) (Math.round((double) size / multipliers.get(letter))) + letter;
            }
        }
        return size + "B";
    }

    public static long getSizeFromHumanReadable (String size) {
        Map<String, Long> multipliers = getMultipliers();
        for(String letter : LETTERS) {
            if(size.contains(letter)){
                return Long.parseLong(size.replaceAll("[^0-9]", "")) * multipliers.get(letter);
            }
        }
        return Long.parseLong(size.replaceAll("[^0-9]", ""));
    }

    public static Map<String, Long> getMultipliers(){
        return new HashMap<String, Long>(){{
            put("T", 1099511627776L);
            put("G", 1073741824L);
            put("M", 1048576L);
            put("K", 1024L);
        }};
    }
}
