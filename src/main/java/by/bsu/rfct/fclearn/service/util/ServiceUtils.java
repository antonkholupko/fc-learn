package by.bsu.rfct.fclearn.service.util;

import org.apache.commons.codec.digest.DigestUtils;

public class ServiceUtils {

    public static Integer countStartLimitFrom(Integer pageNumber, Integer amountOnPage) {
        return (pageNumber - 1) * amountOnPage;
    }

    public static String getHashedPassword(String password) {
        return DigestUtils.md5Hex(password);
    }

}
