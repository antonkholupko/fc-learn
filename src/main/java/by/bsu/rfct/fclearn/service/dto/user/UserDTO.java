package by.bsu.rfct.fclearn.service.dto.user;

import by.bsu.rfct.fclearn.service.dto.AbstractDTO;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.util.Objects;

public class UserDTO extends AbstractDTO<Long> {

    @NotBlank(message = "{validation.user.login.empty}")
    @Length(min = 5, max = 100, message = "{validation.user.name.length}")
    private String login;

    @NotBlank(message = "{validation.user.password.empty}")
    private String password;

    @NotBlank(message = "{validation.user.email.empty}")
    @Email(message = "{validation.user.email.invalid}")
    private String email;

    @URL(message = "{validation.user.photo.invalid}")
    private String photo;

    @NotBlank(message = "{validation.user.status.empty}")
    private String Status;
    private Long collectionsAuthorAmount;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Long getCollectionsAuthorAmount() {
        return collectionsAuthorAmount;
    }

    public void setCollectionsAuthorAmount(Long collectionsAuthorAmount) {
        this.collectionsAuthorAmount = collectionsAuthorAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(login, userDTO.login) &&
                Objects.equals(password, userDTO.password) &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(photo, userDTO.photo) &&
                Objects.equals(Status, userDTO.Status) &&
                Objects.equals(collectionsAuthorAmount, userDTO.collectionsAuthorAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, password, email, photo, Status, collectionsAuthorAmount);
    }
}
