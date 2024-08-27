

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque <Item> implements Iterable<Item>{
    //采用列表生成
    private class Node{
        Item item;
        Node next;
        Node pre;//双向
        Node(Item item1){
            item = item1;
            next = this;
            pre = this;
        }
    }
    private Node sentinel;
    private int size;

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item>{
        Node node;
        @Override
        public boolean hasNext() {
//            if(node.next!=null){//循环数组这样子无限循环了
//                return true;
//            }else return false;
            if(node==sentinel){
                return false;
            }else return true;
        }

        @Override
        public Item next() {
            if(node==sentinel){
                throw new NoSuchElementException();
            }
            Item old = node.item;
            node=node.next;//更新节点
            return old;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        DequeIterator(){
            node=sentinel.next;
        }
    }

    // construct an empty deque
    public Deque(){
        sentinel=new Node(null);
        size=0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        if(size==0){
            return true;
        }else{
            return false;
        }
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if(item==null){
            throw new IllegalArgumentException();
        }
        Node n = new Node(item);
        //当只有两个的sentinel和n节点时候自然而然地就循环了。
        //使得n在前面.
        n.next=sentinel.next;
        n.pre=sentinel;
        //更新sentinel，使得它在最前面
        sentinel.next.pre=n;
        sentinel.next=n;
        size++;
    }

    // add the item to the back
    public void addLast(Item item){
        if(item==null){
            throw new IllegalArgumentException();
        }
        Node n = new Node(item);
        //使得n在最后面
        n.pre=sentinel.pre;
        n.next=sentinel;
        //更新sentinel,使得它在最后面
        sentinel.pre.next=n;//原先最后一个节点指向n
        sentinel.pre=n;//循环
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        //先保存一下
        Node old = new Node(sentinel.next.item);
        sentinel.next.next.pre=sentinel;
        sentinel.next=sentinel.next.next;//删除第一个节点
        size--;
        return old.item;

    }

    // remove and return the item from the back
    public Item removeLast(){
        if(size==0){
            throw new NoSuchElementException();
        }
        Node old = new Node(sentinel.pre.item);
        sentinel.pre.pre.next=sentinel;
        sentinel.pre=sentinel.pre.pre;//删除倒数第一个
        size--;
        return old.item;
    }

    // return an iterator over items in order from front to back
//    public Iterator<Item> iterator()

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> num = new Deque<>();
        num.addFirst(2);
        num.addFirst(4);
        num.addFirst(4);
        for(Integer n :num){
            System.out.println(n);
        }
        //集合的类型与迭代器返回的类型: for-each循环的类型参数需要匹配集合中实际存储的元素类型
        // ，而不是它内部实现中的任何辅助类（如Node）。
        //Iterator<Item>接口: 当Deque类实现了Iterable<Item>，
        // 它的迭代器返回的就是Item类型的对象，而不是Node。
    }

}
