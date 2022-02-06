import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {
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
        final long TB = 1099511627776L;
        final long GB = 1073741824L;
        final long MB = 1048576L;
        final long KB = 1024L;
        if(size >= TB) { return (int) (Math.round((double) size / TB)) + "T"; }
        if(size >= GB) { return (int) (Math.round((double) size / GB)) + "G"; }
        if(size >= MB) { return (int) (Math.round((double) size / MB)) + "M"; }
        if(size >= KB) { return (int) (Math.round((double) size / KB)) + "K"; }
        return size + "B";
    }

    public static long getSizeFromHumanReadable (String size) {
        long digits = Long.parseLong(size.substring(0, size.length() - 1));
        switch (size.toCharArray()[size.length() - 1]) {
            case 'K':
                return digits * 1024;
            case 'M':
                return digits * 1024 * 1024;
            case 'G':
                return digits * 1024 * 1024 * 1024;
            case 'T':
                return digits * 1024 * 1024 * 1024 * 1024;
            default:
                return digits;
        }
    }
}
