

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;  // 用于存储队列元素的数组
    private int size;      // 队列中的元素数量
    // 构造一个空的随机队列
    public RandomizedQueue() {
        items = (Item[]) new Object[2]; // 初始化一个小数组
        size = 0;

    }

    // 判断队列是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    // 返回队列中的元素数量
    public int size() {
        return size;
    }

    // 添加一个元素到队列
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item");
        }
        if (size == items.length) {
            resize(2 * items.length); // 如果数组满了，则调整数组大小
        }
        items[size++] = item; // 将元素添加到数组的末尾
    }

    // 移除并返回一个随机元素
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        //返回一个随机数
        int a = StdRandom.uniformInt(size);
        Item old = items[a];
        items[a]=items[size-1];
        items[size-1]=null;
        size--;
        return old;

    }

    // 返回一个随机元素但不移除它
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int a = StdRandom.uniformInt(size);
        return items[a];
    }

    // 调整数组大小
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // 返回一个独立的随机顺序迭代器
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;
        private int[] idx;
        public RandomizedQueueIterator(){
            //生成一个随机顺序
            idx = new int[size];
            for (int k = 0; k < size; k++){
                idx[k]=k;
            }
            StdRandom.shuffle(idx);//打乱

        }
        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public Item next() {
            Item item = items[idx[i]];
            i++;
            return item;

        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);

        System.out.println("Sample: " + rq.sample());
        System.out.println("Dequeued: " + rq.dequeue());
        System.out.println("Dequeued: " + rq.dequeue());

        for (int item : rq) {
            System.out.println("Iterating: " + item);
        }
    }
    }

    // 单元测试




