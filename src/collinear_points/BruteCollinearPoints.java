//package algorithm.src.collinear_points;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments;
    public BruteCollinearPoints(Point[] points){
        if(!valid(points)){
            throw new IllegalArgumentException();
        }//遍历
        //要的是按照自然顺序的点形成线段
        //没有按顺序添加完整线段：
        //即使发现了一组共线点，你的实现可能没有按照从最小点到最大点的顺序构造线段
        // （例如 (0, 10000) -> (3000, 7000) -> (7000, 3000) -> (10000, 0)）
        // 而是随意选择了两个端点来定义线段，这导致与参考实现的完整线段不一致。
        Point[] copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy);
        //Test 11: check that data type does not mutate the constructor argument
        //要进行防御型复制
        //防御性复制（Defensive Copying）是编程中的一种策略，用来保护对象的内部状态免受外部修改
        //这在设计类时特别重要，尤其是当类的内部状态依赖于外部传入的数据时。

        segments = new ArrayList<>();
        for (int i = 0; i < copy.length; i++) {
            for (int j = i + 1; j < copy.length; j++) {
                for (int k = j + 1; k < copy.length; k++) {
                    for (int l = k + 1; l < copy.length; l++) {
                        Point p = copy[i];
                        Point q = copy[j];
                        Point r = copy[k];
                        Point s = copy[l];

                        // 检查 4 个点是否共线
                        if (collinear(p, q, r, s)) {
                            // 创建线段
                            segments.add(new LineSegment(p,s));
                        }
                    }
                }
            }
        }

    }    // finds all line segments containing 4 points

    public int numberOfSegments(){
        return segments.size();
    }        // the number of line segments
    public LineSegment[] segments(){
        return segments.toArray(new LineSegment[0]);
    }
    private boolean valid(Point[] points){
        if(points==null){
            return false;
        }//
        for (Point p : points)
            if (p == null) return false;
        for(int i = 0;i< points.length;i++){

            for(int j =i+1;j< points.length;j++){
                if(points[i].compareTo(points[j])==0){
                    return false;//这一步有问题，如果i+1是null则抛出nullpointerexception
                }
            }
        }
        for (Point p : points)
            if (p == null) throw new IllegalArgumentException();
        return true;
    }
    private boolean collinear(Point p1, Point p2, Point p3, Point p4){//共线
        if(p1.slopeTo(p2)==p1.slopeTo(p3)&&
                p1.slopeTo(p3)== p1.slopeTo(p4)){
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
