package com.allen.androiddevcoder.activity;

import util.MLog;

/**
 * 作者: allen on 16/7/15.
 */
public class TestReflect {
    private int gradle = 18;
    private String school ="我是私有变量不可修改";

    public int getGradle() {
        return gradle;
    }

    private String getSchool() {
        MLog.d("lifeCycle", "反射调用私有方法: "+school);
        return school;
    }

    public TestReflect(String name, int age) {
        Name = name;
        this.age = age;
    }

    private String Name ;
    private int age;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestReflect{" +
                "Name='" + Name + '\'' +
                ", age=" + age +
                '}';
    }
}
