

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        if (k == 0) return;//获得第一个数
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        //不需要读取所有数 随机读取数
        int cnt=0;
        while(!StdIn.isEmpty()){
            String s = StdIn.readString();
            if(cnt<=k){
            if(StdRandom.uniformInt(2)==1){
                //读取
                queue.enqueue(s);
            }
            }
        }//随机数
        //水塘抽样算法
        while (k-- > 0) StdOut.println(queue.dequeue());

    }
}

