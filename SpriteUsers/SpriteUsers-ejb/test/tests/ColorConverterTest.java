/*
Authors:        abde
File:           ColorConverterTest.java
Description:    Tests methods from our Color Converter class
 */
package tests;

import java.awt.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utility.ColorConverter;

/**
 * Color converter testing class
 * @author abde
 */
public class ColorConverterTest {
    
    public ColorConverterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("\nColor Converter Tests");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
     
    /*
    *Testing the getAsObject method of the ColorConverter class. 
    *
    */
     @Test
     public void testColorObject() { 
     System.out.println("Testing ColorConverter class getAsObject method.");
     
     Color expectedValue = new Color(11,22,33);
     
     ColorConverter converter = new  ColorConverter();  
     
     Color actualValue = (Color) converter.getAsObject(null, null, "#0b1621");  
     
     assertEquals(expectedValue, actualValue);  
      
    }
     
     
     
    /*
    *Testing the getAsString method of the ColorConverter class. 
    *
    */
     @Test
     public void testColorString() {
     System.out.println("Testing ColorConverter class getAsString method.");     
        String expectedValue = "#0b1621"; 
     
        ColorConverter converter = new  ColorConverter();

        String actualValue = converter.getAsString(null, null, new Color(11,22,33));  

        assertEquals(expectedValue, actualValue);    
    } 
    
    
    
}
