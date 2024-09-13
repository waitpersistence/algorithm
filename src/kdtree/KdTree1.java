package kdtree;

import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    //将点与矩形绑定
    private int size;
    private class Node{
        RectHV rect;
        Point2D p;
        Node left;
        Node right;
        Node(Point2D p1,RectHV rect){
            p=p1;
            this.rect = rect;
            left = null;
            right = null;
        }
    }

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
            root = put(p,root,true,0,0,1,1);
        }
        else{
            put(p,root,true,);
        }
    }
    public Node put(Point2D p,Node n, boolean xpart, double xmin, double ymin, double xmax,
                    double ymax){
        if(n == null){
            return new Node(p,new RectHV(xmin, ymin, xmax, ymax));
        }//先塞到root，再判断
        //如果xpart是真，则判断x
        //假则判断y
        if(xpart){
            if(p.x()<n.p.x()){
                n.left = put(p,n.left,false,xmin,ymin,xmax,ymax);
            }else{
                n.right = put(p,n.right,false,xmin,ymin,xmax,ymax);
            }

        }else{
            if(p.y()<n.p.y()){
               n.left = put(p,n.left,true,xmin,ymin,xmax,ymax);

            }else{
               n.right = put(p,n.right,true,xmin,ymin,xmax,ymax);
            }
        }
        return n;//应当返回当前节点，而不是子节点
    }// add the point to the set (if it is not already in the set
    public boolean contains (Point2D p){
        //查找
        if(p==null){
            throw new IllegalArgumentException();
        }
        if(root ==null){
            return false;
        }
        return search(p,root,true);
    }            // does the set contain point p?
    private boolean search(Point2D p ,Node n,boolean xpart){
        if(n==null){
            return false;
        }
        if(xpart){
            if(p.x()<n.p.x()){
                return search(p,n.left,false);//向左
            }else if(p.x()>n.p.x()){
                return search(p,n.right,false);//向右
            }else if(n.p.compareTo(p)==0){
                return true;
            }

        }else{
            if(p.y()<n.p.y()){
                return search(p,n.left,true);
            }else if(p.y()>n.p.y()){
                return search(p,n.right,true);
            }
            else if(n.p.compareTo(p)==0){
                return true;
            }
        }
        return false;
    }
public void draw() {
    draw(root, 0.0, 0.0, 1.0, 1.0, true);
}

    // 递归绘制每个节点及其分割线
    private void draw(Node n, double xmin, double ymin, double xmax, double ymax, boolean vertical) {
        if (n == null) return;

        // 绘制节点
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(n.p.x(), n.p.y());
        StdDraw.setPenRadius();

        // 绘制分割线
        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED); // 垂直分割线用红色
            StdDraw.line(n.p.x(), ymin, n.p.x(), ymax);
            // 递归处理左右子树
            draw(n.left, xmin, ymin, n.p.x(), ymax, !vertical);
            draw(n.right, n.p.x(), ymin, xmax, ymax, !vertical);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE); // 水平分割线用蓝色
            StdDraw.line(xmin, n.p.y(), xmax, n.p.y());
            // 递归处理上下子树
            draw(n.left, xmin, ymin, xmax, n.p.y(), !vertical);
            draw(n.right, xmin, n.p.y(), xmax, ymax, !vertical);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> arrayList = new ArrayList<>();
        if (!isEmpty()) range(root, rect, arrayList);
        return arrayList;
    }

    private void range(Node nd, RectHV rect, ArrayList<Point2D> arrayList) {
        if (rect.contains(nd.p)) arrayList.add(nd.p);
        if (nd.left != null && rect.intersects(nd.left.rect))
            range(nd.left, rect, arrayList);
        if (nd.right != null && rect.intersects(nd.right.rect))
            range(nd.right, rect, arrayList);
    }
    //public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args){
        KdTree tree = new KdTree();
        Point2D p1 = new Point2D(0.1,0.2);
        tree.insert(p1);
        Point2D p2 = new Point2D(0.2,0.3);
        tree.insert(p2);
        Point2D p3 = new Point2D(0.3,0.4);
        tree.insert(p3);
        tree.insert(new Point2D(0.2,0.4));
        System.out.println(tree.contains(p1));
        System.out.println(tree.contains(new Point2D(2,4)));
        tree.draw();
    }

}
