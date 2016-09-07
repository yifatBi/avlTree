import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by yifatbiezuner on 07/09/2016.
 */
public class Avl {
    protected AvlNode root;
    public Avl( )
    {
        root = null;
    }
/**********************************************insert******************************************/
    /**
     * Insert val to the tree
     */
    public void insert(int k) {
        // create new node
        AvlNode n = new AvlNode(k);
        // start recursive procedure for inserting the node
        insertAVL(this.root,n);
    }

    /**
     * Recursive method to insert a node into a tree.
     *
     * @param p The node currently compared, usually you start with the root.
     * @param q The node to be inserted.
     */
    public void insertAVL(AvlNode p, AvlNode q) {
        // If  node to compare is null, the node is inserted. If the root is null, it is the root of the tree.
        if(p==null) {
            this.root=q;
        } else {

            // If compare node is smaller, continue with the left node
            if(q.key<p.key) {
                if(p.left==null) {
                    p.left = q;
                    q.parent = p;

                    // Node is inserted now, continue checking the balance
                    recursiveBalance(p);
                } else {
                    insertAVL(p.left,q);
                }

            } else if(q.key>p.key) {
                if(p.right==null) {
                    p.right = q;
                    q.parent = p;

                    // Node is inserted now, continue checking the balance
                    recursiveBalance(p);
                } else {
                    insertAVL(p.right,q);
                }
            } else {
                // do nothing: This node already exists
            }
        }
    }

    /**
     * Check the balance for each node recursivly and call required methods for balancing the tree until the root is reached.
     *
     * @param cur : The node to check the balance for, usually you start with the parent of a leaf.
     */
    public void recursiveBalance(AvlNode cur) {

        // we do not use the balance in this class, but the store it anyway
        setBalance(cur);
        int balance = cur.balance;

        // check the balance
        if(balance==-2) {

            if(height(cur.left.left)>=height(cur.left.right)) {
                cur = rotateRight(cur);
            } else {
                cur = doubleRotateLeftRight(cur);
            }
        } else if(balance==2) {
            if(height(cur.right.right)>=height(cur.right.left)) {
                cur = rotateLeft(cur);
            } else {
                cur = doubleRotateRightLeft(cur);
            }
        }

        // we did not reach the root yet
        if(cur.parent!=null) {
            recursiveBalance(cur.parent);
        } else {
            this.root = cur;
        }
    }

    /**
     * Left rotation using the given node.
     *
     *
     * @param n
     *            The node for the rotation.
     *
     * @return The root of the rotated tree.
     */
    public AvlNode rotateLeft(AvlNode n) {

        AvlNode v = n.right;
        v.parent = n.parent;

        n.right = v.left;

        if(n.right!=null) {
            n.right.parent=n;
        }

        v.left = n;
        n.parent = v;

        if(v.parent!=null) {
            if(v.parent.right==n) {
                v.parent.right = v;
            } else if(v.parent.left==n) {
                v.parent.left = v;
            }
        }

        setBalance(n);
        setBalance(v);

        return v;
    }

    /**
     * Right rotation using the given node.
     *
     * @param n
     *            The node for the rotation
     *
     * @return The root of the new rotated tree.
     */
    public AvlNode rotateRight(AvlNode n) {

        AvlNode v = n.left;
        v.parent = n.parent;

        n.left = v.right;

        if(n.left!=null) {
            n.left.parent=n;
        }

        v.right = n;
        n.parent = v;


        if(v.parent!=null) {
            if(v.parent.right==n) {
                v.parent.right = v;
            } else if(v.parent.left==n) {
                v.parent.left = v;
            }
        }

        setBalance(n);
        setBalance(v);

        return v;
    }
    /**
     *
     * @param u The node for the rotation.
     * @return The root after the double rotation.
     */
    public AvlNode doubleRotateLeftRight(AvlNode u) {
        u.left = rotateLeft(u.left);
        return rotateRight(u);
    }

    /**
     *
     * @param u The node for the rotation.
     * @return The root after the double rotation.
     */
    public AvlNode doubleRotateRightLeft(AvlNode u) {
        u.right = rotateRight(u.right);
        return rotateLeft(u);
    }


    /**
     * Calculating the "height" of a node.
     *
     * @param cur
     * @return The height of a node (-1, if node is not existent eg. NULL).
     */
    private int height(AvlNode cur) {
        if(cur==null) {
            return -1;
        }
        if(cur.left==null && cur.right==null) {
            return 0;
        } else if(cur.left==null) {
            return 1+height(cur.right);
        } else if(cur.right==null) {
            return 1+height(cur.left);
        } else {
            return 1+Math.max(height(cur.left),height(cur.right));
        }
    }

    private void setBalance(AvlNode cur) {
        cur.balance = height(cur.right)-height(cur.left);
    }
/**********************************************EndInsert******************************************/
/**********************************************printing******************************************/

    final protected void printInorder(AvlNode n) {
        if (n == null) {
            return;
        }
        printInorder(n.left);
        System.out.print(n.key);
        printInorder(n.right);
    }
    public void printInorder(){
        System.out.println("Inorder");
        printInorder(root);
        System.out.println();
    }
    final protected void printPreorder(AvlNode n) {
        if (n == null) {
            return;
        }
        System.out.print(n.key);
        printPreorder(n.left);
        printPreorder(n.right);
    }
    public void printPreorder(){
        System.out.println("Preorder");
        printPreorder(root);
        System.out.println();
    }
    final protected void printPostorder(AvlNode n) {
        if (n == null) {
            return;
        }
        printPostorder(n.left);
        printPostorder(n.right);
        System.out.print(n.key);
    }
    public void printPostorder(){
        System.out.println("PostOrder");
        printPostorder(root);
        System.out.println();
    }
    //Avl NOde
    public class AvlNode {
        public AvlNode left;
        public AvlNode right;
        public AvlNode parent;
        public int key;
        public int balance;

        public AvlNode(int k) {
            left = right = parent = null;
            balance = 0;
            key = k;
        }
        public String toString() {
            return "" + key;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Avl tree= new Avl();
        System.out.println("welcome to AVL");
        System.out.println("please enter values for the tree");
        String list=br.readLine();
        String[] arrList=list.split(",");
        for (String anArrList : arrList) {
            tree.insert(Integer.parseInt(anArrList));
        }
        System.out.println("Select print method:\n" +
                "(1) Inorder print (2) Preorder print (3) Postorder print (4) Quit");
        int action=Integer.parseInt(br.readLine());
        while (action!=4) {
            switch (action) {
                case 1: {
                    tree.printInorder();
                    break;
                }
                case 2: {
                    tree.printPreorder();
                    break;
                }
                case 3: {
                    tree.printPostorder();
                    break;
                }
                default: {
                    break;
                }
            }
            System.out.println("Select print method:\n" +
                    "(1) Inorder print (2) Preorder print (3) Postorder print (4) Quit");
            action=Integer.parseInt(br.readLine());
        }

    }
}

