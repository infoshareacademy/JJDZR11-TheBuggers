package pl.isa.thebuggers.fitly;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class UserService {
    Gson gson = new Gson();

    public void saveUserData(UserData data, String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        gson.toJson(data, writer);
        writer.close();
    }

    public UserData getUserData(String filename) throws IOException {
        JsonParser parser = new JsonParser();
        FileReader reader = new FileReader(filename);
        JsonObject jsonObject = parser.parse(reader).getAsJsonObject();
        UserData data = gson.fromJson(jsonObject, UserData.class);
        reader.close();
        return data;
    }

}