package by.bsu.rfct.fclearn.service.dto.user;

import by.bsu.rfct.fclearn.service.dto.AbstractDTO;

import java.util.Objects;

public class UserDTO extends AbstractDTO<Long> {

    private String login;
    private String email;
    private String photo;
    private String Status;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(login, userDTO.login) &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(photo, userDTO.photo) &&
                Objects.equals(Status, userDTO.Status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, email, photo, Status);
    }
}
