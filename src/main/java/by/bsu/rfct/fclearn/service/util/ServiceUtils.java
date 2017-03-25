package by.bsu.rfct.fclearn.service.util;

public class ServiceUtils {

    public static Integer countStartLimitFrom(Integer pageNumber, Integer amountOnPage) {
        return (pageNumber - 1) * amountOnPage;
    }
}
