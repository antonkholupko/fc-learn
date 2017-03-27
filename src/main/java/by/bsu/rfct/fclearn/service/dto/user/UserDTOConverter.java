package by.bsu.rfct.fclearn.service.dto.user;

import by.bsu.rfct.fclearn.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("userDTOConverter")
public class UserDTOConverter implements Converter<UserDTO, User> {

    @Override
    public User convert(UserDTO userDTO) {
        User user = new User();
        if (userDTO != null) {
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setPassword(userDTO.getPassword());
            user.setEmail(userDTO.getEmail());
            user.setPhoto(userDTO.getPhoto());
            user.setStatus(User.Status.valueOf(userDTO.getStatus()));
        }
        return user;
    }
}
