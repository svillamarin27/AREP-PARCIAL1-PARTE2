package edu.escuelaing.arep.parcial.conection;


import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLConnection;
import org.json.JSONObject;
import java.io.InputStreamReader;
import java.net.URL;

public class Cliente {
	 public static void main( String[] args ) throws IOException {
		 	String readline;
	        BufferedReader input;
	        BufferedReader stdinread = new BufferedReader(new InputStreamReader(System.in));
	        
	        URL trigoApp = new URL("http://hidden-taiga-91065.herokuapp.com/results?numbers=sin-1");
	        
	        URLConnection coApp = trigoApp.openConnection();
	        
	        input = new BufferedReader(new InputStreamReader(coApp.getInputStream()));

	        JSONObject myObject = new JSONObject();
	        
	        while ((readline = input.readLine()) != null) {
	        	String aux = readline.replace("{\"El resultado es \":","").replace("}","");
	            myObject.put("Resultado de es : "+ readline, aux);
	        }
		
	        input.close();
			stdinread.close();
	    }

}
