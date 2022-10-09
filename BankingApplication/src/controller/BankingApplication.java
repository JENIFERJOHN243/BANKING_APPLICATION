package controller;

import java.sql.SQLException;

public class BankingApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		BankingController customer = new BankingController();
		customer.startProcess();
	}
}
