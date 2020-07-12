package sample;

import DataStructure.CirDoublyList;

//封装姓氏和主表的映射关系
public class IndexItem {
    private char lastName;
    private CirDoublyList<Person> cirDoublyList;

    public IndexItem(char lastName, CirDoublyList<Person> cirDoublyList) {
        this.lastName = lastName;
        this.cirDoublyList = cirDoublyList;
    }

    public char getLastName() {
        return lastName;
    }

    public void setLastName(char lastName) {
        this.lastName = lastName;
    }

    public CirDoublyList<Person> getCirDoublyList() {
        return cirDoublyList;
    }

    public void setCirDoublyList(CirDoublyList<Person> cirDoublyList) {
        this.cirDoublyList = cirDoublyList;
    }
}
