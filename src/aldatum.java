import java.net.*;
import java.io.*;
import java.util.Comparator;
import java.util.Iterator; 
import java.util.Map; 

//Imports for using JSONs
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;


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
        //JSONObject object2 = (JSONObject)array.get(0);
        //System.out.println(array); 
        //System.out.println(inputLine);

        //Save the file to local
        try {
            // Create a FileWriter to write to the file
            FileWriter fileWriter = new FileWriter("output.json");
            String jsonString = array.toJSONString();
            // Write the JSON string to the file
            fileWriter.write(jsonString);

            // Close the FileWriter
            fileWriter.close();

            //System.out.println("JSON saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Sort by name
        try {
            // Sort the JSONArray by the "NAME" field
            array.sort(Comparator.comparing(obj -> ((JSONObject) obj).get("NAME").toString()));

            // Print the sorted JSONArray
            //System.out.println(array.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        int totalSum = 0;
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            String name = (String) obj.get("NAME");
            int nameScore = calculateNameScore(name) * (i + 1); // Multiply by alphabetical position
            //obj.put("value", nameScore);
            //obj.put("position", i + 1);
            totalSum += nameScore;
        }

        //Save sorted
        /* 
        try {
            // Create a FileWriter to write to the file
            FileWriter fileWriter = new FileWriter("output2.json");
            String jsonString = array.toJSONString();
            // Write the JSON string to the file
            fileWriter.write(jsonString);

            // Close the FileWriter
            fileWriter.close();

            //System.out.println("JSON saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        System.out.println("Final value: " + totalSum);
        //Get values for each name

    }

    private static int calculateNameScore(String name) {
        int score = 0;
        for (char c : name.toCharArray()) {
            score += Character.toUpperCase(c) - 'A' + 1; // Calculate alphabetical value
        }
        return score;
    }
}