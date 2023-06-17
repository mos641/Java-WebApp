/*
Authors:        abde
File:           TestSuite.java
Description:    Tests all our different test classes
 */
package tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite class
 * @author abde
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({tests.SpriteTest.class, tests.ColorConverterTest.class})
public class TestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
