package com.cg.bms.dao.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cg.bms.dao.BMSDaoImpl;
import com.cg.bms.exceptions.BMSException;
import com.cg.bms.model.Customer;

public class BMSDaoImplTest {

	BMSDaoImpl daoImpl = null;

	@Before
	public void setUp() throws Exception {
		daoImpl = new BMSDaoImpl();
	}

	@After
	public void tearDown() throws Exception {
		daoImpl = null;
	}

	@Test
	public void testCreateAccount() {

		Customer customer = new Customer("Rajsekgar", LocalDate.now(), "QWERT5678A", 23000);

		try {
			long accNo = daoImpl.createAccount(customer);
			assertNotNull(accNo);
		} catch (BMSException e) {

		}
	}

	@Test
	public void testGetCustomerDetails() {

		long accNo = 1003;

		try {
			Customer customer = daoImpl.getCustomerDetails(accNo);
			assertEquals("Shanthi", customer.getName());
		} catch (BMSException e) {

		}

	}

	@Test
	public void testCreateAccountNotEquals() {
		Customer customer = new Customer("Govind", LocalDate.now(), "QWERT1234A", 43000);

		try {
			long accNo = daoImpl.createAccount(customer);
			assertEquals(1000, accNo);
		} catch (BMSException e) {

		}
	}

	@Test(expected = BMSException.class)
	public void testGetCustomerDetailsNull() {

		long accNo = 1103;

		try {
			Customer customer = daoImpl.getCustomerDetails(accNo);
			assertNull(customer);
		} catch (BMSException e) {

		}
	}
}
