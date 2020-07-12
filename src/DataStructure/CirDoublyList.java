package DataStructure;

/**循环双链表*/
public class CirDoublyList<T> {
    /**头指针*/
    public DoubleNode<T> head;

    /**构造空循环双链表*/
    public CirDoublyList() {
        this.head=new DoubleNode<T>();
        this.head.next=this.head;
        this.head.prev=this.head;
    }
    /**重载构造方法*/
    public CirDoublyList(T[] values){
        this();
       DoubleNode<T> p=this.head;
       for (int i=0; i<values.length; i++){
           p.next=new DoubleNode<T>(values[i],p,this.head);
           p=p.next;
       }
       this.head.prev=p;
    }

    /**重载构造方法，深拷贝*/
    public CirDoublyList(CirDoublyList<T> list){
        this();
        DoubleNode<T> p=this.head;
        DoubleNode<T> listNode=list.head.next;
//        while (listNode.next!=list.head){
//            p.next=new DoubleNode<T>(listNode.data,p,p.next.next);
//            p=p.next;
//            listNode=listNode.next;
//        }
//        p.next=new DoubleNode<T>(listNode.data,p,this.head);
//        this.head.prev=p.next;
//以下更优
        while (listNode!=list.head){
            p.next=new DoubleNode<T>(listNode.data,p,this.head);
            p=p.next;
            listNode=listNode.next;
        }
        this.head.prev=p;
    }

    /**判断循环双链表是否为空*/
    public boolean isEmpty(){
        return this.head.next==this.head;
    }

    public int size(){
        DoubleNode<T> p=this.head.next;
        int count;
        for (count=0; p!=this.head; count++){
            p=p.next;
        }
        return count;
    }

    public T get(int i){
        DoubleNode<T> p=this.head;
        for (int k=0; p.next!=this.head && k<=i; k++){
            p=p.next;
        }
        return p.data;
    }

    public void set(int i, T x){
        DoubleNode<T> p=this.head;
        for (int k=0; p.next!=this.head && k<=i; k++){
            p=p.next;
        }
        p.data=x;
    }

    @Override
    public String toString(){
        String string=this.getClass().getName()+"(";
        DoubleNode<T> p=this.head.next;
        if (p == this.head){
            return string+")";
        }
        while (p.next!=this.head){
            string+=p.data+";";
            p=p.next;
        }
        string+=p.data.toString()+")";
        return string;
    }

    /**插入x为第i个元素，x!=null，返回x结点。对i容错，若i<0，则头插入;若i>长度n，则尾插入*/
    public DoubleNode<T> insert(int i, T x){
        if (x==null){
            throw new NullPointerException("x==null");
        }
        DoubleNode<T> front=this.head;
        for (int j=0; front.next!=this.head && j<i; j++){
            front=front.next;
        }
        DoubleNode<T> q=new DoubleNode<T>(x,front,front.next);
        front.next.prev=q;
        front.next=q;
        return q;
    }

    /**尾插入x元素，返回x结点。算法在头结点之前插入，O（1）*/
    public DoubleNode<T> insert(T x){
        if (x==null){
            throw new NullPointerException("x==null");
        }
        DoubleNode<T> p=new DoubleNode<T>(x,this.head.prev,this.head);
        this.head.prev.next=p;
        this.head.prev=p;
        return p;
    }

    public T remove(int i){
        DoubleNode<T> p=this.head.next;
        for (int k=0; k<i && p!=this.head; k++){
            p=p.next;
        }
        p.prev.next=p.next;
        p.next.prev=p.prev;
        return p.data;
    }

    public void clear(){
        this.head.next=this.head;
    }

    /**查找，比相等*/
    public DoubleNode<T> search(T key){
        if (key==null){
            throw new NullPointerException("key==null");
        }
        DoubleNode<T> p=this.head;
        while (p.next!=this.head){
            p=p.next;
            if (key.equals(p.data)){
                return p;
            }
        }
        return null;
    }

    /**尾插入*/
    public DoubleNode<T> insertDifferent(T x){
        if (x==null){
            throw new NullPointerException("x==null");
        }
        DoubleNode<T> p=this.head;
        while (p.next!=this.head){
            p=p.next;
            if (x.equals(p.data)){
                return null;
            }
        }
        p.next=new DoubleNode<T>(x,p,this.head);
        p.next.prev=p;
        this.head.prev=p.next;
        return p.next;
    }

    /**删除，重载
     * 调用查找，比相等，运行时多态*/
    public T remove(T key){
        if (key==null){
            throw new NullPointerException("key==null");
        }
        DoubleNode<T> p=this.search(key);
        if (p!=null){
            p.prev.next=p.next;
            p.next.prev=p.prev;
            return p.data;
        }
        return null;
    }

    @Override
    public boolean equals(Object obj){
        CirDoublyList<T> ObjCirDoublyList=(CirDoublyList<T>)obj;
        DoubleNode<T> objNode=((CirDoublyList<T>) obj).head.next;
        DoubleNode selfNode=this.head.next;
        while ((objNode!=((CirDoublyList<T>) obj).head || selfNode!=this.head) && objNode.data.equals(selfNode.data)){
            objNode=objNode.next;
            selfNode=selfNode.next;
        }
        return (objNode==((CirDoublyList<T>) obj).head && selfNode==this.head);
    }

    /**合并连接*/
    public void addAll(CirDoublyList<T> list){
        DoubleNode<T> selfFirstNode=this.head.prev;
        DoubleNode<T> selfLastNode=this.head.prev;
        DoubleNode<T> listFirstNode=list.head.next;
        DoubleNode<T> listLastNode=list.head.prev;

        selfLastNode.next=listFirstNode;
        listFirstNode.prev=selfLastNode;
        listLastNode.next=this.head;
        selfFirstNode.prev=listLastNode;
        listFirstNode=this.head;
        listLastNode=this.head;
    }

    /**返回所有元素的描述字符串，元素次序从后往前*/
    public String toPreviousString(){
        String string=this.getClass().getName()+"（";
        DoubleNode<T> p=this.head;
        while (p.prev.prev!=this.head){
            p=p.prev;
            string+=p.data.toString()+"，";
        }
        p=p.prev;
        string+=p.data.toString()+"）";
        return string;
    }

    /**删除最后一个元素，返回被删除元素。若链表空，则返回null*/
    public T removeLast(){
        if (this.head.next==this.head||this.head.prev==this.head){
            return null;
        }
        DoubleNode<T> p=this.head.prev;
        p.prev.next=this.head;
        this.head.prev=p.prev;
        return p.data;
    }
}
