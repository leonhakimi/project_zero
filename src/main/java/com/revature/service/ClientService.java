package com.revature.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.AccountDAO;
import com.revature.dao.ClientDAO;
import com.revature.dto.AddUpdateAccountDTO;
import com.revature.dto.AddUpdateClientDTO;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidClientDataException;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.model.Account;
import com.revature.model.Client;

public class ClientService {
	private Logger logger = LoggerFactory.getLogger(ClientService.class);

	private ClientDAO clientDao;
	private AccountDAO accountDao;

	public ClientService() {
		this.clientDao = new ClientDAO();
		this.accountDao = new AccountDAO();
	}

	public List<Client> getAllClients() throws SQLException {
		logger.info("getAllClients() invoked");

		List<Client> listOfClients = this.clientDao.getAllClients();
		return listOfClients;
	}

	public List<Account> getAccountsForId(int clientId, String greaterThan, String lesserThan)
			throws SQLException, InvalidClientDataException, ClientNotFoundException {
		Client client = this.clientDao.getClientById(clientId);
		if (client == null) {
			throw new ClientNotFoundException("Client with id of " + clientId + " not found");
		}

		List<Account> listOfAccounts;
		BigDecimal amountlesserThan;
		BigDecimal amountGreaterThan;

		if (lesserThan != null && greaterThan != null) {
			try {
				amountlesserThan = new BigDecimal(lesserThan);
				amountGreaterThan = new BigDecimal(greaterThan);
				listOfAccounts = this.accountDao.getAllAcountsForId(clientId, amountGreaterThan, amountlesserThan);
				return listOfAccounts;
			} catch (NumberFormatException e) {
				throw new InvalidClientDataException("invalid parameters");
			}

		} else if (lesserThan != null && greaterThan == null) {
			try {
				amountlesserThan = new BigDecimal(lesserThan);
				listOfAccounts = this.accountDao.getAllAcountsLessThan(clientId, amountlesserThan);
				return listOfAccounts;
			} catch (NumberFormatException e) {
				throw new InvalidClientDataException("invalid parameters");
			}
		} else if (greaterThan != null && lesserThan == null) {
			try {
				amountGreaterThan = new BigDecimal(greaterThan);
				listOfAccounts = this.accountDao.getAllAcountsGreaterThan(clientId, amountGreaterThan);
				return listOfAccounts;
			} catch (NumberFormatException e) {
				throw new InvalidClientDataException("invalid parameters");
			}
		} else {
			listOfAccounts = this.accountDao.getAllAcountsForId(clientId);
			return listOfAccounts;
		}

	}

	public Client createClient(AddUpdateClientDTO client) throws SQLException, InvalidClientDataException {
		if (client.getClientFirstName() == null || client.getClientLastName() == null) {
			throw new InvalidClientDataException("Invalid data. Please make sure to include both first and last names");
		}
		Client clientCreated = this.clientDao.addClient(client);
		return clientCreated;
	}

	public Account createAccount(int clientId, AddUpdateAccountDTO account)
			throws InvalidClientDataException, SQLException {
		if (account.getBalance() == null) {
			throw new InvalidClientDataException("Invalid data.Please make sure to include balance");
		}
		Account accountCreated = this.accountDao.addAccount(clientId, account);
		return accountCreated;
	}

	public Client getClientById(int id) throws SQLException, ClientNotFoundException {
		Client client = this.clientDao.getClientById(id);

		if (client == null) {
			throw new ClientNotFoundException("Client with id of " + id + " not found");
		}
		return client;
	}
	
	public Account getAccount(int clientId, int accountId) throws SQLException, ClientNotFoundException, AccountNotFoundException {
		
		
		Account account = this.accountDao.getAccountById(accountId);
		if (account != null && account.getClientId() == clientId ) {
			return account;
		} else {
			throw new AccountNotFoundException("Could not find that account");
		}
	}

	public void deleteClientById(int id) throws SQLException, ClientNotFoundException {
		Client client = this.clientDao.getClientById(id);

		if (client == null) {
			throw new ClientNotFoundException("Client with id of " + id + " not found");
		}
		this.clientDao.deleteClientById(id);
		return;
	}

	public Client updateClientById(int id, AddUpdateClientDTO dto) throws SQLException, ClientNotFoundException {
		Client client = this.clientDao.getClientById(id);

		if (client == null) {
			throw new ClientNotFoundException("Client with id of " + id + " not found");
		}

		if (dto.getClientFirstName() == null) {
			dto.setClientFirstName(client.getClientFirstName());
		}
		if (dto.getClientLastName() == null) {
			dto.setClientLastName(client.getClientLastName());
		}

		Client updatedClient = this.clientDao.updateClient(id, dto);
		return updatedClient;
	}

	public Account updateAccount(int clientId, int accountId, AddUpdateAccountDTO dto) throws SQLException, AccountNotFoundException, ClientNotFoundException {
		Client client = this.clientDao.getClientById(clientId);
		if (client == null) {
			throw new ClientNotFoundException("Client with id of " + clientId + " not found");
		}
		
		Account account = this.accountDao.getAccountById(accountId);
		if (account != null && account.getClientId() == clientId ) {
			return this.accountDao.updateAccount(accountId, clientId, dto);
		} else {
			throw new AccountNotFoundException("Could not find that account");
		}
	}

	public void deleteAccount(int clientId, int accountId) throws SQLException, AccountNotFoundException, ClientNotFoundException {
		Client client = this.clientDao.getClientById(clientId);
		if (client == null) {
			throw new ClientNotFoundException("Client with id of " + clientId + " not found");
		}
		
		Account account = this.accountDao.getAccountById(accountId);
		if (account != null && account.getClientId() == clientId ) {
			this.accountDao.deleteAccount(accountId);
		} else {
			throw new AccountNotFoundException("Could not find that account");
		}
	}

	
}
