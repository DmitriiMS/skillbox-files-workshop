import java.io.File;
import java.util.ArrayList;

public class Node {
    private File folder;
    private ArrayList<Node> children;
    private long size;
    private long limit;
    private int level;


    public Node(File folder) {
        this.folder = folder;
        children = new ArrayList<>();
    }

    public Node(File folder, long limit) {
        this(folder);
        this.limit = limit;
    }

    public File getFolder() {
        return folder;
    }

    public void addChild(Node node) {
        node.setLevel(level + 1);
        node.setLimit(limit);
        children.add(node);
    }

    public ArrayList<Node> getChildren(){
        return children;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        String size = SizeCalculator.getHumanReadableSize(getSize());
        StringBuilder builder = new StringBuilder();
        builder.append(folder.getName()).append(" - ").append(size).append("\n");
        for(Node child : children) {
            if (child.getSize() < limit){
                continue;
            }
            builder.append("  ".repeat(level + 1)).append(child.toString());
        }
        return builder.toString();
    }
}
