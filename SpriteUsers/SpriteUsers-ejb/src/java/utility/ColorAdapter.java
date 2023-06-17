/*
Authors:        abde
File:           ColorAdapter.java
Description:    Converts colors between color objects and xml
 */

package utility;

import java.awt.Color;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Provided class for XML conversions
 * @author abde
 */
public class ColorAdapter extends XmlAdapter<ColorAdapter.ColorValueType, Color> {

    @Override
    public Color unmarshal(ColorValueType v) throws Exception {
        return new Color(v.red, v.green, v.blue);
    }

    @Override
    public ColorValueType marshal(Color v) throws Exception {
        return new ColorValueType(v.getRed(), v.getRed(), v.getBlue());
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ColorValueType {

        private int red;
        private int green;
        private int blue;

        public ColorValueType() {
        }

        public ColorValueType(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }
}
