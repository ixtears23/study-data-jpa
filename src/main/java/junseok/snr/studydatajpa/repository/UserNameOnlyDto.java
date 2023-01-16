package junseok.snr.studydatajpa.repository;

public class UserNameOnlyDto {

    private final String userName;

    public UserNameOnlyDto(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
