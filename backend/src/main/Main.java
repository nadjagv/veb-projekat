package main;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		port(8080);
		
		get("/main/test", (req, res) -> {
			return "Doktor";
		});
	}

}
