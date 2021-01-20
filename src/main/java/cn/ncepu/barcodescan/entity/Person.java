package cn.ncepu.barcodescan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.StringJoiner;

public class Person implements Serializable {
    private String name;
    private String id;
    private String depart;
    private String phone;
    private String temperature;
    @JsonIgnore
    private String grou;

    public Person() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGrou() {
        return grou;
    }

    public void setGrou(String grou) {
        this.grou = grou;
    }

    public Person(String name, String id, String depart, String phone,String temperature) {
        this.name = name;
        this.id = id;
        this.depart = depart;
        this.phone=phone;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", name + "[", "]")
//                .add("身份证号 : '" + id + "'")
                .add("部门 : " + depart + "")
                .add("温度 : " + temperature + " °C")
                .toString();
    }
}
