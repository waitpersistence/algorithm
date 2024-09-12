package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;
public class PointSET {
    TreeSet<Point2D> pointset;
    public         PointSET(){
        pointset = new TreeSet<>();
    }                              // construct an empty set of points
    public           boolean isEmpty(){
        return pointset.isEmpty();
    }                      // is the set empty?

    public               int size(){
        return pointset.size();
    }                         // number of points in the set
    public              void insert(Point2D p){
        test(p);
        pointset.add(p);
    }              // add the point to the set (if it is not already in the set)
    public   boolean contains(Point2D p){
        test(p);
        return pointset.contains(p);
    }            // does the set contain point p?
    public  void draw(){
        for(Point2D p : pointset){
            p.draw();
        }
    }                         // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect){
        if(rect==null){
            throw new IllegalArgumentException();
        }
        TreeSet<Point2D> points = new TreeSet<>();
        for(Point2D p : pointset){
            if(rect.contains(p)){
                points.add(p);
            }
        }
        return points;
    }             // all points that are inside the rectangle (or on the boundary)
    public           Point2D nearest(Point2D p)   {
        test(p);
        double distance = Double.POSITIVE_INFINITY;
        Point2D p1 = new Point2D(0,0);
        for(Point2D p2 : pointset){
            if(p2.distanceTo(p)<distance){
                distance=p2.distanceTo(p);
                p1 = new Point2D(p2.x(),p2.y());
            }
        }
        return p1;
    }          // a nearest neighbor in the set to point p; null if the set is empty
    private void test(Point2D p){
        if(p==null){
            throw new IllegalArgumentException();
        }
    }
    public static void main(String[] args){
        PointSET pp = new PointSET();
        System.out.println(pp.isEmpty());
        Point2D p1 = new Point2D(50,30);
        Point2D p2 = new Point2D(3,4);
        pp.insert(p1);
        pp.insert(p2);
        System.out.println(pp.isEmpty());
        pp.draw();
        System.out.println(pp.nearest(new Point2D(4,5)));
    }                  // unit testing of the methods (optional)
}
