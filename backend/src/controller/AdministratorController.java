package controller;

import static spark.Spark.get;

import com.google.gson.Gson;

import domain.Administrator;
import service.AdministratorService;

public class AdministratorController {

	private static Gson gson = new Gson();

	public AdministratorController() {
		AdministratorService adminService = new AdministratorService();
		
		get("/administratori", (req, res) -> {
			return gson.toJson(adminService.preuzmiSve());
		});
		
		get("/administratori/:username", (req, res) -> {
			Administrator a = adminService.preuzmiPoUsername(req.params("username"));
			return gson.toJson(a);
		});
	}

}
