/*
Authors:        abde
File:           Colour.java
Description:    Converts between rgb hex colour strings and colour objects
*/
package utility;

import java.awt.Color;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Converts color objects to strings and vice versa
 * @author abde
 */
@FacesConverter("colorConverter")
public class ColorConverter implements Converter {

    /**
     * Receive a rgb hexcode string and return a colour object
     * @param context jsf context
     * @param component the ui component
     * @param value string value being received
     * @return a color object created from the rgb hex code
     */    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value){
        // check it's in the right format
        if (value == null || !value.matches("^#[0-9a-fA-F]{6}$")) {
            FacesMessage errMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Color must be in a rgb hex format #RRGGBB", "Color must be in a rgb hex format #RRGGBB");
            throw new ConverterException(errMessage);
        }
        
        // convert received string to Color object
        return new Color (
            Integer.valueOf(value.substring(1,3), 16),
            Integer.valueOf(value.substring(3,5), 16),
            Integer.valueOf(value.substring(5,7), 16));
    }
    
    /**
     * Receives a color object and return a rgb hexcode string
     * @param context jsf context
     * @param component the ui component
     * @param value color object being received
     * @return a string value of the rgb hex code
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value){
        Color color = (Color) value;
        
        // convert received color object to a rgb hex string
        String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        
        return hex;
    }
    
}