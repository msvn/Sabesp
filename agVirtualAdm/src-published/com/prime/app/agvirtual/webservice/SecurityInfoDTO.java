package com.prime.app.agvirtual.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SecurityInfoDTO {
    @XmlElement(name="userName", required=true)
    private String userName;
    @XmlElement(name="remoteAddr", required=true)
    private String remoteAddr;
    @XmlElement(name="systemId", required=true)
    private String systemId;
    @XmlElement(name="loginTimestamp", required=true)
    private String loginTimestamp;

    public SecurityInfoDTO() {
    }
    
    public SecurityInfoDTO(String loginTimestamp, String remoteAddr, String systemId, String userName) {
        this.loginTimestamp = loginTimestamp;
        this.remoteAddr = remoteAddr;
        this.systemId = systemId;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getLoginTimestamp() {
        return loginTimestamp;
    }

    public void setLoginTimestamp(String loginTimestamp) {
        this.loginTimestamp = loginTimestamp;
    }

    @Override
    public String toString() {
        return super.toString() + "userName: " + getUserName() + ", remoteAddr: " + getRemoteAddr() + ", systemId: "
            + getSystemId() + ", loginTimestap: " + getLoginTimestamp();
    }
}
