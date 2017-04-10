package by.bsu.rfct.fclearn.dto.user;

import by.bsu.rfct.fclearn.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("userConverter")
public class UserConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            userDTO.setId(user.getId());
            userDTO.setLogin(user.getLogin());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhoto(user.getPhoto());
            userDTO.setStatus(user.getStatus().toString());
        }
        return userDTO;
    }
}
