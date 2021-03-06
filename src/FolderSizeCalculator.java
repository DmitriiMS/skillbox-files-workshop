import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator extends RecursiveTask<Long> {
    private Node node;

    public FolderSizeCalculator(Node node) {
        this.node = node;
    }

    @Override
    protected Long compute() {

        long sum = 0;
        File folder = node.getFolder();
        if(folder.isFile()){
            sum += folder.length();
            node.setSize(sum);
            return sum;
        }

        List<FolderSizeCalculator> subTasks = new LinkedList<>();

        File[] files = folder.listFiles();
        if(files != null){
            for(File file : files) {
                Node child = new Node(file);
                FolderSizeCalculator task = new FolderSizeCalculator(child);
                task.fork(); // запустим асинхронно
                subTasks.add(task);
                node.addChild(child);
            }
        }

        for(FolderSizeCalculator task : subTasks) {
            sum += task.join(); // дождёмся выполнения задачи и прибавим результат
        }

        node.setSize(sum);
        return sum;
    }

}