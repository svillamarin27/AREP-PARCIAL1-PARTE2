package edu.escuelaing.arep.parcial;

import java.io.*;
import java.net.*;

import spark.*;
import static spark.Spark.*;
import org.json.*;

public class App 
{
	 public static void main( String[] args ) {
		 port(getPort());
	     get("/cliente", (req, res) -> inputDataPage(req, res));
	     get("/results", (req, res) -> resultsPage(req, res));   
	    }
	 
	 private static String inputDataPage(Request req, Response res) {
		 String page
	         = "<!DOCTYPE html>"
	         + "<html>"
	         + "<body style=\"background-color:#B9C5BC;\">"
	         +"<font align=\"center\" color=\"Green\" face=\"Comic Sans MS,arial\">"
	         + "<h1>Calculadora Trigonometrica</h1>"
	         + "<form action=\"/results\">"
	         + "  Ingrese unicamente las estas funciones trigonometricas 'sin','cos' o 'tan' seguido de un - y el numero en radian (EJ: sin-0) <br>"
	         + "  <input type=\"text\" name=\"numbers\" >"
	         + "  <br><br>"
	         + "  <input type=\"submit\" value=\"Submit\">"
	         + "</form>"
	         + "</body>"
	         + "</html>";
		 return page;
	    }

	 
	    private static JSONObject resultsPage(Request req, Response res) throws MalformedURLException, IOException {
	        String[] listaFunctions= req.queryParams("numbers").split("-");
	        String readline;
	        JSONObject myObject = new JSONObject();
	        BufferedReader input ;

	        double numberPath = Double.parseDouble(listaFunctions[1]);
	        String path = ""; 

	        if (listaFunctions[0].equals("sin") ){
	        	path = "sin-"+numberPath;
	        } else if (listaFunctions[0].equals("cos") ){
	        	path = "cos-"+numberPath;
	        } else if (listaFunctions[0].equals("tan") ){
	        	path = "tan-"+numberPath;
	        }

	        URL trigoApp = new URL("http://limitless-falls-93957.herokuapp.com/results?numbers="+path);
	        URLConnection coApp = trigoApp.openConnection();

	        input = new BufferedReader(new InputStreamReader(coApp.getInputStream()));
	        
	        BufferedReader stdinread = new BufferedReader(new InputStreamReader(System.in));
	        
	        

	        while ((readline = input.readLine()) != null) { 
	            
	            String aux = readline.replace("{\"El resultado es \":","").replace("}","");
	            myObject.put("Resultado de "+ path +" es : "+ readline, aux);
	        }

	        input.close();
	        stdinread.close();
	        res.header("Content-Type","application/json");        
	        return myObject;
	    }

	    static int getPort() {
	        if (System.getenv("PORT") != null) {
	            return Integer.parseInt(System.getenv("PORT"));
	        }        

	        return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)    }
	    }
}


