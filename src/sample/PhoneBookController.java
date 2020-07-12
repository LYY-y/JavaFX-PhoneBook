package sample;

import DataStructure.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PhoneBookController {
    @FXML
    private TextField add_name;
    @FXML
    private TextField add_phone;
    @FXML
    private TextField delete_name;
    @FXML
    private TextField update_name;
    @FXML
    private TextField update_phone;
    @FXML
    private TextField search_name;
    @FXML
    private TextField search_lastName;
    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, String> name;
    @FXML
    private TableColumn<Person, String> phone;

    private final ObservableList<Person> data = FXCollections.observableArrayList();


    //索引表
    SeqList<IndexItem> indexList;
    //主表
    CirDoublyList<Person> phonebook;

    //索引表长度
    private int len;


    //初始化电话簿
    public PhoneBookController() {
        Person[] persons = new Person[]{new Person("李华","123456"),new Person("李展","987654")};
        phonebook = new CirDoublyList<>(persons);
        IndexItem indexItem = new IndexItem('李', phonebook);
        indexList = new SeqList<>();
        indexList.insert(indexItem);
        len = indexList.size();
    }

    @FXML
    public void add(){
        String name = this.add_name.getText();
        String phone = this.add_phone.getText();
        Person person = new Person(name, phone);
        char lastName = name.charAt(0);
        int low = 0;
        int high = len;
        int mid = 0;
        while (low <= high && low < len && high >= 0){
            mid = low + ((high - low) >> 1);
            if (lastName - indexList.get(mid).getLastName() > 0){
                low = mid + 1;
            }else if (lastName - indexList.get(mid).getLastName() < 0){
                high = mid - 1;
            }else {
                indexList.get(mid).getCirDoublyList().insertDifferent(person);
                System.out.println("添加成功！");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("增添信息反馈");
                alert.setHeaderText("新增联系人成功！");
                alert.setContentText("新增联系人信息："+person.toString());
                alert.showAndWait();
                return;
            }
        }
        CirDoublyList<Person> phonebook = new CirDoublyList<>();
        phonebook.insert(0,person);
        IndexItem indexItem = new IndexItem(lastName, phonebook);
        indexList.insert(low,indexItem);
        len+=1;
        System.out.println("添加成功！");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("增添信息反馈");
        alert.setHeaderText("新增联系人成功！");
        alert.setContentText("新增联系人信息："+person.toString());
        alert.showAndWait();
    }

    @FXML
    public void delete(){
        String name = this.delete_name.getText();
        int index = binarySearch(indexList,name.charAt(0));
        if (index != -1) {
            CirDoublyList<Person> phonebookList = indexList.get(index).getCirDoublyList();
            DoubleNode<Person> p = phonebookList.head;
            while (p.next.data != null) {
                if (name.equals(((Person)p.next.data).getName())) {
                    p.next = p.next.next;
                    p.next.prev = p.next.prev.prev;
                    System.out.println("删除成功！");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("删除信息反馈");
                    alert.setHeaderText(null);
                    alert.setContentText("删除联系人成功！");
                    alert.showAndWait();
                    return;
                }
                p = p.next;
            }
        }
        System.out.println("删除失败，电话簿中无该数据！");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("删除信息反馈");
        alert.setHeaderText(null);
        alert.setContentText("删除失败，电话簿中无该数据！");
        alert.showAndWait();
    }

    @FXML
    public void update(){
        String name = this.update_name.getText();
        String phone = this.update_phone.getText();
        Person person = new Person(name, phone);
        int index = binarySearch(indexList,name.charAt(0));
        if (index != -1) {
            CirDoublyList<Person> phonebookList = indexList.get(index).getCirDoublyList();
            DoubleNode<Person> p = phonebookList.head.next;
            while (p.data != null) {
                if (name.equals(p.data.getName())) {
                    p.data = person;
                    System.out.println("修改成功！");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("修改信息反馈");
                    alert.setHeaderText("修改联系人信息成功！");
                    alert.setContentText("修改后联系人信息为："+person.toString());
                    alert.showAndWait();
                    return;
                }
                p = p.next;
            }
        }
        System.out.println("修改信息失败，电话簿中无该数据！");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("修改信息反馈");
        alert.setHeaderText(null);
        alert.setContentText("修改信息失败，电话簿中无该数据！");
        alert.showAndWait();
    }

    //用折半查找法检索索引表
    public int binarySearch(SeqList<IndexItem> indexList, char lastName){
        int low = 0;
        int high = len;
        while (low <= high){
            int mid = low + ((high - low) >> 1);
            if (lastName - indexList.get(mid).getLastName() > 0){
                low += 1;
            }else if (lastName - indexList.get(mid).getLastName() < 0){
                high -= 1;
            }else {
                return mid;
            }
        }
        return -1;
    }

    @FXML
    public void search(){
        String name = this.search_name.getText();
        int index = binarySearch(indexList, name.charAt(0));
        if (index != -1){
            phonebook = indexList.get(index).getCirDoublyList();
            DoubleNode<Person> p = phonebook.head.next;
            while (p.data != null){
                if (name.equals(p.data.getName())){
                    System.out.println("查询结果为："+p.data.toString());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("查询信息反馈");
                    alert.setHeaderText("查询信息成功！");
                    alert.setContentText("查询信息："+p.data.toString());
                    alert.showAndWait();

                    ObservableList<Person> list=FXCollections.observableArrayList();
                    list.addAll(p.data);//将对象赋值到 集合中
                    tableView.setItems(list);//将集合的值 存储到tableView里
                    TableColumn<Person, String > table_name=new TableColumn<Person, String>("name");//创建TableColumn  列名为序号
                    TableColumn<Person, String> table_phone=new TableColumn<Person, String>("phone");
                    /**
                     * 反射取值
                     */
                    table_name.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));//相当于getid
                    table_phone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));//getName
                    /**
                     * 合并列
                     */
                    TableColumn<Person, Object> group=new TableColumn<Person, Object>("基本信息");
                    group.getColumns().add(table_name);
                    group.getColumns().add(table_phone);
                    tableView.getColumns().add(group);

                    return;
                }
                p = p.next;
            }
        }
        System.out.println("查询结果为空！");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("查询信息反馈");
        alert.setHeaderText(null);
        alert.setContentText("查询结果为空！");
        alert.showAndWait();
    }


    public CirDoublyList<Person> quickSort(CirDoublyList<Person> cirDoublyList, int low, int high){
        int l = low;
        int r = high;
        Person index = cirDoublyList.get(l);
        Person left  = cirDoublyList.get(l);
        Person right = cirDoublyList.get(r);
        while (l < r) {
            while (l < r && right.compareTo(index) >= 0){
                r--;
                right = cirDoublyList.get(r);
            }
            if (l < r){
                cirDoublyList.set(l, right);
                l++;
                left = cirDoublyList.get(l);
            }
            while (l < r && left.compareTo(index) < 0){
                l++;
                left = cirDoublyList.get(l);
            }
            if (l < r) {
                cirDoublyList.set(r, left);
                r--;
                right = cirDoublyList.get(r);
            }
            cirDoublyList.set(l, index);
            quickSort(cirDoublyList, low, l-1);
            quickSort(cirDoublyList, l+1, high);
        }
        return cirDoublyList;
    }

    //同姓记录排序输出
    public void searchLastName(){
        char lastName = this.search_lastName.getText().charAt(0);
        int index = binarySearch(indexList, lastName);
        if (index != -1){
            CirDoublyList<Person> phonebook = indexList.get(index).getCirDoublyList();
            phonebook = quickSort(phonebook, 0, phonebook.size());
            System.out.println(phonebook);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("查询信息反馈");
            alert.setHeaderText("查询信息成功！");
            alert.setContentText("查询信息："+phonebook.toString());
            alert.showAndWait();

            ObservableList<Person> list=FXCollections.observableArrayList();
            DoubleNode p = phonebook.head.next;
            while (p.data != null){
                list.add((Person) p.data);
                p = p.next;
            }
            tableView.setItems(list);//将集合的值 存储到tableView里
            TableColumn<Person, String > table_name=new TableColumn<Person, String>("name");//创建TableColumn  列名为序号
            TableColumn<Person, String> table_phone=new TableColumn<Person, String>("phone");
            /**
             * 反射取值
             */
            table_name.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));//相当于getid
            table_phone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));//getName
            /**
             * 合并列
             */
            TableColumn<Person, Object> group=new TableColumn<Person, Object>("基本信息");
            group.getColumns().add(table_name);
            group.getColumns().add(table_phone);
            tableView.getColumns().add(group);

            return;
        }
        System.out.println("查询结果为空！");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("查询信息反馈");
        alert.setHeaderText(null);
        alert.setContentText("查询结果为空！");
        alert.showAndWait();
    }


    @FXML
    public void add_reset(){
        this.add_name.clear();
        this.add_phone.clear();
    }

    @FXML
    public void delete_reset(){
        this.delete_name.clear();
    }

    @FXML
    public void update_reset(){
        this.update_name.clear();
        this.update_phone.clear();
    }

    @FXML
    public void search_reset(){
        this.search_name.clear();
    }

    @FXML
    public void searchLastName_reset(){
        this.search_lastName.clear();
    }

    public void searchAll(){
        for (int i = 0; i < len; i++){
            System.out.println(indexList.get(i).getCirDoublyList().toString());
        }
    }


}
