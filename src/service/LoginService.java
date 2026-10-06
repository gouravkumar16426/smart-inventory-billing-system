package service;

public class LoginService {

    private final String USERNAME = "admin";
    private final String PASSWORD = "1234";

    public boolean login(String username, String password) {

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            return true;
        }

        return false;
    }
}