/*
Authors:        abde
File:           SpriteTest.java
Description:    Tests methods from our sprite entity class
 */
package tests;


import cst.entities.Sprite;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Sprite testing class
 * @author abde
 */
public class SpriteTest {
    
    public SpriteTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("\nSprite Entity Tests");
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

    /**
     * Test of getId method, of class Sprite.
     */
    @Test(expected = NullPointerException.class)
    public void testGetId() {
        System.out.println("Test getId type and non null");
        Sprite testSprite = new Sprite(500, 500);
        Long result = testSprite.getId();
        // check if it is a Long
        assertEquals(Long.class, result.getClass());
        // check that it is not null
        assertNotNull(result);
    }

    /**
     * Test of setId method, of class Sprite.
     */
    @Test
    public void testSetId() {
        System.out.println("Testing setId");
        Sprite testSprite = new Sprite(500, 500);
        Long id = 1010L;
        testSprite.setId(id);
        // test the set id is what we expect
        assertEquals(id, testSprite.getId());
    }

    /**
     * Test of move method, forward movement without bounce without any bounce, of class Sprite.
     */
    @Test
    public void testMoveForward() {
        System.out.println("Moving forward without bounce");
        int width = 400;
        int height = 400;
        Sprite testSprite = new Sprite(width, height);
        int x = width - (width / 2);
        int y = height - (height / 2);
        int speed = 5;
        // set sprite in the middle of panel with an x speed only of 5
        testSprite.setX(x);
        testSprite.setY(y);
        testSprite.setDx(speed);
        testSprite.setDy(0);
        // move sprite and assert new position
        testSprite.move();
        
        assertEquals((int)testSprite.getX(), (int)(x + speed));
        assertEquals((int)testSprite.getY(), (int)y);
    }
    
    /**
     * Test of move method, reverse movement without any bounce, of class Sprite.
     */
    @Test
    public void testMoveReverse() {
        System.out.println("Moving reverse without bounce");
        int width = 400;
        int height = 400;
        Sprite testSprite = new Sprite(width, height);
        int x = width - (width / 2);
        int y = height - (height / 2);
        int speed = -5;
        // set sprite in the middle of panel with an x speed only of -5
        testSprite.setX(x);
        testSprite.setY(y);
        testSprite.setDx(speed);
        testSprite.setDy(0);
        // move sprite and assert new position
        testSprite.move();
        
        assertEquals((int)testSprite.getX(), (int)(x + speed));
        assertEquals((int)testSprite.getY(), (int)y);
    }
    
    /**
     * Test of move method with a bounce, of class Sprite.
     */
    @Test
    public void testMoveBounce() {
        System.out.println("Moving forward without bounce");
        int width = 400;
        int height = 400;
        Sprite testSprite = new Sprite(width, height);
        int x = width - Sprite.SIZE + 1;
        int y = height - (height / 2);
        int speed = 5;
        // set sprite one away from panel edge
        testSprite.setX(x);
        testSprite.setY(y);
        testSprite.setDx(speed);
        testSprite.setDy(0);
        // move sprite and assert new position
        testSprite.move();
        
        assertEquals((int)(width - Sprite.SIZE - speed), (int)testSprite.getX());
        assertEquals((int)y, (int)testSprite.getY());
    }
    
       
}
