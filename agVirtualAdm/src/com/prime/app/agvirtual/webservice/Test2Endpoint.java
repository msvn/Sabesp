package com.prime.app.agvirtual.webservice;

import java.math.BigDecimal;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.prime.app.agvirtual.webservice.Name;
import com.prime.app.agvirtual.webservice.ObjList;
import com.prime.app.agvirtual.webservice.ObjListInteger;
import com.prime.app.agvirtual.webservice.Test2WebService;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.LITERAL)
public class Test2Endpoint implements Test2WebService {


    @WebMethod
    @WebResult(name="resObjList")
    public ObjList test1(@WebParam(name="objList")ObjList objList) {
        System.out.println("test1 params: "+objList);
        return objList;
    }
    
    @WebMethod
    @WebResult(name="resData")
    public String test2(@WebParam(name="data") Date data, 
        @WebParam(name="decimal")BigDecimal decimal, 
        @WebParam(name="intNum")Integer intNum, 
        @WebParam(name="longNum")Long longNum, 
        @WebParam(name="flag")Boolean flag) {
        StringBuilder result = new StringBuilder();
        if (data != null)
            result.append("data:"+data.toString()+", ");
        if (decimal != null)
            result.append("decimal:"+decimal.toString()+", ");
        if (intNum != null)
            result.append("intNum:"+intNum.toString()+", ");
        if (longNum != null)
            result.append("longNum:"+longNum.toString()+", ");
        if (flag != null)
            result.append("flag: "+flag);
        
        String resultString = result.toString();
        System.out.println("test2 params: "+resultString);
        return resultString;
    }
    
    @WebMethod
    @WebResult(name="objListInteger")
    public ObjListInteger test3(@WebParam(name="objList")ObjList objList, @WebParam(name="count")Integer count) {
        ObjListInteger objListInteger = new ObjListInteger();
        if (objList!= null) 
            objListInteger.setNames(objList.getNames());
        objListInteger.setCount(count);
        System.out.println("test3 params: " + objList.toString());
        return objListInteger;
    }
    
    @WebMethod
    @WebResult(name="name")
    public Name test4(@WebParam(name="name")Name name) {
        System.out.println("test4 params: "+name);
        return name;
    }

}
