class Directory {
    private final String name;
    private Directory left;  // Left subdirectory
    private Directory right; // Right subdirectory

    // Constructor to initialize the directory name
    public Directory(String name) {
        this.name = name;
        this.left = null;
        this.right = null;
    }

    // Get and set methods for the left subdirectory
    public Directory getLeft() {
        return left;
    }

    public void setLeft(Directory left) {
        this.left = left;
    }

    public Directory getRight() {
        return right;
    }

    public void setRight(Directory right) {
        this.right = right;
    }

    public void printStructure(String indent) {
        System.out.println(indent + name);
        if (left != null) {
            left.printStructure(indent + "    ");
        }
        if (right != null) {
            right.printStructure(indent + "    ");
        }
    }
}
