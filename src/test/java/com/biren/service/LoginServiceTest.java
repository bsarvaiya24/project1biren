package com.biren.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.biren.dao.UserDAO;
import com.biren.dto.LoginDTO;
import com.biren.exception.BadParameterException;
import com.biren.exception.LoginException;
import com.biren.model.User;
import com.biren.model.UserRoles;

public class LoginServiceTest {
	
	private static UserDAO mockUserDAO;
	private static Connection mockConnection;
	
	private static LoginService loginService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockUserDAO = mock(UserDAO.class);
//		mockConnection = mock(Connection.class);
		
//		when(mockUserDAO.addPirate(eq(1), eq(new PostPirateDTO("Edward", "Thatch"))))
//				.thenReturn(new Pirate(1, "Edward", "Thatch"));
		
//		User user = userDAO.getUserByUsernameAndPassword(loginDTO);
		String passwordHash = hashPassword("password");
		LoginDTO mockLoginDTO1 = new LoginDTO("username",passwordHash);
		
		UserRoles mockRoleId = new UserRoles("approver");
		mockRoleId.setErsUserRoleId(1);
		User mockUser = new User("username", "password", "firstName", "lastName", "email@email.com", mockRoleId);
		mockUser.setUserId(1);
		
		when(mockUserDAO.getUserByUsernameAndPassword(eq(mockLoginDTO1))).thenReturn(mockUser);
		
		passwordHash = hashPassword("notPassword");
		LoginDTO mockLoginDTO2 = new LoginDTO("notUsername",passwordHash);
		when(mockUserDAO.getUserByUsernameAndPassword(eq(mockLoginDTO2))).thenReturn(null);
		
	}

//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//		
//	}

	@Before
	public void setUp() throws Exception {
		loginService = new LoginService(mockUserDAO);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_login_ValidUserExists() throws BadParameterException, LoginException {
//		String passwordHash = hashPassword("password");
		LoginDTO inputLoginDTO = new LoginDTO("username","password");
		User actual = loginService.login(inputLoginDTO);
		
		UserRoles mockRoleId = new UserRoles("approver");
		mockRoleId.setErsUserRoleId(1);
		User expected = new User("username", "password", "firstName", "lastName", "email@email.com", mockRoleId);
		expected.setUserId(1);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void test_login_ValidUserDoesNotExist() throws BadParameterException {
		LoginDTO inputLoginDTO = new LoginDTO("notUsername","notPassword");
		try {
			User actual = loginService.login(inputLoginDTO);
			fail("LoginException did not happen");
		} catch (LoginException e) {
			assertEquals(e.getMessage(),"User was not able to login with given username and password.");
		}
	}
	
	@Test
	public void test_login_NoUsernameValidPassword() throws LoginException {
		LoginDTO inputLoginDTO = new LoginDTO("","password");
		try {
			User actual = loginService.login(inputLoginDTO);
			fail("BadParameterException did not happen");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(),"Cannot have blank username and/or password");
		}
	}
	
	@Test
	public void test_login_BlankSpaceUsernameValidPassword() throws LoginException {
		LoginDTO inputLoginDTO = new LoginDTO("  ","password");
		try {
			User actual = loginService.login(inputLoginDTO);
			fail("BadParameterException did not happen");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(),"Cannot have blank username and/or password");
		}
	}
	
	@Test
	public void test_login_ValidUsernameNoPassword() throws LoginException {
		LoginDTO inputLoginDTO = new LoginDTO("username","");
		try {
			User actual = loginService.login(inputLoginDTO);
			fail("BadParameterException did not happen");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(),"Cannot have blank username and/or password");
		}
	}
	
	@Test
	public void test_login_ValidUsernameBlankSpacePassword() throws LoginException {
		LoginDTO inputLoginDTO = new LoginDTO("username","  ");
		try {
			User actual = loginService.login(inputLoginDTO);
			fail("BadParameterException did not happen");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(),"Cannot have blank username and/or password");
		}
	}
	
	@Test
	public void test_hashPassword_valid() throws LoginException {
		String mockPassword = "password";
		String actual = loginService.hashPassword(mockPassword);
		String expected = hashPassword(mockPassword);
		assertEquals(expected, actual);
	}
	
	
	// Password Hashing
	public static String hashPassword(String password) {
        
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
        
    }

}
