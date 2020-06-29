package test.com.yang.auth; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** 
* AuthApplicationTests Tester. 
* 
* @author <Authors name> 
* @since <pre>6ÔÂ 29, 2020</pre> 
* @version 1.0 
*/ 
public class AuthApplicationTestsTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: contextLoads() 
* 
*/ 
@Test
public void testContextLoads() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: test() 
* 
*/ 
@Test
public void testTest() throws Exception {
    String pwd = "123456";
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String passwd = passwordEncoder.encode(pwd);
    System.out.println(passwd);
} 


} 
