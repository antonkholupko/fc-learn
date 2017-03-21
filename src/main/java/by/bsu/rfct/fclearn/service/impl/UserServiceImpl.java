package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.UserDAO;
import by.bsu.rfct.fclearn.dao.impl.UserDAOImpl;
import by.bsu.rfct.fclearn.service.UserService;
import by.bsu.rfct.fclearn.service.dto.user.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserDAOImpl.class);

    @Autowired
    private UserDAO userDAO;

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
    public List<UserDTO> readAll(Long pageNumber, Long amountOnPage) {
        return null;
    }

    @Override
    public Long countAll() {
        LOG.debug("UserService - count all");
        return userDAO.countAll();
    }

    @Override
    public Boolean checkIfExists(UserDTO dto) {
        return null;
    }
}
