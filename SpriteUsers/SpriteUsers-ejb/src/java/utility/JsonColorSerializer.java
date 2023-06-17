/*
Authors:        abde
File:           JsonColorSerializer.java
Description:    Converts color objects to JSON format
 */

package utility;

import java.awt.Color;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

/**
 * Provided class for JSON conversions
 * @author abde
 */
public class JsonColorSerializer implements JsonbSerializer<Color> {

    @Override
    public void serialize(Color c, JsonGenerator jg, SerializationContext ctx) {
        jg.writeStartObject();
        jg.write("red", c.getRed());
        jg.write("green", c.getGreen());
        jg.write("blue", c.getBlue());
        jg.writeEnd();
    }
}
