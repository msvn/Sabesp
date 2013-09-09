package com.prime.app.agvirtual.mbean;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource
public class TestMBean {

    private int age;

    @ManagedAttribute
    public int getAge() {
        return age;
    }

    @ManagedOperation
    @ManagedOperationParameter(name = "years", description = "Years to grow")
    public void growOlder(int years) {
        this.age += years;
    }

}