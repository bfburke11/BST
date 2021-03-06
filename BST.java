public class BST<Key extends Comparable<Key>, Value> 
{
    private Node root;
    private Node check;
    private int N;

    // CONSTRUCTOR 
    public BST() 
    {
	    this.root = null;
	    this.N = 0;
    }

    // PUBLIC METHODS 

    //
    // insert a new (key, val) into tree
    // or replace value of existing key
    //
    public void insert(Key key, Value val) 
    {
	    //TO BE IMPLEMENTED
        root = insert(root, key, val);
        updateHeight(root);
    }

    private Node insert(Node node, Key key, Value val) {
        if (node == null) {
            node = new Node(key, val);
            N++;
            return node;
        }
        int test = key.compareTo(node.key);
        if (test == 0) {
            node.val = val;
        }
        else if(test < 0) {
            node.left = insert(node.left, key, val);
        }
        else {
            node.right = insert(node.right, key, val);
        }
        return node;
    }
    
    //
    // get the value associated with the given key;
    // return null if key doesn't exist
    //
    public Value get(Key key) 
    {
	    //TO BE IMPLEMENTED
        if (root != null) {
            if (root.key.equals(key)) {
                check = root;
                return check.val;
            }

           Node temp = root;

            while (1 == 1) {
                if (temp.key.compareTo(key) > 0) {
                    if (temp.left == null) {
                        return null;
                    }
                    else {
                        temp = temp.left;
                    }
                }
                else if (temp.key.compareTo(key) < 0) {
                    if (temp.right == null) {
                        return null;
                    }
                    else {
                        temp = temp.right;
                    }
                }
                else {
                    check = temp;
                    return check.val;
                }
            }
        }
        return null;
    }

    //
    // return true if the tree
    // is empty and false 
    // otherwise
    //
    public boolean isEmpty() 
    {
	    return root == null;
    }

    //
    // return the number of Nodes
    // in the tree
    //
    public int size() 
    {
	    return N;
    }

    //
    // returns the height of the tree
    //
    public int height() 
    {
	    return height(root);
    }

    //
    // returns the height of node 
    // with given key in the tree;
    // return -1 if the key does
    // not exist in the tree
    //
    public int height(Key key) 
    {
	    //TO BE IMPLEMENTED
        if(get(key) == null) {
            return -1;
        }
        return maxHeight(check) - 1;

    }

    private void updateHeight(Node node) {
        if (node == null) {
            return;
        }
        node.height = height(node.key);
        updateHeight(node.left);
        updateHeight(node.right);
    }

    private int maxHeight(Node node) {
        if (node == null) {
            return 0;
        }
        int left = maxHeight(node.left);
        int right = maxHeight(node.right);
        return Math.max(left, right) + 1;
    }

    //
    // return a String version of the tree
    // level by level
    //
    public String toString() 
    {
        String str = "";
        Pair x = null;
        Queue<Pair> queue = new Queue<Pair>(N);
        int level = 0;
        queue.enqueue(new Pair(root, level));
        str += "Level 0: ";

        while(!queue.isEmpty()) 
        {
            x = queue.dequeue();
            Node n = x.node;

            if(x.depth > level) 
            {
                level++;
                str += "\nLevel " + level + ": ";
            }

            if(n != null) 
            {
                str += "|" + n.toString() + "|";
                queue.enqueue(new Pair(n.left, x.depth + 1));
                queue.enqueue(new Pair(n.right, x.depth + 1));
            } 
            else  
                str += "|null|";
        }

        str += "\n";
        return str;
    }
		

    // PRIVATE METHODS 

    //
    // return the height of x
    // or -1 if x is null
    //
    private int height(Node x) 
    {
        if(x == null)
            return -1;

        return x.height;
    }

    // NODE CLASS 
    public class Node 
    {
        Key key;
        Value val;
        Node left, right;
        int height;

        public Node(Key key, Value val) 
        {
            this.key = key;
            this.val = val;
        }
        
        public String toString() 
        {
            return "(" + key + ", " + val + "): " + height;
        }
    }

    // PAIR CLASS 
    public class Pair 
    {
        Node node;
        int depth;
        
        public Pair(Node node, int depth) 
        {
            this.node = node;
            this.depth = depth;
        }
    }

}
