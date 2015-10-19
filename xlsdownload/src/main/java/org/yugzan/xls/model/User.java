package org.yugzan.xls.model;

import java.time.OffsetDateTime;

/**
 * @author yongzan
 * @date 2015/10/19
 */
public class User {
    private String name;
    private int age;
    private String time;
    
    
    
    
    public User(String name, int age) {
        super();
        this.name = name;
        this.age = age;
        this.time = OffsetDateTime.now().toString();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return time;
    }
    public void setTime(OffsetDateTime time) {
        this.time = time.toString();
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    
}
