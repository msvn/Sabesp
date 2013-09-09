package com.prime.infra.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

public class EnumConverter implements javax.faces.convert.Converter {

    /**
     * atributo SELECIONE.
     */
    private static final String SELECIONE = "-1";

    /**
     * @param context para processamento.
     * @param component para processamento.
     * @param value para processamento.
     * @return Object
     * @exception ConverterException lanca exception
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public final Object getAsObject(final FacesContext context,
                    final UIComponent component, final String value)
            throws ConverterException {
            if ((value != null 
                    && value.equals(SELECIONE)) 
                    || value.equals("")) {
                    return null;
            }
            
            Class enumType = 
                    component.getValueBinding("value").getType(context);
            return Enum.valueOf(enumType, value);
    }

    /**
     * @param context para processamento.
     * @param component para processamento.
     * @param object para processamento.
     * @return String
     * @exception ConverterException lanca exception
     */
    public final String getAsString(
            final FacesContext context, 
            final UIComponent component,
            final Object object
    ) throws ConverterException {
            if (object == null) {
                    return null;
            }
            if (object instanceof Enum) {
                    Enum type = (Enum) object;
                    return type.toString();
            } else {
                    return (String) object.toString();
            }
    }
   
}

