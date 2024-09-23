public class Main {
    public static void main(String[] args) {
        // Create the root directory
        Directory root = new Directory("Directory 1");

        // Create subdirectories
        Directory subDir2 = new Directory("Directory 2");
        Directory subDir3 = new Directory("Directory 3");
        Directory subDir4 = new Directory("Directory 4");
        Directory subDir5 = new Directory("Directory 5");

        // Build the tree structure
        root.setLeft(subDir4);
        root.setRight(subDir2);

        subDir2.setLeft(subDir5);
        subDir2.setRight(subDir3);

        // Print the directory tree structure
        root.printStructure("");
    }
}