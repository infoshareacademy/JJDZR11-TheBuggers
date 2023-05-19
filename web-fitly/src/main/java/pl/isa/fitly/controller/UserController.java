package pl.isa.fitly.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import pl.isa.fitly.model.UserData;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private List<UserData> usersData;

    public UserController() {
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
        List<UserData> users = usersData.stream().filter(user -> user.getEmail().equals(email)).toList();
        if (users.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public UserData getUserByEmail(String email) {
        List<UserData> users = usersData.stream().filter(user -> user.getEmail().equals(email)).toList();
        if (users.isEmpty()) {
            return new UserData();
        } else {
            return users.get(0);
        }
    }

    private List<UserData> readUsers() {
//        List<UserData> result = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Path pathJson = Path.of("web-fitly", "src", "main", "resources", "UserData.json");
        TypeReference<List<UserData>> typeReference = new TypeReference<>() {
        };
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

    public  enum formError{
        OK(""),
        WRITE_ERROR("Write error"),
        USER_EXISTS("User exists");

        String text;
        formError(String text){
            this.text=text;
        }

    }

}
