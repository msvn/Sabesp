package com.prime.app.agvirtual.web.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.prime.app.agvirtual.dao.SQLDao;
import com.prime.infra.web.jsf.BasicBBean;

@Component
@Scope(value="session")
public class SqlBBean extends BasicBBean implements Serializable {
	private Boolean exibeSQL = true;
	private String query = "";
	private ArrayList<String> resultList = new ArrayList<String>();	

    @Autowired
    private SQLDao dao;
	
	public void runQuery(ActionEvent event) {
		List<Object[]> l = dao.executeQuery(query);
		for(Object[] s : l){
			String aux = "";
			for (int i = 0; i < s.length; i++) {
//				System.out.println(s[i].toString());
				aux = aux.concat(s[i].toString()+" | ");
			}
			resultList.add(aux); 
		}
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Boolean getExibeSQL() {
		return exibeSQL;
	}

	public void setExibeSQL(Boolean exibeSQL) {
		this.exibeSQL = exibeSQL;
	}

	public ArrayList<String> getResultList() {
		return resultList;
	}

	public void setResultList(ArrayList<String> resultList) {
		this.resultList = resultList;
	}
	
}
