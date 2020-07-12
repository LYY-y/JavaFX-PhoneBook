package DataStructure;

public class Node<T> {
    /**数据域，存储数据元素*/
    public T data;
    /**地址域，引用后继结点*/
    public Node<T> next;

    /**构造节结点*/
    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public Node() {
        this(null,null);
    }

    @Override
    public String toString() {
        return this.data.toString();
    }


}
