import java.io.File;
import java.io.FileNotFoundException;
public class ParametersBag {
    private long limit;
    private String path;

    public ParametersBag(String args[]) throws IllegalArgumentException, FileNotFoundException, ArrayIndexOutOfBoundsException {
        if (args.length < 2 || args.length > 4) {
            throw new IllegalArgumentException("Неправильное количество параметров.\n" +
                    "Как минимум надо указать путь к файлу или папке.");
        }

        int hasPath = contains(args, "-d");
        int hasLimit = contains(args, "-l");
        if(hasPath == -1 && hasLimit == -1) {
            throw new IllegalArgumentException("Неправильное использование флагов.\n" +
                    "-d -- для указания пути к папке/файлу\n" +
                    "-l -- для указания лимита.");
        } else if (hasPath != -1){
            try {
                File f = new File(args[hasPath + 1]);
                if(!f.exists()) {
                    throw new FileNotFoundException("Нет файла или папки с указаным именем.");
                }
                path = args[hasPath + 1];
            } catch (ArrayIndexOutOfBoundsException aioofbe) {
                throw new ArrayIndexOutOfBoundsException("Не достаточно параметров: не указан путь к файлу.");
            }
            if(hasLimit != -1) {
                try {
                    long humanReadableLimit = SizeCalculator.getSizeFromHumanReadable(args[hasLimit + 1]);
                    if (humanReadableLimit < 0) {
                        throw new IllegalArgumentException("Лимит должен быть больше 0.");
                    }
                    limit = humanReadableLimit;
                } catch (ArrayIndexOutOfBoundsException aioofbe) {
                    throw new ArrayIndexOutOfBoundsException("Не достаточно параметров: не указан лимит.");
                }
            }
        }

    }

    private int contains(String args[], String arg){
        for(int i = 0; i < args.length; i++){
            if (args[i].equals(arg)) { return i; }
        }
        return -1;
    }

    public long getLimit() {
        return limit;
    }

    public String getPath() {
        return path;
    }
}
