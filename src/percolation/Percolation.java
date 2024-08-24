import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;  // 渗透网格
    private int n;             // 网格大小
    private WeightedQuickUnionUF uf;//用于连接
    private WeightedQuickUnionUF ufforFull; //用于full
    private int opens;         // 记录开放站点的数量

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0.");
        }
        this.n = n;
        grid = new boolean[n][n];  // 初始化网格，默认是阻塞状态
        uf = new WeightedQuickUnionUF(n * n + 2);  // 初始化并查集结构，大小为 n*n + 2
        ufforFull = new WeightedQuickUnionUF(n*n+1);//只包含虚拟顶点
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndices(row, col);
        // 将 1-based 索引调整为 0-based 索引
        int row0Based = row - 1;
        int col0Based = col - 1;

        if (!isOpen(row, col)) {
            grid[row0Based][col0Based] = true;  // 将站点设为开放状态
            int index = xyTo1D(row0Based, col0Based);  // 将二维坐标转换为一维索引

            // 如果该站点在顶行，连接到虚拟顶点
            if (row == 1) {
                uf.union(index, n * n);// 连接到虚拟顶点
                ufforFull.union(index,n*n);
            }
            // 如果该站点在底行，连接到虚拟底点
            if (row == n) {
                uf.union(index, n * n + 1);  // 连接到虚拟底点
            }

            // 连接相邻的开放站点
            if (row > 1 && isOpen(row - 1, col)) {
                uf.union(index, xyTo1D(row - 2, col - 1));  // 上方站点
                ufforFull.union(index, xyTo1D(row - 2, col - 1));  // 上方站点
            }
            if (row < n && isOpen(row + 1, col)) {
                uf.union(index, xyTo1D(row, col - 1));  // 下方站点
                ufforFull.union(index, xyTo1D(row, col - 1));  // 下方站点
            }
            if (col > 1 && isOpen(row, col - 1)) {
                uf.union(index, xyTo1D(row - 1, col - 2));  // 左侧站点
                ufforFull.union(index, xyTo1D(row - 1, col - 2));  // 左侧站点
            }
            if (col < n && isOpen(row, col + 1)) {
                uf.union(index, xyTo1D(row - 1, col));  // 右侧站点
                ufforFull.union(index, xyTo1D(row - 1, col));  // 右侧站点
            }
            opens++;
        }
    }

    private int xyTo1D(int row0Based, int col0Based) {
        return row0Based * n + col0Based;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        int row0Based = row - 1;
        int col0Based = col - 1;
        return grid[row0Based][col0Based];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        int row0Based = row - 1;
        int col0Based = col - 1;
        int index = xyTo1D(row0Based, col0Based);
        return ufforFull.find(index) == ufforFull.find(n * n); // 确保比较的是与虚拟顶部节点的连接
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opens;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <= n; i++) {
            if (uf.find(xyTo1D(n - 1, i - 1)) == uf.find(n * n)) {
                return true;
            }
        }
        return false;
    }

    // validates that row and col are within bounds
    private void validateIndices(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Index out of bounds: row " + row + ", col " + col);
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 20;  // 网格大小
        int trials = 1000;  // 试验次数
        double[] thresholds = new double[trials];  // 存储每次试验的渗透阈值

        for (int t = 0; t < trials; t++) {
            Percolation perc = new Percolation(n);

            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;  // 生成 1-based 索引
                int col = StdRandom.uniformInt(n) + 1;  // 生成 1-based 索引
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }

            thresholds[t] = (double) perc.numberOfOpenSites() / (n * n);
        }

        // 计算和打印平均渗透阈值
        double sum = 0.0;
        for (double threshold : thresholds) {
            sum += threshold;
        }
        double meanThreshold = sum / trials;
        System.out.println("Estimated percolation threshold: " + meanThreshold);
    }
}