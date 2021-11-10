package com.revature.controller;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;


import io.javalin.Javalin;

// When we have exceptions that propagate all the way to our Handlers in the controller layer, we can instead of using try {} catch () {} 
// utilize Javalin's exception mapping functionality
// This allows us to map exceptions that may make it's way to the controller layer, and then handle them within a centralized location.
// This prevents code redundancy and duplication
public class ExceptionMappingController {

	public void mapExceptions(Javalin app) {
		app.exception(UnrecognizedPropertyException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json("Invalid parameters");
		});
		
		
	}
	
}