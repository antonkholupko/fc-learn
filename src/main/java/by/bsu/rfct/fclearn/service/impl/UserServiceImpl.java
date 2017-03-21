package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.service.UserService;
import by.bsu.rfct.fclearn.service.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Override
    public UserDTO create(UserDTO dto) {
        return null;
    }

    @Override
    public UserDTO read(Long id) {
        return null;
    }

    @Override
    public UserDTO update(UserDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<UserDTO> readAll() {
        return null;
    }

    @Override
    public Long countAll() {
        return null;
    }
}
