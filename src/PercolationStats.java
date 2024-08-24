import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    //double[] thresholds = new double[trials];
    //在这段代码中，trials 的默认值是 0，因为在类初始化时，int 类型的成员变量 trials 默认被初始化为 0。
    // 因此，thresholds 数组的大小是 0。
    // 当你尝试在构造函数中访问 thresholds[t] 时，就会出现 ArrayIndexOutOfBoundsException。
    // perform independent trials on an n-by-n grid
    private double[] thresholds;

    public PercolationStats(int n, int trials) {

        if ((trials <= 0) || (n <= 0)) {
            throw new IllegalArgumentException("参数错误");
        }
        //一直尝试
        this.trials = trials;
        thresholds = new double[trials];

        for (int t = 0; t < trials; t++) {
            Percolation perc = new Percolation(n);

            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(n)+1;//1-base
                int col = StdRandom.uniformInt(n)+1;
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }

            thresholds[t] = (double) perc.numberOfOpenSites() / (n * n);

        }


    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: java PercolationStats <grid size> <number of trials>");
        }
   //     System.out.println("Arguments received: " + args[0] + " " + args[1]);  // 添加这行来调试参数输入

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
//        int n =-20;
//        int trials=-100;

        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");

    }
}
