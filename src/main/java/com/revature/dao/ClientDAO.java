package com.revature.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revature.dto.AddUpdateClientDTO;
import com.revature.model.Client;
import com.revature.utility.JDBCUtility;

public class ClientDAO {
	public Client addClient(AddUpdateClientDTO client) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "INSERT INTO clients(client_first_name, client_last_name) VALUES (?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, client.getClientFirstName());
			pstmt.setString(2, client.getClientLastName());

			int numberOfRecordsCreated = pstmt.executeUpdate();

			if (numberOfRecordsCreated != 1) {
				throw new SQLException("Adding a new client was unsuccessful");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();
			int automaticallyGeneratedId = rs.getInt(1);

			return new Client(automaticallyGeneratedId, client.getClientFirstName(), client.getClientLastName());

		}
	}

	public List<Client> getAllClients() throws SQLException {
		List<Client> listOfAllClients = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM clients";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("client_id");
				String firstName = rs.getString("client_first_name");
				String lastName = rs.getString("client_last_name");

				Client c = new Client(id, firstName, lastName);
				listOfAllClients.add(c);
			}
		}

		return listOfAllClients;
	}

	public Client getClientById(int id) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM clients WHERE client_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new Client(rs.getInt("client_id"), rs.getString("client_first_name"),
						rs.getString("client_last_name"));
			} else {
				return null;
			}

		}
	}

	public Client updateClient(int id, AddUpdateClientDTO client) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE clients SET client_first_name = ?," + "client_last_name = ?"
					+ " WHERE client_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, client.getClientFirstName());
			pstmt.setString(2, client.getClientLastName());
			pstmt.setInt(3, id);
			
			int numberOfRecordsUpdated = pstmt.executeUpdate();
			
			if (numberOfRecordsUpdated != 1) {
				throw new SQLException("Unable to update client record with id of " + id);
			}
		}

		return new Client(id, client.getClientFirstName(), client.getClientLastName());
	}
	
	public void deleteClientById(int id) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "DELETE FROM clients WHERE client_id = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			
			int numberOfRecordsDeleted = pstmt.executeUpdate();
			
			if (numberOfRecordsDeleted != 1) {
				throw new SQLException("Unable to delete record with id of " + id);
			}
		}
	}
}
