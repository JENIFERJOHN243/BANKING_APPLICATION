package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.CustomerDetails;

public class BankingApplicationDAO {
	static Connection connection;
	static PreparedStatement preparedStatement;
	static ResultSet resultSet;
	
	public static void getConnection() throws SQLException, ClassNotFoundException {
    	//Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/bankingapplication", "root",
				"root");
	}

	public static void addCustomerDetails(CustomerDetails details) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "INSERT INTO CUSTOMER_DETAILS (user_name,mob_no,initial_deposit,balance,acc_no,deposit)"
				+ "VALUES(?,?,?,?,?,?)";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, details.getCustomerName());
		preparedStatement.setString(2, details.getPhoneNumber());
		preparedStatement.setDouble(3, details.getInitialDepositAmount());
		preparedStatement.setDouble(4, details.getBalance());
		preparedStatement.setString(5, details.getAccountNumber());
		preparedStatement.setDouble(6, details.getDepositAmount());
		preparedStatement.executeUpdate();
	}

	public static int fetchTheCustomerId(CustomerDetails details) throws ClassNotFoundException, SQLException {
		getConnection();
		Integer userId = null;
		String sqlQuery = "SELECT customer_id FROM CUSTOMER_DETAILS WHERE user_name=? AND mob_no=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, details.getCustomerName());
		preparedStatement.setString(2, details.getPhoneNumber());
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			userId = resultSet.getInt(1);
		}
		return userId;
	}

	public static ArrayList<CustomerDetails> getCustomerDetails(CustomerDetails details) throws ClassNotFoundException, SQLException {
		getConnection();
		ArrayList<CustomerDetails> accountList = new ArrayList<CustomerDetails>();
		String sqlQuery ="SELECT user_name,acc_no,customer_id,balance FROM CUSTOMER_DETAILS WHERE customer_id=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1, details.getCustomerId());
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			CustomerDetails customerDetails = new CustomerDetails();
			customerDetails.setCustomerName(resultSet.getString(1));
			customerDetails.setAccountNumber(resultSet.getString(2));
			customerDetails.setCustomerId(resultSet.getInt(3));
			customerDetails.setBalance(resultSet.getDouble(4));
			accountList.add(customerDetails);
		}
		return accountList;
	}

	public static Integer fetchCustomerId(CustomerDetails details) throws ClassNotFoundException, SQLException {
		getConnection();
		Integer userId = null;
		String sqlQuery = "SELECT customer_id FROM CUSTOMER_DETAILS WHERE user_name=? AND customer_id=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setString(1, details.getCustomerName());
		preparedStatement.setInt(2, details.getCustomerId());;
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			userId = resultSet.getInt(1);
		}
		return userId;
	}

	public static ArrayList<CustomerDetails> getBalance(Integer userID) throws ClassNotFoundException, SQLException {
		getConnection();
		ArrayList<CustomerDetails> accountList = new ArrayList<CustomerDetails>();
		String sqlQuery ="SELECT user_name,acc_no,balance FROM CUSTOMER_DETAILS WHERE customer_id=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1, userID);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			CustomerDetails customerDetails = new CustomerDetails();
			customerDetails.setCustomerName(resultSet.getString(1));
			customerDetails.setAccountNumber(resultSet.getString(2));
			customerDetails.setBalance(resultSet.getDouble(3));
			accountList.add(customerDetails);
		}
		return accountList;
	}

	public static double fetchCustomerBalance(Integer userID) throws ClassNotFoundException, SQLException {
		getConnection();
		double netBalance = 0;
		String sqlQuery = "SELECT balance FROM CUSTOMER_DETAILS WHERE customer_id=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1, userID);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			netBalance = resultSet.getDouble(1);
		}
		return netBalance;
	}

	public static ArrayList<CustomerDetails> getDeposit(CustomerDetails details) throws ClassNotFoundException, SQLException {
		getConnection();
		ArrayList<CustomerDetails> accountList = new ArrayList<CustomerDetails>();
		String sqlQuery ="SELECT deposit,balance FROM CUSTOMER_DETAILS WHERE customer_id=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1, details.getCustomerId());
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			CustomerDetails customerDetails1 = new CustomerDetails();
			customerDetails1.setDepositAmount(resultSet.getDouble(1));
			customerDetails1.setBalance(resultSet.getDouble(2));
			accountList.add(customerDetails1);
		}
		return accountList;
	}

	public static ArrayList<CustomerDetails> getWithdraw(CustomerDetails details) throws ClassNotFoundException, SQLException {
		getConnection();
		ArrayList<CustomerDetails> accountList = new ArrayList<CustomerDetails>();
		String sqlQuery ="SELECT withdraw,balance FROM CUSTOMER_DETAILS WHERE customer_id=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1, details.getCustomerId());
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			CustomerDetails customerDetails1 = new CustomerDetails();
			customerDetails1.setWithdrawAmount(resultSet.getDouble(1));
			customerDetails1.setBalance(resultSet.getDouble(2));
			accountList.add(customerDetails1);
		}
		return accountList;
	}

	public static void addDepositDetails(CustomerDetails details) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "UPDATE CUSTOMER_DETAILS set deposit=?,balance =?  WHERE customer_id=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setDouble(1, details.getDepositAmount());
		preparedStatement.setDouble(2, details.getBalance());
		preparedStatement.setInt(3, details.getCustomerId());
		preparedStatement.executeUpdate();
	}

	public static void addWithdrawDetails(CustomerDetails details) throws ClassNotFoundException, SQLException {
		getConnection();
		String sqlQuery = "UPDATE  CUSTOMER_DETAILS set withdraw=?,balance =?  WHERE customer_id=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setDouble(1, details.getWithdrawAmount());
		preparedStatement.setDouble(2, details.getBalance());
		preparedStatement.setInt(3, details.getCustomerId());
		preparedStatement.executeUpdate();
	}

	public static ArrayList<CustomerDetails> getMiniStatement(Integer userID) throws ClassNotFoundException, SQLException {
		getConnection();
		ArrayList<CustomerDetails> accountList = new ArrayList<CustomerDetails>();
		String sqlQuery = "SELECT withdraw,deposit,balance FROM CUSTOMER_DETAILS WHERE customer_id=?";
		preparedStatement = connection.prepareStatement(sqlQuery);
		preparedStatement.setInt(1,userID );
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			CustomerDetails customerDetails1 = new CustomerDetails();
			customerDetails1.setWithdrawAmount(resultSet.getDouble(1));
			customerDetails1.setDepositAmount(resultSet.getDouble(2));
			customerDetails1.setBalance(resultSet.getDouble(3));
			accountList.add(customerDetails1);
		}
		return accountList;
	}

	public static ArrayList<CustomerDetails> getAdmin() throws ClassNotFoundException, SQLException {
		getConnection();
		ArrayList<CustomerDetails> accountList = new ArrayList<CustomerDetails>();
		String sqlQuery = "SELECT customer_id,user_name,acc_no,balance FROM CUSTOMER_DETAILS ";
		preparedStatement = connection.prepareStatement(sqlQuery);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			CustomerDetails customerDetails = new CustomerDetails();
			customerDetails.setCustomerId(resultSet.getInt(1));
			customerDetails.setCustomerName(resultSet.getString(2));
			customerDetails.setAccountNumber(resultSet.getString(3));
			customerDetails.setBalance(resultSet.getDouble(4));
			accountList.add(customerDetails);
		}
		return accountList;
	}
	
	
}
