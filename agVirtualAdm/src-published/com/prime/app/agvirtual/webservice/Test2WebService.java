package com.prime.app.agvirtual.webservice;

import java.math.BigDecimal;
import java.util.Date;

public interface Test2WebService {
    ObjList test1(ObjList objList);
    
    String test2(Date data, BigDecimal decimal, Integer intNum, Long longNum, Boolean flag);
    
    ObjListInteger test3(ObjList objList, Integer count);
    
    Name test4(Name name);
}
