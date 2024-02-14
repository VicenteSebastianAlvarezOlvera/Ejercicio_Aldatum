import java.net.*;
import java.io.*;
import java.util.Iterator; 
import java.util.Map; 

//Imports for using JSONs
//import org.json.simple.JSONArray; 
//import org.json.simple.JSONObject; 
//import org.json.simple.parser.*;


public class aldatum {
    @SuppressWarnings("deprecation")
    //Main function, which will connect to the web service using the link and the authentication code
    public static void main(String[] args) throws Exception{
        //Link of the Source Web Service
        String SourceWebService = "https://elastic.snaplogic.com/api/1/rest/slsched/feed/Partners/AllDatum/Entrevista_Integracion/LeeArchivoNombresTask";
        //Link of the parameters
        String Param1 = "first_names";
        String Param2 = "txt";
        //We create the authentication token
        String authen = "Bearer h8JLQvfj5Yl1iQeOvBT43d17RoDBO6UQ";

        //We create the final link, combining the Source Web Service and its 2 parameters, and create the URL to be used
        String Link = SourceWebService + "?archivo=" + Param1 + "&extension=" + Param2;
        URL enlace = new URL(Link);
        
        //Creates a connection 
        HttpURLConnection connection = (HttpURLConnection) enlace.openConnection();
        //Specifies the type of request and its authentication key
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", authen);

        //Creates a buffered readder in order to get the response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            System.out.println(inputLine);
        }
        in.close();

        //JSONParser parser=new JSONParser();
        //Object object = parser.parse(in);
        //
        //JSONArray array = (JSONArray) object;        
        //JSONObject object2 = (JSONObject)array.get(0);
        //System.out.println(object2.get("hello")); 

        //System.out.println(inputLine);

        
    }
}