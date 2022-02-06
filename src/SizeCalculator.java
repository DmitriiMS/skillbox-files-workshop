import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SizeCalculator {
    private static final String[] LETTERS = new String[]{"T", "G", "M", "K"};
    private static final Map<String, Long> MULTIPLIERS = new HashMap<>();
    static {
        MULTIPLIERS.put("T", 1099511627776L);
        MULTIPLIERS.put("G", 1073741824L);
        MULTIPLIERS.put("M", 1048576L);
        MULTIPLIERS.put("K", 1024L);
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
        for(String letter : LETTERS){
            if (size >= MULTIPLIERS.get(letter)) {
                return (int) (Math.round((double) size / MULTIPLIERS.get(letter))) + letter;
            }
        }
        return size + "B";
    }

    public static long getSizeFromHumanReadable (String size) {
        for(String letter : LETTERS) {
            if(size.contains(letter)){
                return Long.parseLong(size.replaceAll("[^0-9]", "")) * MULTIPLIERS.get(letter);
            }
        }
        return Long.parseLong(size.replaceAll("[^0-9]", ""));
    }
}
