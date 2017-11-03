public class AVLTreeMap implements Map {

    class Node {
        String key;
        String value;
        int height;
        Node left;
        Node right;

        public Node(String key, String value, int height) {
            this.key = key;
            this.value = value;
            this.height = height;
            this.left = null;
            this.right = null;
        }


        // this function should balance an unbalanced tree
        public int balance() {
            return height(this.left) - height(this.right);
        }
    }

    private int size;
    private Node root;

    public AVLTreeMap() {
        root = null; // want to intialize the root as null because all trees begin empty
        size = 0;
    }

    // number of keys and values in the map
    public int size() {
        return this.size;
    }


    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    public void put(String key, String value) {
        if (root == null) {
            Node new_node = new Node(key, value, 1);
            root = new_node;
        }
        else {
            root = put(root, key, value);
        }

    }

    public Node put(Node node, String key, String value) {
        if (node == null) {
            return new Node(key, value, 1);
        }
        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.left = put(node.left, key, value);
        }
        else if (comparison > 0) {
            node.right = put(node.right, key, value);
        }
        else {
            return null;
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        size ++;
        return balance(node);
    }



    // we need two rotation functions, one that rotates clockwise and the other counterclockwise
    private Node balance(Node node) { // looking at the balance of the subtrees to determine whether a clockwise
        // or counterclockwise rotation is needed
        if (node.balance() < -1) {
            if (node.right.balance() < 0) {
                node.right = clockwise(node.right);
            }
            node = counterclockwise(node);
        } else if (node.balance() > 1){
            if (node.left.balance() < 0) {
                node.left = counterclockwise(node.left);
            }
            node = clockwise(node);
        }
        return node;
    }

    private Node clockwise(Node node) {
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        temp.height = 1 + Math.max(height(temp.left), height(temp.right));
        return temp;
    }

    private Node counterclockwise(Node node) {
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        temp.height= 1 + Math.max(height(temp.left), height(temp.right));
        return temp;
    }


    public Node get(Node node, String key) {
        if (node == null) {
            return null;
        }
        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            return get(node.left, key);
        }
        if (comparison > 0) {
            return get(node.right, key);
        }
        else {
            return node;
        }
    }

    public String get(String key) {
        Node node = get(root, key);
        if (node == null) {
            return null;
        }
        return node.value;
    }


    public void print() {
        this.print(this.root, "", 0);
    }

    private void print(Node node, String prefix, int depth) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        if (!prefix.equals("")) {
            System.out.print(prefix);
            System.out.print(":");
        }
        System.out.print(node.key);
        System.out.print(" (");
        System.out.print("H:");
        System.out.print(node.height);
        System.out.print(", B:");
        System.out.print(node.balance());
        System.out.println(")");
        this.print(node.left, "L", depth + 1);
        this.print(node.right, "R", depth + 1);
    }

    public static void main(String[] args) {
        Map avlTreeMap = new AVLTreeMap();
        String[] keys = {"7", "9", "6", "0", "4", "2", "1"};
        String[] values = {"seven", "nine", "six", "zero", "four", "two", "one"};
        for (int i = 0; i < keys.length; i++) {
            avlTreeMap.put(keys[i], values[i]);
            avlTreeMap.print();
            System.out.println();
        }
        for (int i = 0; i < keys.length; i++) {
            //System.out.println("key:");
            System.out.print(keys[i]);
            System.out.print(" ");
            //System.out.println("value:");
            System.out.println(avlTreeMap.get(keys[i]));
        }
    }
}

