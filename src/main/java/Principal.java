import com.google.gson.Gson;

public class Principal {
    public static void main(String[] args) {
        //System.out.println("Entorno Java configurado correctamente 🚀");
        Gson gson = new Gson();
        String json = gson.toJson("Hola Gson");
        System.out.println(json);
    }
}
