import java.net.*;
import java.io.*;
import java.util.Comparator;


//Importaciones para usar JSONs
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;




public class aldatum {
    //Se crea la función principal, la cual realizará todo el procedimiento
    public static void main(String[] args) throws Exception{
        //Enlace del Source Web Service
        String SourceWebService = "https://elastic.snaplogic.com/api/1/rest/slsched/feed/Partners/AllDatum/Entrevista_Integracion/LeeArchivoNombresTask";
       
        //Los parámetros a usar con el Source Web Service
        String archivoSWS = "first_names";
        String extensionSWS = "txt";
        //Se almacena el token de autenticación
        String authenSWS = "Bearer h8JLQvfj5Yl1iQeOvBT43d17RoDBO6UQ";


        //Se crea el link final, combinando el link del Source Web Service con los dos parámetros a usar, y creamos una variable tipo URL
        String LinkSWS = SourceWebService + "?archivo=" + archivoSWS + "&extension=" + extensionSWS;
        URL enlaceSWS = new URL(LinkSWS);
       
        //Se crea la conexión al enlace
        HttpURLConnection connection = (HttpURLConnection) enlaceSWS.openConnection();
        //Se especifica el tipo de solicitud, en este caso GET, y pasamos el token de autenticación
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", authenSWS);


        //Se crea un bufferedReader para leer los datos de la conexión
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));


        //Se guarda la respuesta en inputLine
        StringBuilder respuesta = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            respuesta.append(inputLine);
        }
        in.close();


        //Se crea un parser JSON para guardar la respuesta
        JSONParser parser=new JSONParser();
        Object object = parser.parse(respuesta.toString());
       
        //Se convierte la respuesta a JSON
        JSONArray array = (JSONArray) object;        


        //Se ordena el contenido de JSON de manera alfabética
        try {
            array.sort(Comparator.comparing(obj -> ((JSONObject) obj).get("NAME").toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        //Se suman los valores de los nombres usando la función CalcularValorDelNombre
        int totalSuma = 0;
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            String name = (String) obj.get("NAME");
            //Se multiplica el valor de cada nombre por su posición alfabética
            int nameScore = CalcularValorDelNombre(name) * (i + 1);
            obj.put("value", nameScore);
            obj.put("position", i + 1);
            totalSuma += nameScore;
        }


        //Se crea un JSON para enviar el resultado
        JSONObject resultado = new JSONObject();
        resultado.put("ResultadoObtenido", totalSuma);


        //Se prepara el link, los parámetros, y la autenticación
        String TargetWebService = "https://elastic.snaplogic.com/api/1/rest/slsched/feed/Partners/AllDatum/Entrevista_Integracion/VerificaResultadoEjercicioTecnico1ALLDATUMTask";
        //Contenido de los parámetros
        String archivoTWS = "first_names";
        String extensionTWS = "txt";
        String nombreTWS = "Vicente_Alvarez";
        String pruebaTWS = "0";
        //Token de autenticación
        String authenTWS = "Bearer giqJWNuzhOnDTYaa1Diy1jw7FQhqZSwl";


        //Se crea el enlace final, concatenando el TargetWebService y sus 4 parámetros
        String LinkTWS = TargetWebService + "?archivo=" + archivoTWS + "&extension=" + extensionTWS + "&nombre=" + nombreTWS + "&prueba=" + pruebaTWS;
        URL enlaceTWS = new URL(LinkTWS);


        //Se inicia la conexión
        HttpURLConnection connectionTWS = (HttpURLConnection) enlaceTWS.openConnection();
        //Se especifica el tpo de respuesta, el tipo de contenido a enviar, el token de autorización, y se configura para poder mandar datos
        connectionTWS.setRequestMethod("POST");
        connectionTWS.setRequestProperty("Authorization", authenTWS);
        connectionTWS.setRequestProperty("Content-Type", "application/json");
        connectionTWS.setDoOutput(true);


        //Se envía el contenido del JSON
        try(OutputStream os = connectionTWS.getOutputStream()) {
            byte[] input = resultado.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }


        //Se obtiene el estado de la respuesta
        int responseCode = connectionTWS.getResponseCode();
        System.out.println("Response Code: " + responseCode);


    }


    //Función para calcular el valor de cada nombre
    private static int CalcularValorDelNombre(String nombre) {
        int valor = 0;
        for (char c : nombre.toCharArray()) {
            valor += Character.toUpperCase(c) - 'A' + 1; // Calcula el valor alfabético
        }
        return valor;
    }
}
