package controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import model.CustomerDetails;
import validation.Validation;

public class BankingController {
	Validation valid = new Validation();
	Scanner scanner = new Scanner(System.in);
	Random random=new Random();
	ArrayList<CustomerDetails> accountList= new ArrayList<>();
	int unqiueID=1;
	
	public void startProcess() {
		System.out.printf("%30s","Welcome To JJ Bank");
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

	private void createAccount() {
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
		details.setCustomerId(unqiueID);
		unqiueID++;
		accountList.add(details);
		System.out.println("Successfully Your Account is Created");
		System.out.println();
		System.out.println("Your Account Name : "+details.getCustomerName());
		System.out.println("Your Account Number : "+details.getAccountNumber());
		System.out.println("Your Customer ID :"+details.getCustomerId());
		System.out.println("Your Current Balance :"+details.getBalance());
		System.out.println();
		startProcess();
	}


	public void loginAccount() {
		boolean boolValue = true;
		System.out.println("Enter Your Account Name : ");
		String userName = scanner.next();
		System.out.println("Enter Your Customer ID :");
		int userID=scanner.nextInt();
		if(userName.equalsIgnoreCase("Admin") && userID==2412) {
			adminLogin();
		}
		else if(checkAccount(userName,userID)) {
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
				checkBalance(userID);
				break;
			}
			case 2:{
				deposit(userID);
				break;
			}
			case 3:{
				withdraw(userID);
				break;
			}
			case 4:{
				getMiniStatement(userID);
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
			System.out.println("No Account Found");
			}
	}

	private void adminLogin() {
		for(int index=0;index<accountList.size();index++) {
		System.out.println();
		System.out.println("Customer Name         : "+accountList.get(index).getCustomerName());
		System.out.println("Customer ID           : "+accountList.get(index).getCustomerId());
		System.out.println("Customer Phone Number : "+accountList.get(index).getPhoneNumber());
		System.out.println("Account Number        : "+accountList.get(index).getAccountNumber());
		System.out.println("Net Balance           : "+accountList.get(index).getBalance());
		System.out.println();
		}
		System.out.println();
		startProcess();
   }

	private void getMiniStatement(int userID) {
		for(int index=0;index<accountList.size();index++) {
			if(accountList.get(index).getCustomerId()==userID) {
			System.out.println();
			System.out.println("Last WithDrawal Amount :"+accountList.get(index).getWithdrawAmount());
			System.out.println("Last Deposit Amount :"+accountList.get(index).getDepositAmount());
			System.out.println("Your Net Balance :"+accountList.get(index).getBalance());
			System.out.println();
		}
			}
		System.out.println();
	}

	private void withdraw(int userID) {
		System.out.println("Enter An Amount To Withdraw ");
		double amount2 = scanner.nextInt();
		for(int index=0;index<accountList.size();index++) {
			if(accountList.get(index).getCustomerId()==userID) {
				if(accountList.get(index).getBalance()>amount2) {
					accountList.get(index).setWithdrawAmount(amount2);
					accountList.get(index).setBalance(accountList.get(index).getBalance() - amount2);
					System.out.println("Withdrawl Amount: "+amount2);
					System.out.println("Your Net Balance after Withdraw: "+accountList.get(index).getBalance());
					System.out.println();
				}else {
					System.out.println("Insufficient Balance");
					System.out.println();
				}
			}
		}
		System.out.println();
	}

	private void deposit(int userID) {
		System.out.println("Enter an amount to deposit ");
		double amount = scanner.nextInt();
		for(int index=0;index<accountList.size();index++) {
			if(accountList.get(index).getCustomerId()==userID) {
			if(amount > 0) {
				accountList.get(index).setDepositAmount(amount);
				accountList.get(index).setBalance(accountList.get(index).getBalance()+ amount);
				System.out.println("Deposited Amount: "+amount);
				System.out.println("Your Net Balance after deposit: "+accountList.get(index).getBalance());
				System.out.println();
			}else {
				System.out.println("Enter a Valid Amount");
				System.out.println();
			}
		}
			}
		System.out.println();
	}

	private void checkBalance(int userID) {
		for(int index=0;index<accountList.size();index++) {
			if(accountList.get(index).getCustomerId()==userID) {
			System.out.println();
			System.out.println("Welcome "+accountList.get(index).getCustomerName());
			System.out.println("Your Account Number : "+accountList.get(index).getAccountNumber());
			System.out.println("Your Net Balance = "+accountList.get(index).getBalance());
			}
		}
		System.out.println();
	}

	private boolean checkAccount(String userName, int userID) {
		for(int index=0;index<accountList.size();index++) {
			if((accountList.get(index).getCustomerName()).equals(userName)&&
					(accountList.get(index).getCustomerId())==userID) {
					return true;
			}
		}
		return false;
	}
	
	private boolean checkInitailDepoistAmount(double initialDepositAmount) {
		if(initialDepositAmount >=500) {
			return true;
		}
		return false;
	}
}