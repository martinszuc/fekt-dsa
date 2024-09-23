import java.util.ArrayList;
import java.util.List;

class Directory {
    private final String name;
    private final List<Directory> subdirectories;

    public Directory(String name) {
        this.name = name;
        this.subdirectories = new ArrayList<>();
    }

    public void addSubdirectory(Directory subdirectory) {
        if (subdirectories.size() < 2) {
            subdirectories.add(subdirectory);
        } else {
            System.out.println("Cannot add more than 2 subdirectories to " + name);
        }
    }

    public void printStructure(String indent) {
        System.out.println(indent + name);
        for (Directory subdirectory : subdirectories) {
            subdirectory.printStructure(indent + "    ");
        }
    }
}