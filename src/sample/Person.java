package sample;

import javafx.beans.property.SimpleStringProperty;

public class Person implements Comparable<Person>{
    private String name;
    private String phone;

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "name=" + name +  ", phone=" + phone;
    }

    @Override
    public int compareTo(Person person) {
        int thisNameLen = this.name.length();
        int perNameLen = person.name.length();
        int n = thisNameLen < perNameLen ? thisNameLen : perNameLen;
        for (int i = 1; i < n; i++){
            if (name.charAt(i) < person.name.charAt(i)){
                return -1;
            }else if (name.charAt(i) > person.name.charAt(i)){
                return 1;
            }else {
                continue;
            }
        }
        if (thisNameLen == perNameLen){
            return 0;
        }else if (thisNameLen > perNameLen){
            return 1;
        }else {
            return -1;
        }
    }
}
