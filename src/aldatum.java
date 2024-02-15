import java.net.*;
import java.io.*;
import java.util.Comparator;
//import java.util.Iterator; 
//import java.util.Map; 

//Imports for using JSONs
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;


public class aldatum {
    @SuppressWarnings({ "deprecation", "unchecked" })
    //Main function, which will connect to the web service using the link and the authentication code
    public static void main(String[] args) throws Exception{
        //Link of the Source Web Service
        String SourceWebService = "https://elastic.snaplogic.com/api/1/rest/slsched/feed/Partners/AllDatum/Entrevista_Integracion/LeeArchivoNombresTask";
        
        //Link of the parameters
        String archivoSWS = "first_names";
        String extensionSWS = "txt";
        //We create the authentication token
        String authenSWS = "Bearer h8JLQvfj5Yl1iQeOvBT43d17RoDBO6UQ";

        //We create the final link, combining the Source Web Service and its 2 parameters, and create the URL to be used
        String LinkSWS = SourceWebService + "?archivo=" + archivoSWS + "&extension=" + extensionSWS;
        URL enlaceSWS = new URL(LinkSWS);
        
        //Creates a connection 
        HttpURLConnection connection = (HttpURLConnection) enlaceSWS.openConnection();
        //Specifies the type of request and its authentication key
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", authenSWS);

        ////Creates a buffered readder in order to get the response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            //System.out.println(inputLine);
        }
        in.close();

        //Creates a JSON parser to get the response 
        JSONParser parser=new JSONParser();
        Object object = parser.parse(response.toString());
        
        //Converts the response to JSON format
        JSONArray array = (JSONArray) object;        

        //Sort by name
        try {
            // Sort the JSONArray by the "NAME" field
            array.sort(Comparator.comparing(obj -> ((JSONObject) obj).get("NAME").toString()));

            // Print the sorted JSONArray
            //System.out.println(array.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Gets the sum of all names
        int totalSum = 0;
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            String name = (String) obj.get("NAME");
            int nameScore = calculateNameScore(name) * (i + 1); // Multiply by alphabetical position
            //obj.put("value", nameScore);
            //obj.put("position", i + 1);
            totalSum += nameScore;
        }

        //Creates JSON to send
        JSONObject resultado = new JSONObject();
        resultado.put("ResultadoObtenido", totalSum);
        System.out.println(resultado);

        //Prepare link, parameters and authentication to send delivery
        String TargetWebService = "https://elastic.snaplogic.com/api/1/rest/slsched/feed/Partners/AllDatum/Entrevista_Integracion/VerificaResultadoEjercicioTecnico1ALLDATUMTask";
        //Link of the parameters
        String archivoTWS = "first_names";
        String extensionTWS = "txt";
        String nombreTWS = "Vicente_Alvarez";
        String pruebaTWS = "1";
        //We create the authentication token
        String authenTWS = "Bearer giqJWNuzhOnDTYaa1Diy1jw7FQhqZSwl";

        //We create the final link, combining the Source Web Service and its 2 parameters, and create the URL to be used
        String LinkTWS = TargetWebService + "?archivo=" + archivoTWS + "&extension=" + extensionTWS + "&nombre=" + nombreTWS + "&prueba=" + pruebaTWS;
        //System.out.println(LinkTWS);
        URL enlaceTWS = new URL(LinkTWS);

        //Creates a connection 
        HttpURLConnection connectionTWS = (HttpURLConnection) enlaceTWS.openConnection();
        ////Specifies the type of request and its authentication key
        connectionTWS.setRequestMethod("POST");
        connectionTWS.setRequestProperty("Authorization", authenTWS);
        connectionTWS.setRequestProperty("Content-Type", "application/json");
        connectionTWS.setDoOutput(true);

        try(OutputStream os = connectionTWS.getOutputStream()) {
            byte[] input = resultado.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        int responseCode = connectionTWS.getResponseCode();
        System.out.println("Response Code: " + responseCode);

    }

    private static int calculateNameScore(String name) {
        int score = 0;
        for (char c : name.toCharArray()) {
            score += Character.toUpperCase(c) - 'A' + 1; // Calculate alphabetical value
        }
        return score;
    }
}