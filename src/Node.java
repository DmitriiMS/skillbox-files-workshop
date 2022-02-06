import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Node {
    private File folder;
    private ArrayList<Node> children;
    private long size;
    private int level;


    public Node(File folder) {
        this.folder = folder;
        children = new ArrayList<>();
    }

    public File getFolder() {
        return folder;
    }

    public void addChild(Node node) {
        node.setLevel(level + 1);
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        String size = SizeCalculator.getHumanReadableSize(getSize());
        StringBuilder builder = new StringBuilder();
        builder.append(folder.getName()).append(" - ").append(size).append("\n");
        for(Node child : children) {
            builder.append("  ".repeat(level)).append(child.toString());
        }
        return builder.toString();
    }
}
