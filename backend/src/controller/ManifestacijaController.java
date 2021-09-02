package controller;

import static spark.Spark.get;

import java.util.ArrayList;

import com.google.gson.Gson;

import domain.Manifestacija;
import service.ManifestacijaService;

public class ManifestacijaController {
	private static Gson gson = new Gson();

	public ManifestacijaController() {
		ManifestacijaService manService = new ManifestacijaService();
		
		get("/manifestacije", (req, res) -> {
			return gson.toJson(manService.preuzmiSve());
		});
		
		get("/manifestacije/:id", (req, res) -> {
			Manifestacija m = manService.preuzmiPoId(req.params("id"));
//			if (m != null) {
//				res.status(200);
//			}else {
//				res.status(404);
//			}
			return gson.toJson(m);
		});
	}

}

//public UserController(final UserService userService) {
//	get("/users", new Route() {
//		@Override
//		public Object handle(Request request, Response response) {
//		// process request
//		return userService.getAllUsers();
//		}
//	});
//	
//	// more routes
//	}
//}
