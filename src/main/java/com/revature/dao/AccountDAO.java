package com.revature.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.AddUpdateAccountDTO;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.utility.JDBCUtility;

public class AccountDAO {
	public Account addAccount(int id, AddUpdateAccountDTO dto) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "INSERT INTO accounts(balance, client_id) VALUES (?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setBigDecimal(1, dto.getBalance());
			pstmt.setInt(2, id);

			int numberOfRecordsCreated = pstmt.executeUpdate();

			if (numberOfRecordsCreated != 1) {
				throw new SQLException("Adding a new account was unsuccessful");
			}
			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();
			int automaticallyGeneratedId = rs.getInt(1);

			return new Account(automaticallyGeneratedId, dto.getBalance(), id);
		}
	}

	public List<Account> getAllAcountsForId(int id) throws SQLException {

		List<Account> listOfAccounts = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE client_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int clientId = rs.getInt("client_id");
				BigDecimal balance = rs.getBigDecimal("balance");
				int accountId = rs.getInt("account_id");

				Account a = new Account(accountId, balance, clientId);
				listOfAccounts.add(a);
			}
		}
		return listOfAccounts;
	}

	public List<Account> getAllAcountsForId(int clientId, BigDecimal amountGreatherThan, BigDecimal amountLesserThan)
			throws SQLException {
		List<Account> listOfAccounts = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE client_id = ? AND balance > ? AND balance < ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, clientId);
			pstmt.setBigDecimal(2, amountGreatherThan);
			pstmt.setBigDecimal(3, amountLesserThan);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BigDecimal balance = rs.getBigDecimal("balance");
				int accountId = rs.getInt("account_id");

				Account a = new Account(accountId, balance, clientId);
				listOfAccounts.add(a);
			}
		}
		return listOfAccounts;
	}

	public List<Account> getAllAcountsLessThan(int clientId, BigDecimal amountlesserThan) throws SQLException {
		List<Account> listOfAccounts = new ArrayList<>();
		
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE client_id = ? AND balance < ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, clientId);
			pstmt.setBigDecimal(2, amountlesserThan);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BigDecimal balance = rs.getBigDecimal("balance");
				int accountId = rs.getInt("account_id");

				Account a = new Account(accountId, balance, clientId);
				listOfAccounts.add(a);
			}
		}
		return listOfAccounts;
	}
	
	public List<Account> getAllAcountsGreaterThan(int clientId, BigDecimal amountGreaterThan) throws SQLException {
		List<Account> listOfAccounts = new ArrayList<>();
		
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE client_id = ? AND balance > ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, clientId);
			pstmt.setBigDecimal(2, amountGreaterThan);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				BigDecimal balance = rs.getBigDecimal("balance");
				int accountId = rs.getInt("account_id");

				Account a = new Account(accountId, balance, clientId);
				listOfAccounts.add(a);
			}
		}
		return listOfAccounts;
	}

	public Account getAccountById(int accountId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE account_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, accountId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new Account(rs.getInt("account_id"), rs.getBigDecimal("balance"), rs.getInt("client_id"));
			} else {
				return null;
			}

		}
	}

	public Account updateAccount(int accountId, int clientId, AddUpdateAccountDTO dto) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setBigDecimal(1, dto.getBalance());
			pstmt.setInt(2, accountId);

			int numberOfRecordsUpdated = pstmt.executeUpdate();

			if (numberOfRecordsUpdated != 1) {
				throw new SQLException("Unable to update account record with id of " + accountId);
			}
		}
		return new Account(accountId, dto.getBalance(), clientId);
	}

	public void deleteAccount(int accountId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "DELETE FROM accounts WHERE account_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, accountId);

			int numberOfRecordsDeleted = pstmt.executeUpdate();

			if (numberOfRecordsDeleted != 1) {
				throw new SQLException("Unable to delete record with id of " + accountId);
			}
		}
	}

}
