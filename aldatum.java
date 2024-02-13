import java.net.*;
import java.io.*;

import java.io.FileReader; 
import java.util.Iterator; 
import java.util.Map; 

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

public class aldatum {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception{
        String Link = "https://elastic.snaplogic.com/api/1/rest/slsched/feed/Partners/AllDatum/Entrevista_Integracion/LeeArchivoNombresTask";
        URL enlace = new URL(Link);
        String authen = "h8JLQvfj5Yl1iQeOvBT43d17RoDBO6UQ";
        HttpURLConnection connection = (HttpURLConnection) enlace.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + authen);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        //    StringBuilder response = new StringBuilder();
        //    String inputLine;
        //    while ((inputLine = in.readLine()) != null) {
        //        response.append(inputLine);
        //        System.out.println(inputLine);
        //    }
        //    in.close();
        //JSONParser parser=new JSONParser();
        //Object object = parser.parse(in);
        //
        //JSONArray array = (JSONArray) object;        
        //JSONObject object2 = (JSONObject)array.get(0);
        //System.out.println(object2.get("hello")); 

        System.out.println(in);

        
    }
}