package pl.isa.thebuggers.fitly;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.util.ISO8601Utils;
import java.nio.file.Path;
import java.nio.file.Files;
public class UserService extends UserData {
    Gson gson = new Gson();
    GsonBuilder gsonBuilder = new GsonBuilder();
    UserData userData = new UserData();
    String json = gson.toJson(userData);
}