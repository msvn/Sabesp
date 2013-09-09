package com.prime.app.agvirtual.webservice;

public class ObjListInteger extends ObjList {
    private Integer count;
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
    
    @Override
    public String toString() {
        return super.toString()+", count="+getCount();
    }
}
