package pl.isa.fitly.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import pl.isa.fitly.model.ContactInfo;
import pl.isa.fitly.model.UserData;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private List<UserData> usersData;
    private UserData currentUser;

    public UserRepository() {
        usersData = readUsers();
    }

    public formError userUpdate(String email, UserData userData) {
        int userId = usersData.indexOf(getUserByEmail(email));
        usersData.set(userId,userData);
        return saveUsersData();
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

    public UserData getUserFromPrincipal(Principal principal) {
        UserData userData = UserData.createUserData();
        userData = getUserByEmail(principal.getName());
        return userData;
    }

    public List<UserData> getUsersData() {
        return usersData;
    }

    private List<UserData> readUsers() {
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

    public UserData getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserData currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isCurrentUser() {
        return (this.getCurrentUser() != null && !this.getCurrentUser().emptyUser());
    }

    public enum formError {
        OK(""),
        WRITE_ERROR("Write error"),
        USER_EXISTS("User exists"),
        NOT_FOUND_USER("Not found user"),
        INCORRECT_PASSWORD("Incorrect password"),
        UPDATE_ERROR("Update error"),
        CHAT_ROOM_EXISTS("Chat room already exists");

        public String text;

        formError(String text) {
            this.text = text;
        }
    }

    public List<UserData> getSpecialists() {
        return usersData.stream()
                .filter(user -> !user.getRole().equals("USER"))
                .collect(Collectors.toList());
    }

    public List<String> getChatRooms(String email) {
        return getUserByEmail(email).getRoomIds();
    }

    public void updateUserData(UserData userData) {
        // Find the user in the list by email and update their data
        List<UserData> updatedUsers = usersData.stream()
                .map(user -> user.getEmail().equals(userData.getEmail()) ? userData : user)
                .collect(Collectors.toList());

        // Save the updated list to the JSON file
        saveUsersData(updatedUsers);
    }

    private void saveUsersData(List<UserData> updatedUsers) {
        ObjectMapper objectMapper = new ObjectMapper();
        Path pathJson = Path.of("web-fitly", "src", "main", "resources", "UserData.json");
        try {
            String json = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(updatedUsers);
            Files.writeString(pathJson, json);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " - " + e);
        }
    }

    public formError addChatRoom(String email, String chatRoomId) {
        UserData user = getUserByEmail(email);
        if (user.getRoomIds().contains(chatRoomId)) {
            return formError.CHAT_ROOM_EXISTS;
        } else {
            user.getRoomIds().add(chatRoomId);
            updateUserData(user);
            return formError.OK;
        }
    }

    public List<UserData> getContacts(List<String> roomIds) {
        return usersData.stream()
                .filter(user -> roomIds.containsAll(user.getRoomIds()) && !user.getEmail().equals(currentUser.getEmail()))
                .collect(Collectors.toList());
    }

    public void setCurrentUserByEmail(String email) {
        this.currentUser = getUserByEmail(email);
    }

    public List<ContactInfo> getContactsForUser(String userEmail) {
        List<ContactInfo> contacts = new ArrayList<>();

        // Pobierz użytkownika na podstawie jego emaila
        UserData user = getUserByEmail(userEmail);
        if (user == null) {
            return contacts;
        }

        // Przejdź przez wszystkie roomId użytkownika
        for (String roomId : user.getRoomIds()) {
            // Znajdź użytkownika, który ma dokładnie taki sam roomId
            UserData contactUser = usersData.stream()
                    .filter(u -> u.getRoomIds().contains(roomId) && !u.getEmail().equals(userEmail))
                    .findFirst()
                    .orElse(null);

            if (contactUser != null) {
                // Jeśli znaleziono użytkownika, dodaj go do listy kontaktów
                String contactEmail = roomId.replace(userEmail + "_", "");
                contacts.add(new ContactInfo(contactEmail, contactUser.getName(), roomId));
            }
        }

        return contacts;
    }

}
