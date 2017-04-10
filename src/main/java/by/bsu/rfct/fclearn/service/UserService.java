package by.bsu.rfct.fclearn.service;

import by.bsu.rfct.fclearn.dto.user.UserDTO;

public interface UserService extends GenericService<UserDTO, Long> {

    Long loginUser(UserDTO userDTO);

}
