package pl.isa.fitly.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.isa.fitly.model.UserData;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
@Repository
public class UserRepository {
    private List<UserData> usersData;
    private boolean userLoggedIn;


    public UserRepository() {
        usersData = readUsers();
    }

    public formError addUser(UserData userData) {
        if (userExists(userData.getEmail())) {
            return formError.USER_EXISTS;
        } else {
            usersData.add(userData);
            return saveUsersData();
        }
    }

    public boolean userExists(String email) {
        List<UserData> users = usersData.stream()
                .filter(user -> user.getEmail().equals(email))
                .toList();
        return !users.isEmpty();
    }

    public formError userLogin(String email, String password) {
        if (userExists(email)) {
            if (getUserByEmail(email).getPassword().equals(password)) {
                return formError.OK;
            } else {
                return formError.INCORRECT_PASSWORD;
            }
        } else {
            return formError.NOT_FOUND_USER;
        }
    }

    public UserData getUserByEmail(String email) {
        List<UserData> users = usersData.stream()
                .filter(user -> user.getEmail().equals(email))
                .toList();
        if (users.isEmpty()) {
            return new UserData();
        } else {
            return users.get(0);
        }
    }

    public List<UserData> getUsersData(){
        return usersData;
    }

    private List<UserData> readUsers() {
        ObjectMapper objectMapper = new ObjectMapper();
        Path pathJson = Path.of("web-fitly", "src", "main", "resources", "UserData.json");
        TypeReference<List<UserData>> typeReference = new TypeReference<>() {};
        try {
            return objectMapper.readValue(Files.readString(pathJson), typeReference);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " - " + e);
        }
        return new ArrayList<>();
    }

    private formError saveUsersData() {
        ObjectMapper objectMapper = new ObjectMapper();
        Path pathJson = Path.of("web-fitly", "src", "main", "resources", "UserData.json");
        try {
            String json = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(usersData);
            Files.writeString(pathJson, json);
            return formError.OK;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " - " + e);
            return formError.WRITE_ERROR;
        }
    }

    public boolean isUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    public enum formError {
        OK(""),
        WRITE_ERROR("Write error"),
        USER_EXISTS("User exists"),
        NOT_FOUND_USER("Not found user"),
        INCORRECT_PASSWORD("Incorrect password");
        public String text;
        formError(String text) {
            this.text = text;
        }
    }

}
