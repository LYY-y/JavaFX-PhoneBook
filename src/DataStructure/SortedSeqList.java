package DataStructure;

/**排序顺序表类（升序）*/
public class SortedSeqList<T extends Comparable<? super T>> extends SeqList<T> {
    public SortedSeqList() {
        super();
    }

    public SortedSeqList(int length) {
        super(length);
    }

    public SortedSeqList(T[] values) {
        super(values.length);
        for (int i=0; i<values.length; i++){
            this.insert(values[i]);
        }
    }

    /**顺序排序表的拷贝构造方法，深拷贝*/
    public SortedSeqList(SeqList<? extends T> list) {
        super(list.n);
        for (int i=0; i<list.n; i++){
            this.insert(list.get(i));
        }
    }

    /**顺序排序表的拷贝构造方法，深拷贝，list引用实例，参数类型赋值相容*/
    public SortedSeqList(SortedSeqList<? extends T> list) {
        super(list.n);
    }

    /**插入x，根据x对象大小顺序查找确定插入位置，插入在等值节点之前，返回x序号
     * 调用T 的CompareTo（）方法比较对象大小 P31*/
    @Override
    public int insert(T x) {
        int i=0;
        if (this.isEmpty()){
            return super.insert(i,x);
        }
        for (i=0; i<this.n; i++){
            if (x.compareTo(this.get(i))<=0){
                break;
            }
        }
        return super.insert(i,x);
    }

    /**插入不重复元素。查找不成功时，按值插入*/
    @Override
    public int insertDifferent(T x){
        return super.search(x)==-1 ? this.insert(x) : -1;
    }

    /**不支持父类的方法，将其覆盖并抛出异常*/
    @Override
    public void set(int i, T x){
        throw new UnsupportedOperationException("set(int i, T x)");
    }

    /**不支持父类的方法，将其覆盖并抛出异常*/
    @Override
    public int insert(int i, T x) {
        throw new UnsupportedOperationException("insert(int i, T x)");
    }

    /**顺序查找首次出现的与key相等元素，返回元素序号i（0<=i<n）；若查找不成功，则返回-1，覆盖*/
    @Override
    public int search(T key){
        if (key==null){
            throw new NullPointerException("key=null");
        }
        for (int i=0; i<this.n; i++){
            if (key.compareTo(this.get(i))==0){
                return i;
            }
            if (key.compareTo(this.get(i))<0){
                break;
            }
        }
        return -1;
    }

    /**删除首次出现的与key相等元素，返回被删除元素；查找不成功返回null*/
    @Override
    public T remove(T key){
        return this.remove(this.search(key));
    }

    @Override
    public void addAll(SeqList<? extends T> list) {
        super.addAll(list);
    }

    /**P59 实验题2_2
     * 返回包含max以内所有素数的排序顺序表*/
    public SortedSeqList<Integer> createPrime(int max) {
        SortedSeqList<Integer> sortedSeqList=new SortedSeqList<Integer>();
        boolean[] nums = new boolean[max+1];
        Integer[] primeArr = new Integer[max+1];
        int countPrime = 0;
        nums[1] = true;
        for (int i = 2; i <= max; i++) {
            if (!nums[i]) {
                primeArr[++countPrime] = i;
                sortedSeqList.insert(i);
            }
            for (int j = 1; j <= countPrime && i * primeArr[j] <= max; ++j) {
                nums[i * primeArr[j]] = true;
                if (i % primeArr[j] == 0) {
                    break;
                }
            }
        }
        return sortedSeqList;
    }
}
