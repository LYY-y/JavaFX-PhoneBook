package DataStructure;

/**双链表结点类，T指定结点的元素类型*/
public class DoubleNode<T>{
    /**数据域，存储数据元素*/
    public T data;
    /**地址域，prev只向前驱结点，next指向后继结点*/
    public DoubleNode prev,next;

    /**构造方法重载*/
    public DoubleNode(T data, DoubleNode prev, DoubleNode next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public DoubleNode(T data) {
        this(data,null,null);
    }

    public DoubleNode() {
        this(null,null,null);
    }

    /**返回结点数据域的描述字符串*/
    @Override
    public String toString() {
        return this.data.toString();
    }


}