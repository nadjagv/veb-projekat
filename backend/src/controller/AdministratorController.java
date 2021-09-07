package controller;

import static spark.Spark.get;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.gson.Gson;

import domain.Administrator;
import service.AdministratorService;

public class AdministratorController {

	Jsonb jsonb = JsonbBuilder.newBuilder().build();

	public AdministratorController() {
		AdministratorService adminService = new AdministratorService();
		
		get("/administratori", (req, res) -> {
			return jsonb.toJson(adminService.preuzmiSve());
		});
		
		get("/administratori/:username", (req, res) -> {
			Administrator a = adminService.preuzmiPoUsername(req.params("username"));
			return jsonb.toJson(a);
		});
	}

}
