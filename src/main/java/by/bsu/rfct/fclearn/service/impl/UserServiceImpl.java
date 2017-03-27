package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CollectionDAO;
import by.bsu.rfct.fclearn.dao.UserDAO;
import by.bsu.rfct.fclearn.dao.impl.UserDAOImpl;
import by.bsu.rfct.fclearn.entity.User;
import by.bsu.rfct.fclearn.service.UserService;
import by.bsu.rfct.fclearn.service.dto.user.UserConverter;
import by.bsu.rfct.fclearn.service.dto.user.UserConverterSmall;
import by.bsu.rfct.fclearn.service.dto.user.UserDTO;
import by.bsu.rfct.fclearn.service.dto.user.UserDTOConverter;
import by.bsu.rfct.fclearn.service.exception.EntityExistsException;
import by.bsu.rfct.fclearn.service.util.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserDAOImpl.class);

    private UserDAO userDAO;
    private UserConverter userConverter;
    private UserConverterSmall userConverterSmall;
    private CollectionDAO collectionDAO;
    private UserDTOConverter userDTOConverter;

    public UserServiceImpl(UserDAO userDAO, UserConverter userConverter, UserConverterSmall userConverterSmall,
                           CollectionDAO collectionDAO, UserDTOConverter userDTOConverter) {
        this.userDAO = userDAO;
        this.userConverter = userConverter;
        this.userConverterSmall = userConverterSmall;
        this.collectionDAO = collectionDAO;
        this.userDTOConverter = userDTOConverter;
    }

    @Override
    public Long create(UserDTO dto) {
        LOG.debug("UserService - create user login={}", dto.getLogin());
        if(!userDAO.checkIfExist(userDTOConverter.convert(dto))) {
            return userDAO.create(userDTOConverter.convert(dto));
        } else {
            throw new EntityExistsException("Such user exists");
        }
    }

    @Override
    public UserDTO read(Long id) {
        LOG.debug("UserService - read by id={}", id);
        UserDTO userDTO = userConverter.convert(userDAO.read(id));
        userDTO.setCollectionsAuthorAmount(collectionDAO.countByAuthorId(userDTO.getId()));
        return userDTO;
    }

    @Override
    public UserDTO update(UserDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<UserDTO> readAll(Integer pageNumber, Integer amountOnPage) {
        LOG.debug("UserService - read all users");
        List<UserDTO> userDTOs = new ArrayList<>();
        List<User> users = userDAO.readAll(ServiceUtils.countStartLimitFrom(pageNumber, amountOnPage), amountOnPage);
        for (User user : users) {
            userDTOs.add(userConverterSmall.convert(user));
        }
        return userDTOs;
    }

    @Override
    public Long countAll() {
        LOG.debug("UserService - count all");
        return userDAO.countAll();
    }
}
