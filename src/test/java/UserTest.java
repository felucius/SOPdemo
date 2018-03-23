/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author M
 */
public class UserTest {
    
    public UserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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

    @Test
    public void testIfUserIsNotNull(){
        User user = new User("Maxime", "maxime@hotmail.com");
        
        assertNotNull(user);
    }
    
    @Test
    public void testErrorOnWrongEmail(){
        User user = new User("Maxime", null);
        
        assertNull(user.getEmail());
    }
    
    @Test
    public void testErrorOnWrongName(){
        User user = new User(null, "Maxime@hotmail.com");
        
        assertNull(user.getName());
    }
    
    @Test
    public void testErrorValuesNull(){
        User user = new User(null, null);
        
        assertNull(user.getName());
        assertNull(user.getEmail());
    }
    
    @Test
    public void twoUserValuesNull(){
        User user = new User(null, null);
        User user2 = new User(null, null);
        
        assertEquals(user.equals(user2), user2.equals(user));
    }
}
