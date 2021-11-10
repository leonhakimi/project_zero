package com.revature.controller;

import java.util.List;
import java.lang.NumberFormatException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.dto.AddUpdateAccountDTO;
import com.revature.dto.AddUpdateClientDTO;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidClientDataException;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientController {
	private ClientService clientService;

	public ClientController() {
		this.clientService = new ClientService();
	}

	private Handler getAllClients = (ctx) -> {

		try {
			List<Client> listOfAllClients = this.clientService.getAllClients();
			ctx.json(listOfAllClients);
		} catch (Exception e) {
			ctx.status(404);
			ctx.json(e);
		}

	};

	private Handler createClient = (ctx) -> {

		try {
			AddUpdateClientDTO dto = ctx.bodyAsClass(AddUpdateClientDTO.class);
			Client createdClient = this.clientService.createClient(dto);
			ctx.json(createdClient);
		} catch (InvalidClientDataException e) {
			ctx.status(400);
			ctx.json(e);
		} catch (Exception e) {
			ctx.status(400);
			ctx.json(
					"Could not create client. Please ensure data is correct and has properties clientFirstName and clientLastName");
		}

	};

	public Handler getClient = (ctx) -> {
		try {
			String clientId = ctx.pathParam("client_id");
			int id = Integer.parseInt(clientId);
			Client client = this.clientService.getClientById(id);
			ctx.json(client);
		} catch (ClientNotFoundException e) {
			ctx.status(404);
			ctx.json(e.getMessage());
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.json("Id must be a number");
		}

	};

	public Handler deleteClient = (ctx) -> {
		try {
			String clientId = ctx.pathParam("client_id");
			int id = Integer.parseInt(clientId);
			this.clientService.deleteClientById(id);
			ctx.status(200);
			ctx.json("Successfully deleted client with id " + id);
		} catch (ClientNotFoundException e) {
			ctx.status(404);
			ctx.json(e.getMessage());
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.json("Id must be a number");
		}
	};

	public Handler editClient = (ctx) -> {
		try {
			String clientId = ctx.pathParam("client_id");
			int id = Integer.parseInt(clientId);

			AddUpdateClientDTO dto = ctx.bodyAsClass(AddUpdateClientDTO.class);
			Client editedClient = this.clientService.updateClientById(id, dto);
			ctx.status(200);
			ctx.json(editedClient);
		} catch (ClientNotFoundException e) {
			ctx.status(404);
			ctx.json(e.getMessage());
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.json("Id must be a number");
		} catch (Exception e) {
			ctx.status(400);
			ctx.json(
					"Could not update client. Please ensure data is correct and has property clientFirstName and/or clientLastName");
		}
	};

	public Handler createAccount = (ctx) -> {
		try {
			String clientId = ctx.pathParam("client_id");
			int id = Integer.parseInt(clientId);

			AddUpdateAccountDTO dto = ctx.bodyAsClass(AddUpdateAccountDTO.class);
			Account createdAccount = this.clientService.createAccount(id, dto);
			ctx.json(createdAccount);
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.json("Id must be a number");
		} catch (InvalidClientDataException e) {
			ctx.status(400);
			ctx.json(e.getMessage());
		} catch (Exception e) {
			ctx.status(400);
			ctx.json("Invalid data format. Balance must be Big Decimal type");
		}
	};

	public Handler getAccountsForId = (ctx) -> {
		try {
			String clientId = ctx.pathParam("client_id");
			int id = Integer.parseInt(clientId);

			String greaterThanParam = ctx.queryParam("amountGreaterThan");
			String lessThanParam = ctx.queryParam("amountLessThan");

			List<Account> listOfAccounts = this.clientService.getAccountsForId(id, greaterThanParam, lessThanParam);
			ctx.json(listOfAccounts);
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.json("Invalid client id");
		} catch (ClientNotFoundException e) {
			ctx.status(404);
			ctx.json(e.getMessage());
		} catch (InvalidClientDataException e) {
			ctx.status(400);
			ctx.json(e.getMessage());
		}
//		catch(Exception e) {
//			ctx.status(404);
//			ctx.json(e);
//		}
	};

	public Handler getAccount = (ctx) -> {
		try {
			String clientParam = ctx.pathParam("client_id");
			int clientId = Integer.parseInt(clientParam);

			String accountParam = ctx.pathParam("account_id");
			int accountId = Integer.parseInt(accountParam);

			// Client client = this.clientService.getClientById(clientId);

			Account account = this.clientService.getAccount(clientId, accountId);
			ctx.json(account);
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.json("Invalid id format");
		} catch (ClientNotFoundException e) {
			ctx.status(404);
			ctx.json(e.getMessage());
		} catch (AccountNotFoundException e) {
			ctx.status(404);
			ctx.json(e.getMessage());
		}
	};

	public Handler editAccount = (ctx) -> {
		try {
			String clientParam = ctx.pathParam("client_id");
			int clientId = Integer.parseInt(clientParam);

			String accountParam = ctx.pathParam("account_id");
			int accountId = Integer.parseInt(accountParam);

			AddUpdateAccountDTO dto = ctx.bodyAsClass(AddUpdateAccountDTO.class);
			Account account = this.clientService.updateAccount(clientId, accountId, dto);
			ctx.json(account);
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.json("Invalid id format");
		} catch (ClientNotFoundException e) {
			ctx.status(404);
			ctx.json(e.getMessage());
		} catch (AccountNotFoundException e) {
			ctx.status(404);
			ctx.json(e.getMessage());
		} catch (Exception e) {
			ctx.status(400);
			ctx.json("Invalid data format. balance must be of type BigDecimal");
		}
	};

	public Handler deleteAccount = (ctx) -> {
		try {
			String clientParam = ctx.pathParam("client_id");
			int clientId = Integer.parseInt(clientParam);

			String accountParam = ctx.pathParam("account_id");
			int accountId = Integer.parseInt(accountParam);

			this.clientService.deleteAccount(clientId, accountId);
			ctx.json("Successfully deleted account with id " + accountId);
		} catch (NumberFormatException e) {
			ctx.status(400);
			ctx.json("Invalid id format");
		} catch (ClientNotFoundException e) {
			ctx.status(404);
			ctx.json(e.getMessage());
		} catch (AccountNotFoundException e) {
			ctx.status(404);
			ctx.json(e.getMessage());
		}
	};

	public void registerEndpoints(Javalin app) {
		app.get("/clients", getAllClients);
		app.post("/clients", createClient);
		app.get("/clients/{client_id}", getClient);
		app.delete("/clients/{client_id}", deleteClient);
		app.put("/clients/{client_id}", editClient);
		app.post("/clients/{client_id}/accounts", createAccount);
		app.get("/clients/{client_id}/accounts", getAccountsForId);
		app.get("/clients/{client_id}/accounts/{account_id}", getAccount);
		app.put("/clients/{client_id}/accounts/{account_id}", editAccount);
		app.delete("/clients/{client_id}/accounts/{account_id}", deleteAccount);

	}
}
