package com.cg.bms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.log4j.Logger;

import com.cg.bms.exceptions.BMSException;
import com.cg.bms.model.Customer;
import com.cg.bms.utility.JdbcUtility;

public class BMSDaoImpl implements BMSDao {

	static Logger logger = Logger.getLogger(BMSDaoImpl.class);

	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	/**
	 * method name : createAccount argument : CVustomer object return type : long
	 * value description : this Author : capgemini creation date : 26-Jult-2019
	 */

	@Override
	public long createAccount(Customer customer) throws BMSException {

		long id = 0;
		connection = JdbcUtility.getConnection();
		logger.info("connection established");
		try {

			statement = connection.prepareStatement(QueryMapper.INSERT_CUSTOMER_QUERY);
			logger.debug("statement cretaed");
			statement.setString(1, customer.getName());

			Date date = Date.valueOf(customer.getBirthDate());
			statement.setDate(2, date);

			statement.setString(3, customer.getPanNo());
			statement.setDouble(4, customer.getBalance());

			statement.executeUpdate();
			logger.debug("row inserted");

			statement = connection.prepareStatement(QueryMapper.GET_GEN_ID);
			resultSet = statement.executeQuery();
			logger.info("resultset created");

			while (resultSet.next()) {
				logger.debug("in while loop");
				id = resultSet.getLong(1);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new BMSException("problem while creating statement");
		}
		logger.info("id generated with value: " + id);
		return id;
	}

	/**
	 * method name : getCustomerDetails argument : account number of the customer of
	 * long type return type : after the succesful execution this method wiull
	 * return customer opbject description : this Author : capgemini creation date :
	 * 26-Jult-2019
	 */
	@Override
	public Customer getCustomerDetails(long accNo) throws BMSException {

		connection = JdbcUtility.getConnection();
		Customer customer = null;
		try {
			statement = connection.prepareStatement(QueryMapper.GET_ACCOUNT_DETAILS);
			statement.setLong(1, accNo);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				long accountNo = resultSet.getLong(1);
				String name = resultSet.getString(2);
				Date date = resultSet.getDate(3);
				LocalDate localDate = date.toLocalDate();
				String pan = resultSet.getString(4);
				double balance = resultSet.getDouble(5);

				customer = new Customer(accountNo, name, localDate, pan, balance);
			}

		} catch (SQLException e) {
			throw new BMSException("problem while creating statement");
		}

		if (customer == null) {
			throw new BMSException("No customer presen twith the given account number");
		}

		return customer;
	}
}
