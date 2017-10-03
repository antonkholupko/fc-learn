package by.bsu.rfct.fclearn.service.impl;

import by.bsu.rfct.fclearn.dao.CollectionDAO;
import by.bsu.rfct.fclearn.dao.UserDAO;
import by.bsu.rfct.fclearn.dao.impl.UserDAOImpl;
import by.bsu.rfct.fclearn.dto.collection.CollectionDTO;
import by.bsu.rfct.fclearn.dto.user.UserConverter;
import by.bsu.rfct.fclearn.dto.user.UserConverterSmall;
import by.bsu.rfct.fclearn.dto.user.UserDTO;
import by.bsu.rfct.fclearn.dto.user.UserDTOConverter;
import by.bsu.rfct.fclearn.entity.User;
import by.bsu.rfct.fclearn.service.UserService;
import by.bsu.rfct.fclearn.service.exception.CannotLoginUserException;
import by.bsu.rfct.fclearn.service.exception.EntityExistsException;
import by.bsu.rfct.fclearn.service.util.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserDAOImpl.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserDAO userDAO;
    private UserConverter userConverter;
    private UserConverterSmall userConverterSmall;
    private CollectionDAO collectionDAO;
    private UserDTOConverter userDTOConverter;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserDAO userDAO, UserConverter userConverter,
                           UserConverterSmall userConverterSmall, CollectionDAO collectionDAO,
                           UserDTOConverter userDTOConverter) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDAO = userDAO;
        this.userConverter = userConverter;
        this.userConverterSmall = userConverterSmall;
        this.collectionDAO = collectionDAO;
        this.userDTOConverter = userDTOConverter;
    }

    @Override
    public Long create(UserDTO dto) {
        LOG.debug("UserService - create user login={}", dto.getLogin());
        if (!userDAO.checkIfExist(userDTOConverter.convert(dto))) {
            //dto.setPassword(ServiceUtils.getHashedPassword(dto.getPassword()));
            dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
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
    public Long update(UserDTO dto) {
        LOG.debug("UserService - update user id={}", dto.getId());
        return userDAO.update(userDTOConverter.convert(dto));
    }

    @Override
    public void delete(Long id) {
        LOG.debug("UserService - delete id={}", id);
        userDAO.delete(id);
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

    @Override
    public Long loginUser(UserDTO userDTO) {
        if (!StringUtils.isEmpty(userDTO.getLogin()) && !StringUtils.isEmpty(userDTO.getEmail()) &&
                !StringUtils.isEmpty(userDTO.getPassword())) {
            LOG.debug("UserService - login user login={}, email={}", userDTO.getLogin(), userDTO.getEmail());
            userDTO.setPassword(ServiceUtils.getHashedPassword(userDTO.getPassword()));
            User user = userDAO.readUserByLoginAndEmailAndPassword(userDTO.getLogin(), userDTO.getEmail(),
                    userDTO.getPassword());
            if (User.Status.BANED == user.getStatus()) {
                throw new CannotLoginUserException("User is baned");
            }
            return user.getId();
        } else if (!StringUtils.isEmpty(userDTO.getEmail()) && !StringUtils.isEmpty(userDTO.getPassword())) {
            LOG.debug("UserService - login user email={}", userDTO.getEmail());
            userDTO.setPassword(ServiceUtils.getHashedPassword(userDTO.getPassword()));
            User user = userDAO.readUserByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
            if (User.Status.BANED == user.getStatus()) {
                throw new CannotLoginUserException("User is baned");
            }
            return user.getId();
        } else if (!StringUtils.isEmpty(userDTO.getLogin()) && !StringUtils.isEmpty(userDTO.getPassword())) {
            LOG.debug("UserService - login user login={}", userDTO.getLogin());
            userDTO.setPassword(ServiceUtils.getHashedPassword(userDTO.getPassword()));
            User user = userDAO.readUserByLoginAndPassword(userDTO.getLogin(), userDTO.getPassword());
            if (User.Status.BANED == user.getStatus()) {
                throw new CannotLoginUserException("User is baned");
            }
            return user.getId();
        } else {
            throw new CannotLoginUserException("User doesn't have enough data to login");
        }
    }

    @Override
    public UserDTO getByName(String name) {
        return userConverter.convert(userDAO.readByUsername(name));
    }

    @Override
    public void addCollection(Long userId, Long collectionId) {
        userDAO.addCollection(userId, collectionId);
    }
}
