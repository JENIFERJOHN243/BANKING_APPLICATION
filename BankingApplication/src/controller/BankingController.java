package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import dao.BankingApplicationDAO;
import model.CustomerDetails;
import validation.Validation;

public class BankingController {
	Validation valid = new Validation();
	Scanner scanner = new Scanner(System.in);
	Random random=new Random();
	
	public void startProcess() throws ClassNotFoundException, SQLException {
		System.out.printf("%30s","Welcome To BB Bank");
		System.out.println();
		System.out.println("1 : Create Account \n2 : Login Account \n3 : Exit");
		System.out.println();
		System.out.println("Press 1 or 2 or 3 respectively");
		int choice=scanner.nextInt();
		switch(choice) {
		case 1:{
			createAccount();
			break;
		}
		case 2:{
			loginAccount();
			break;
		}
		case 3:{
			System.out.println("Thank You For Using Our Service");
			System.exit(0);
			break;
		}
		default :{
			System.out.println("Invalid Option..!! Please Enter The Correct Option");
			System.out.println();
			startProcess();
		}
		}
	}

	private void createAccount() throws ClassNotFoundException, SQLException {
		CustomerDetails details = new CustomerDetails();
		boolean boolValue=false;
		boolean boolValue1=false;
		boolean boolValue2=false;
		while(!boolValue) {
		System.out.println("Enter Your Name");
		String customerName = scanner.next();
		if(valid.nameValidation(customerName)) {
			details.setCustomerName(customerName);
			System.out.println();
			boolValue = true;
		}else {
		System.out.println("Invalid Name..!! Please Enter an Valid Name");
		}
		}
		while(!boolValue1) {
		System.out.println("Enter Your PhoneNumber");
		String phoneNumber = scanner.next();
		if(valid.mobileNumberValidation(phoneNumber)) {
			details.setPhoneNumber(phoneNumber);
			System.out.println();
			boolValue1=true;
		}else {
		System.out.println("Invalid PhoneNumber..!! Please Enter an Valid PhoneNumber");
		}
		}
		while(!boolValue2) {
			System.out.println("Enter The Amount to be Deposit :");
			System.out.println("MINIMUM AMOUNT SHOULD BE 500/-");
			double initialDepositAmount =scanner.nextDouble();
			if(checkInitailDepoistAmount(initialDepositAmount)) {
				details.setInitialDepositAmount(initialDepositAmount);
				details.setBalance(initialDepositAmount);
				details.setDepositAmount(initialDepositAmount);
				boolValue2=true;
			}else {
				System.out.println("Initial Deposit Amount Should be Greater Than 500/- !!");
			}
			System.out.println();
		}
		String accountNumber="BB";
		for(int index=0;index<14;index++) {
			int num=random.nextInt(9);
			accountNumber+=Integer.toString(num);
		}
		details.setAccountNumber(accountNumber);
		BankingApplicationDAO.addCustomerDetails(details);
		details.setCustomerId(BankingApplicationDAO.fetchTheCustomerId(details));
		ArrayList<CustomerDetails> accountList = BankingApplicationDAO.getCustomerDetails(details);
		System.out.println("Successfully Your Account is Created");
		System.out.println();
		for(int index=0;index<accountList.size();index++) {
		System.out.println("Your Account Name : "+accountList.get(index).getCustomerName());
		System.out.println("Your Account Number : "+accountList.get(index).getAccountNumber());
		System.out.println("Your Customer ID :"+accountList.get(index).getCustomerId());
		System.out.println("Your Current Balance :"+accountList.get(index).getBalance());
		System.out.println();
		startProcess();
	}
}

	public void loginAccount() throws ClassNotFoundException, SQLException {
		CustomerDetails details = new CustomerDetails();
		boolean boolValue = true;
		System.out.println("Enter Your Account Name : ");
		String userName = scanner.next();
		details.setCustomerName(userName);
		System.out.println("Enter Your Customer ID :");
		Integer customer_id = scanner.nextInt();
		details.setCustomerId(customer_id);
		Integer userId = BankingApplicationDAO.fetchCustomerId(details);
		if(userName.equalsIgnoreCase("Admin") && customer_id==2412) {
			adminLogin();
		}
		else if(userId!=null) {
			System.out.println();
			while(boolValue) {
			System.out.println("1 : Check Your Balance");
			System.out.println("2 : Deposit");
			System.out.println("3 : Withdraw");
			System.out.println("4 : Mini Statement");
			System.out.println("5 : Back To Main Menu");
			System.out.println();
				System.out.println("Enter Your Choice");
				int option=scanner.nextInt();
				
				switch (option){
				case 1:{
					checkBalance(details);
					break;
				}
				case 2:{
					deposit(details);
					break;
				}
				case 3:{
					withdraw(details);
					break;
				}
				case 4:{
					getMiniStatement(details);
					break;
				}
				case 5:{
					boolValue=false;
					startProcess();
					break;
				}
				default:{
					System.out.println("Invalid Option!!Please Enter Correct Option");
					System.out.println();
					}
				}
			}
		}else {
			System.out.println("No Account Found..!! Try Again");
			loginAccount();
			
		}
	}

	private void adminLogin() throws ClassNotFoundException, SQLException {
		ArrayList<CustomerDetails> accountList = BankingApplicationDAO.getAdmin();
		System.out.println("+---------------------------------------------------------------------------------+");
		System.out.printf("| %15s | %15s | %20s | %20s |","Customer ID","Customer Name","Account No","Net Balance");
		System.out.println();
		System.out.println("+---------------------------------------------------------------------------------+");
		for(int index=0;index<accountList.size();index++) {
			System.out.printf("| %15s | %15s | %20s | %20s |",accountList.get(index).getCustomerId(),accountList.get(index).getCustomerName(),
					accountList.get(index).getAccountNumber(),accountList.get(index).getBalance());
			System.out.println();
			System.out.println("+---------------------------------------------------------------------------------+");
		}
		System.out.println();
		startProcess();
   }

	private void getMiniStatement(CustomerDetails details) throws ClassNotFoundException, SQLException {
		ArrayList<CustomerDetails> accountList = BankingApplicationDAO.getMiniStatement(details.getCustomerId());
		for(int index=0;index<accountList.size();index++) {

			System.out.println();
			System.out.println("Last WithDrawal Amount :"+accountList.get(index).getWithdrawAmount());
			System.out.println("Last Deposit Amount :"+accountList.get(index).getDepositAmount());
			System.out.println("Your Net Balance :"+accountList.get(index).getBalance());
			System.out.println();
		}
		System.out.println();
	}

	private void withdraw(CustomerDetails details) throws ClassNotFoundException, SQLException {
		System.out.println("Enter An Amount To Withdraw ");
		double withdrawAmount = scanner.nextInt();
		double netBalance = BankingApplicationDAO.fetchCustomerBalance(details.getCustomerId());
		if(netBalance > withdrawAmount) {
			details.setWithdrawAmount(withdrawAmount);
			details.setBalance(netBalance-withdrawAmount);
			BankingApplicationDAO.addWithdrawDetails(details);
			ArrayList<CustomerDetails> accountList = BankingApplicationDAO.getWithdraw(details);
			for(int index=0;index<accountList.size();index++) {
				System.out.println("Withdrawal Amount: "+accountList.get(index).getWithdrawAmount());
				System.out.println("Your Net Balance After Withdraw: "+accountList.get(index).getBalance());
				System.out.println();
				}
		}
		else {
			System.out.println("Insufficient Balance");
			System.out.println();
		}
		System.out.println();
	}
		

	private void deposit(CustomerDetails details) throws ClassNotFoundException, SQLException {
		System.out.println("Enter an amount to deposit ");
		double depositAmount = scanner.nextInt();
			if(depositAmount > 0) {
				double netBalance = BankingApplicationDAO.fetchCustomerBalance(details.getCustomerId());
				details.setDepositAmount(depositAmount);
				details.setBalance(netBalance+depositAmount);
				BankingApplicationDAO.addDepositDetails(details);
			  	ArrayList<CustomerDetails> accountList = BankingApplicationDAO.getDeposit(details);
				for(int index=0;index<accountList.size();index++) {
				System.out.println("Deposited Amount: "+accountList.get(index).getDepositAmount());
				System.out.println("Your Net Balance after deposit: "+accountList.get(index).getBalance());
				System.out.println();
				}
			}else {
				System.out.println("Enter an Valid Amount");
				System.out.println();
			}
			System.out.println();
		}
	

	private void checkBalance(CustomerDetails details) throws ClassNotFoundException, SQLException {
		ArrayList<CustomerDetails> accountList = BankingApplicationDAO.getBalance(details.getCustomerId());
		for(int index=0;index<accountList.size();index++) {
			System.out.println();
			System.out.println("Welcome "+accountList.get(index).getCustomerName());
			System.out.println("Your Account Number : "+accountList.get(index).getAccountNumber());
			System.out.println("Your Net Balance = "+accountList.get(index).getBalance());
		}
		System.out.println();
	}
	
	private boolean checkInitailDepoistAmount(double initialDepositAmount) {
		if(initialDepositAmount >=500) {
			return true;
		}
		return false;
	}
}
