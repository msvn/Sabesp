package com.prime.app.agvirtual.jsf.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;

public class DateConverterAdmin extends DateTimeConverter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String data) {		
			
		if( data != null && !data.equals("") ){
			 Pattern datePattern = Pattern.compile("(\\d{2})/(\\d{2})/(\\d{4})");
			    Matcher dateMatcher = datePattern.matcher(data);
			    if (!dateMatcher.find())
			    {
					FacesContext.getCurrentInstance().addMessage(null,
						    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data inválida", null));
			    }  
		}
		
		       

		
		return super.getAsObject(arg0, arg1, data);
	}	
	
	
	

}
