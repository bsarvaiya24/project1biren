package com.biren.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.biren.dao.ReimbursementDAO;
import com.biren.dao.UserDAO;
import com.biren.dto.ReimbursementDTO;
import com.biren.exception.BadParameterException;
import com.biren.exception.ReimbursementsNotFoundException;
import com.biren.model.Reimbursement;
import com.biren.model.ReimbursementStatus;
import com.biren.model.ReimbursementType;
import com.biren.model.User;
import com.biren.model.UserRoles;

public class SubmitterServiceTest {
	
	private static UserDAO mockUserDAO;
	private static ReimbursementDAO mockReimbursementDAO;
	private static Connection mockConnection;
	
	private static SubmitterService submitterService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mockUserDAO = mock(UserDAO.class);
		mockReimbursementDAO = mock(ReimbursementDAO.class);
		
		UserRoles mockRoleId = new UserRoles("submitter");
		mockRoleId.setErsUserRoleId(2);
		User mockUser = new User("username", "password", "firstName", "lastName", "email@email.com", mockRoleId);
		mockUser.setUserId(1);
		
		List<Reimbursement> mockReimbursementList = new ArrayList<>();
		Date mockDate = new Date();
		ReimbursementStatus mockStatus = new ReimbursementStatus("submitted");
		mockStatus.setReimbStatusId(1);
		ReimbursementType mockType = new ReimbursementType("travel_lodging");
		mockType.setReimbTypeId(1);
		Reimbursement mockReimbursement = new Reimbursement(1000,mockDate,mockUser,mockStatus,mockType);
		mockReimbursementList.add(mockReimbursement);
		
		when(mockReimbursementDAO.getReimbursementsByUser(eq(mockUser))).thenReturn(mockReimbursementList);
		
		User mockUserWithoutReimbursements = new User("abc", "def", "ghi", "jkl", "mno@pqr.stu", mockRoleId);
		mockUserWithoutReimbursements.setUserId(2);
		when(mockReimbursementDAO.getReimbursementsByUser(eq(mockUserWithoutReimbursements))).thenReturn(null);
		
		ReimbursementDTO mockReimbursementDTO = new ReimbursementDTO(20.0,"2021/05/04",1,"1");
		ReimbursementDTO mockReturnReimbursementDTO = new ReimbursementDTO(20.0,"2021/05/04",1,"1");
		mockReturnReimbursementDTO.setReimbId(2);
		when(mockReimbursementDAO.addReimbursement(eq(mockReimbursementDTO))).thenReturn(mockReturnReimbursementDTO);
		
		ReimbursementDTO mockReimbursementDTONoDate = new ReimbursementDTO(20.0,"",1,"1");
		when(mockReimbursementDAO.addReimbursement(eq(mockReimbursementDTONoDate)))
				.thenThrow(new BadParameterException("Invalid date input found. Message: "));
		
		ReimbursementDTO mockReimbursementDTOBlankSpaceDate = new ReimbursementDTO(20.0,"  ",1,"1");
		when(mockReimbursementDAO.addReimbursement(eq(mockReimbursementDTOBlankSpaceDate)))
				.thenThrow(new BadParameterException("Invalid date input found. Message: "));
		
		ReimbursementDTO mockReimbursementDTOInvalidDate = new ReimbursementDTO(20.0,"Not a date",1,"1");
		when(mockReimbursementDAO.addReimbursement(eq(mockReimbursementDTOInvalidDate)))
				.thenThrow(new BadParameterException("Invalid date input found. Message: "));
		
		ReimbursementDTO mockReimbursementDTONotFound = new ReimbursementDTO(20.0,"2020/05/04",1,"1");
		when(mockReimbursementDAO.addReimbursement(eq(mockReimbursementDTONotFound))).thenReturn(null);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		submitterService = new SubmitterService(mockUserDAO,mockReimbursementDAO);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_getReimbursementsByUser_valid() throws ReimbursementsNotFoundException {
		UserRoles mockRoleId = new UserRoles("submitter");
		mockRoleId.setErsUserRoleId(2);
		User mockUser = new User("username", "password", "firstName", "lastName", "email@email.com", mockRoleId);
		mockUser.setUserId(1);
		List<ReimbursementDTO> actual = submitterService.getReimbursementsByUser(mockUser);
		
		List<ReimbursementDTO> expected = new ArrayList<ReimbursementDTO>();
		List<Reimbursement> mockReimbursementList = new ArrayList<>();
		Date mockDate = new Date();
		ReimbursementStatus mockStatus = new ReimbursementStatus("submitted");
		mockStatus.setReimbStatusId(1);
		ReimbursementType mockType = new ReimbursementType("travel_lodging");
		mockType.setReimbTypeId(1);
		Reimbursement mockReimbursement = new Reimbursement(1000,mockDate,mockUser,mockStatus,mockType);
		expected.add(new ReimbursementDTO(mockReimbursement));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_getReimbursementsByUser_NoTransactionsInDatabase() {
		UserRoles mockRoleId = new UserRoles("submitter");
		mockRoleId.setErsUserRoleId(2);
		User mockUserWithoutReimbursements = new User("abc", "def", "ghi", "jkl", "mno@pqr.stu", mockRoleId);
		mockUserWithoutReimbursements.setUserId(2);
		try {
			List<ReimbursementDTO> actual = submitterService.getReimbursementsByUser(mockUserWithoutReimbursements);
			fail("ReimbursementsNotFoundException did not execute.");
		} catch (ReimbursementsNotFoundException e) {
			assertEquals(e.getMessage(),"No Reimbursements found for the given user");
		}
		
	}
	
	@Test
	public void test_addReimbursement_valid() throws BadParameterException, ReimbursementsNotFoundException{
		ReimbursementDTO mockReimbursementDTO = new ReimbursementDTO(20.0,"2021/05/04",1,"1");
		ReimbursementDTO actual = submitterService.addReimbursement(mockReimbursementDTO);
		
		ReimbursementDTO expected = new ReimbursementDTO(20.0,"2021/05/04",1,"1");
		expected.setReimbId(2);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_addReimbursement_noDate() throws ReimbursementsNotFoundException{
		ReimbursementDTO mockReimbursementDTO = new ReimbursementDTO(20.0,"",1,"1");
		ReimbursementDTO actual;
		try {
			actual = submitterService.addReimbursement(mockReimbursementDTO);
			fail("BadParameterException did not execute.");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(), "Invalid date input found. Message: ");
		}
		
	}
	
	@Test
	public void test_addReimbursement_BlankSpaceDate() throws ReimbursementsNotFoundException{
		ReimbursementDTO mockReimbursementDTO = new ReimbursementDTO(20.0,"  ",1,"1");
		ReimbursementDTO actual;
		try {
			actual = submitterService.addReimbursement(mockReimbursementDTO);
			fail("BadParameterException did not execute.");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(), "Invalid date input found. Message: ");
		}
		
	}
	
	@Test
	public void test_addReimbursement_InvalidAlphabetDate() throws ReimbursementsNotFoundException{
		ReimbursementDTO mockReimbursementDTO = new ReimbursementDTO(20.0,"Not a date",1,"1");
		ReimbursementDTO actual;
		try {
			actual = submitterService.addReimbursement(mockReimbursementDTO);
			fail("BadParameterException did not execute.");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(), "Invalid date input found. Message: ");
		}
		
	}
	
	@Test
	public void test_addReimbursement_ReimbursementsNotFound() throws BadParameterException {
		ReimbursementDTO mockReimbursementDTO = new ReimbursementDTO(20.0,"2020/05/04",1,"1");
		ReimbursementDTO actual;
		try {
			actual = submitterService.addReimbursement(mockReimbursementDTO);
			fail("ReimbursementsNotFoundException did not execute.");
		} catch (ReimbursementsNotFoundException e) {
			assertEquals(e.getMessage(), "Recently added reimbursement not found.");
		}
		
	}

}
