package kdtree;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private int size;
    private class Node{
        Point2D p;
        Node left;
        Node right;
        Node(Point2D p1){
            p=p1;
            left = null;
            right = null;
        }
    }
    private Boolean xpart = true;
    private Node root;
    public    KdTree(){
        size = 0;
        root = null;
    }// construct an empty set of points
    public  boolean isEmpty(){
        return root == null;
    }                      // is the set empty?
    public   int size(){
        return size;
    }                         // number of points in the set
    public           void insert(Point2D p){
        size++;
        if(p==null){
            throw new IllegalArgumentException();
        }
        if(root ==null){
            root = put(p,root);
        }
        else{
            put(p,root);
        }
    }
    public Node put(Point2D p ,Node n){
        if(n == null){
            return new Node(p);
        }//先塞到root，再判断
        //如果xpart是真，则判断x
        //假则判断y
        if(xpart){
            xpart = false;
            if(p.x()<n.p.x()){
                n.left = put(p,n.left);
            }else{
                n.right = put(p,n.right);
            }

        }else{
            xpart = true;
            if(p.y()<n.p.y()){
                n.left = put(p,n.left);
            }else{
                n.right = put(p,n.right);
            }
        }
        return null;
    }// add the point to the set (if it is not already in the set
    public boolean contains (Point2D p){
        return false;
    }            // does the set contain point p?
    //public              void draw()                         // draw all points to standard draw
    //public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    //public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args){
        KdTree tree = new KdTree();
        Point2D p1 = new Point2D(1,2);
        tree.insert(p1);
        Point2D p2 = new Point2D(2,3);
        tree.insert(p2);
        Point2D p3 = new Point2D(1,4);
    }

}
