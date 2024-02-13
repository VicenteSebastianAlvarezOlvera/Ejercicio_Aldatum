import java.net.*;
import java.io.*;

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
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
        System.out.println(inputLine);

        
    }
}