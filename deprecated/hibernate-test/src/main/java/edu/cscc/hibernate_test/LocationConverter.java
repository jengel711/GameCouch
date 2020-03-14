package edu.cscc.hibernate_test;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
 
@FacesConverter(forClass=Location.class)
public class LocationConverter implements Converter {
  
	@Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (modelValue == null) {
            return "";
        }

        if (modelValue instanceof Location) {
            return String.valueOf(((Location) modelValue).toString());
        } else {
            throw new ConverterException(new FacesMessage(modelValue + " is not a valid Location"));
        }
    }

	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }

        try {
        	var lookup = new Lookup();
        	return lookup.getLocationByID(Long.valueOf(submittedValue));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(submittedValue + " is not a valid Location ID"), e);
        }
    }

}