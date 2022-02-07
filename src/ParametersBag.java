import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParametersBag {
    private long limit;
    private String path;

    public ParametersBag(String args[]) throws IllegalArgumentException, FileNotFoundException, ArrayIndexOutOfBoundsException {
        String errorMessage = "всё сломалось";
        String helpMessage = "\nПример правильного использования флагов\n" +
                "java -jar skillbox-files-workshop.jar -d /path/to/dir/ -l 100M\n" +
                "или\n" +
                "java -jar skillbox-files-workshop.jar -l 1G -d C:\\path\\to\\dir\\\n" +
                "Лимит задаётся с указанием множителя: K, M, G или T\n";

        if (args.length < 2 || args.length > 4) {
            throw new IllegalArgumentException("Неправильное количество параметров.\n" +
                    "Как минимум надо указать путь к файлу или папке." + helpMessage);
        }

        try {
            for(int i = 0; i < args.length; i += 2){
                if(args[i].equals("-d")){
                    errorMessage = "Не достаточно параметров: после флага -d не указан путь к файлу." + helpMessage;
                    File f = new File(args[i + 1]);
                    if(!f.exists()) {
                        throw new FileNotFoundException("Нет файла или папки с указаным именем: \"" +
                                args[i + 1] + "\"" + helpMessage);
                    }
                    path = args[i + 1];
                    continue;
                }
                if(args[i].equals("-l")) {
                    errorMessage = "Не достаточно параметров: после флага -l не указан размер лимита." + helpMessage;
                    Pattern pat = Pattern.compile("\\d+[TGMK]");
                    Matcher mat = pat.matcher(args[i + 1]);
                    if(!mat.matches()) {
                        throw new IllegalArgumentException(errorMessage);
                    }
                    long humanReadableLimit = SizeCalculator.getSizeFromHumanReadable(args[i + 1]);
                    if (humanReadableLimit < 0) {
                        throw new IllegalArgumentException("Лимит должен быть больше 0." + helpMessage);
                    }
                    limit = humanReadableLimit;
                    continue;
                }
                throw new IllegalArgumentException("Неправильное использование флагов.\n" +
                        "-d -- для указания пути к папке/файлу\n" +
                        "-l -- для указания лимита." + helpMessage);
        }
        }catch (ArrayIndexOutOfBoundsException aioofbe) {
            throw new ArrayIndexOutOfBoundsException(errorMessage);
        }

    }

    public long getLimit() {
        return limit;
    }

    public String getPath() {
        return path;
    }
}
