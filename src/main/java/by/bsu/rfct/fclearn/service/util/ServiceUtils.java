package by.bsu.rfct.fclearn.service.util;

public class ServiceUtils {

    public static Long countStartLimitFrom(Long pageNumber, Long amountOnPage) {
        return (pageNumber - 1) * amountOnPage;
    }
}
