package com.prime.app.agvirtual.webservice;

public class Name {
    private String name;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return super.toString()+": name="+getName();
    }
}
