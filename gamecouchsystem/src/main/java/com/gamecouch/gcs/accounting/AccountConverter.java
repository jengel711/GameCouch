package com.gamecouch.gcs.accounting;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.gamecouch.gcs.gamecouchsystem.Lookup;
 
/**
 * @author Alan Bolte
 *
 */
@FacesConverter(forClass=Account.class)
public class AccountConverter implements Converter {
  
	@Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (modelValue == null) {
            return "";
        }

        if (modelValue instanceof Account) {
            return String.valueOf(((Account) modelValue).toString());
        } else {
            throw new ConverterException(new FacesMessage(modelValue + " is not a valid Account"));
        }
    }

	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }

        try {
        	var lookup = new Lookup();
        	return lookup.getRowObjectByID(Account.class, Long.valueOf(submittedValue));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(submittedValue + " is not a valid Account ID"), e);
        }
    }

}