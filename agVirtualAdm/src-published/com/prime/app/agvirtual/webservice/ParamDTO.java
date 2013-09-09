package com.prime.app.agvirtual.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="param")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParamDTO {
    @XmlElement(name="param1", required=true)
    private String param1;
    @XmlElement(name="param2", required=true)
    private String param2;
    
    public String getParam1() {
        return param1;
    }
    public void setParam1(String param1) {
        this.param1 = param1;
    }
    public String getParam2() {
        return param2;
    }
    public void setParam2(String param2) {
        this.param2 = param2;
    }
    
    @Override
    public String toString() {
        return super.toString()+"param1:"+getParam1()+", param2:"+getParam2();
    }
}
