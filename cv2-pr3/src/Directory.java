class Directory {
    private final String name;
    private Directory left;
    private Directory right;

    public Directory(String name) {
        this.name = name;
        this.left = null;
        this.right = null;
    }

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
