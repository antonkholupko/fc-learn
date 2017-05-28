package by.bsu.rfct.fclearn.entity;

import java.util.Objects;

public class User extends AbstractEntity<Long> {

    private String login;
    private String email;
    private String password;
    private String photo;
    private Status status;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatusString(String statusString) {
        this.status = Status.valueOf(statusString.toUpperCase().trim());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(photo, user.photo) &&
                status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, email, password, photo, status);
    }

    public enum Status {
        ADMIN, ACTIVE, BANED
    }
}
