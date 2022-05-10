public class RLRBT<Key extends Comparable<Key>, Value> 
{
    private Node root;
    private Node check;
    private int N;

    // CONSTRUCTOR 
    public RLRBT() 
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
        N++;
        if(root == null) {
            root = insert(root, key, val);
            root.isRed = false;
            return;
        }
        insert(root, key, val);
        updateHeight(root);

    }

    private Node insert(Node node, Key key, Value val) {
        if (node == null) {
            node = new Node(key, val);
            return node;
        }
        int test = key.compareTo(node.key);
        if (test == 0) {
            node.val = val;
            N--;
        }
        else if(test < 0){
            node.left = insert(node.left, key, val);
        }
        else {
            node.right = insert(node.right, key, val);
        }

        if (node.right != null && node.right.right != null) {
            if (node.right.isRed && node.right.right.isRed) {
                boolean flag = false;
                if (node.key.compareTo(root.key) == 0) {
                    flag = true;
                }
                node = rotateLeft(node);
                if (flag) {
                    root = node;
                }
            }
        }
        if (node.left != null) {
            if ((node.right == null || !node.right.isRed) && node.left.isRed) {
                boolean flag = false;
                if (node.key.compareTo(root.key) == 0) {
                    flag = true;
                }
                node = rotateRight(node);
                if (flag) {
                    root = node;
                }
            }
        }

        if (node.right != null && node.left != null) {
            if (node.right.isRed && node.left.isRed) {
                colorFlip(node);
                root.isRed = false;
            }
        }
        node.height = height(node.key);
        return node;


    }
    
    //
    // get the value associated with the given key;
    // return null if key doesn't exist
    //
    public Value get(Key key) 
    {
	    //COPY FROM BST
        if (root != null) {
            if (root.key.equals(key)) {
                check = root;
                return check.val;
            }

            Node temp = root;

            while (true) {
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
    // return String representation of tree                                      
    // level by level                                                            
    //
    public String toString() 
    {
        String ret = "Level 0: ";
        Pair x = null;
        Queue<Pair> queue = new Queue<Pair>(N);
        int level = 0;
        queue.enqueue(new Pair(root, level));
        
        while(!queue.isEmpty()) 
        {
            x = queue.dequeue();
            Node n = x.node;

            if(x.depth > level) 
            {
                level++;
                ret += "\nLevel " + level + ": ";
            }
            
            if(n != null) 
            {
                ret += "|" + n.toString() + "|";
                queue.enqueue(new Pair(n.left, x.depth + 1));
                queue.enqueue(new Pair(n.right, x.depth + 1));
            }
            else
                ret += "|null|";
        }

        ret += "\n";
        return ret;
    }


    //
    // return the black height of the tree
    //
    public int blackHeight() 
    {
	    //TO BE IMPLEMENTED
        if (root == null) {
            return 0;
        }
        Node temp = root;
        int height = -1;
        while (1 == 1) {
            if (!temp.isRed) {
                height++;
            }
            if (temp.left != null) {
                temp = temp.left;
            }
            else if (temp.right != null) {
                temp = temp.right;
            }
            else {
                break;
            }
        }
        return height;
    }
		
    // PRIVATE METHODS 

    //
    // swap colors of two Nodes
    //
    private void swapColors(Node x, Node y) 
    {
        if(x.isRed == y.isRed)
            return;
        
        boolean temp = x.isRed;
        x.isRed = y.isRed;
        y.isRed = temp;
    }

    //
    // rotate a link to the right
    //
    private Node rotateRight(Node x) 
    {
        Node temp = x.left;
        x.left = temp.right;
        temp.right = x;
        swapColors(x, temp);
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        temp.height = Math.max(height(temp.left), x.height) + 1;
        return temp;
    }

    //
    // rotate a link to the left
    //
    private Node rotateLeft(Node x) 
    {
        Node temp = x.right;
        x.right = temp.left;
        temp.left = x;
        swapColors(x, temp);
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        temp.height = Math.max(height(temp.right), x.height) + 1;
        return temp;
    }

    //
    // color flip
    //
    private Node colorFlip(Node x) 
    {
        if(x.left == null || x.right == null)
            return x;
        
        if(x.left.isRed == x.right.isRed) 
        {
            x.left.flip();
            x.right.flip();
            x.flip();
        }
        
        return x;
    }

    //
    // return the height of Node x
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
        boolean isRed;

        public Node(Key key, Value val) 
        {
            this.key = key;
            this.val = val;
            this.isRed = true;
        }
        
        public String toString() 
        {
            return "(" + key + ", " + val + "): " 
            + height + "; " + (this.isRed?"R":"B");
        }

        public void flip() 
        {
            this.isRed = !this.isRed;
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
