package com.prime.app.agvirtual.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class DateRangeValidator implements Validator {
	
	SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MM/yyyy");

	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		Date myDate = (Date)value;
		Date today = null;
		try {
			today = dateFormat.parse(dateFormat.format((new Date())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (myDate.before(today)) {
            FacesMessage message = new FacesMessage();
            message.setDetail("A Data não pode ser anterior a data atual");
            message.setSummary("A Data não pode ser anterior a data atual");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
		}
	}

}
