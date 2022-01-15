package site.nomoreparties.stellarburgers;

public class UserCredentials {

    public String email;
    public String password;

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentials from(User user) {
        return new UserCredentials(user.email, user.password);
    }
}
