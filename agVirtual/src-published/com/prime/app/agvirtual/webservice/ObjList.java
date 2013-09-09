package com.prime.app.agvirtual.webservice;

import java.util.List;

public class ObjList {
    private List<Name> names;

    public List<Name> getNames() {
        return names;
    }
    
    public void setNames(List<Name> names) {
        this.names = names;
    }
    
    @Override
    public String toString() {
        return super.toString()+": names="+getNames();
    }
}
