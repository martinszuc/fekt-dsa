import java.util.ArrayList;
import java.util.List;

class Directory {
    private final String name;
    private final List<Directory> subdirectories;

    // Constructor to initialize the directory name
    public Directory(String name) {
        this.name = name;
        this.subdirectories = new ArrayList<>();
    }

    // Add a subdirectory to the current directory
    public void addSubdirectory(Directory subdirectory) {
        if (subdirectories.size() < 2) { // Limit to 2 subdirectories
            subdirectories.add(subdirectory);
        } else {
            System.out.println("Cannot add more than 2 subdirectories to " + name);
        }
    }

    // Print the directory structure (recursively)
    public void printStructure(String indent) {
        System.out.println(indent + name);
        for (Directory subdirectory : subdirectories) {
            subdirectory.printStructure(indent + "    ");
        }
    }
}