package pl.isa.thebuggers.fitly;
public class Main {
    public static void main(String[] args) {
UserData userData = new UserData();
UserService userService = new UserService();
        System.out.println(userService.json);
        System.out.println(userData.getAge());
    }
}
