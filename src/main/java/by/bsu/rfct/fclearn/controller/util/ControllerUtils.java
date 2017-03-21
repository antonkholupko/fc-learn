package by.bsu.rfct.fclearn.controller.util;

public class ControllerUtils {

    private final static int MAX_PAGE_SIZE = 100;
    public final static String DEFAULT_PAGE_NUMBER = "1";
    public final static String DEFAULT_PAGE_SIZE = "10";

    private ControllerUtils() {
    }

    public static Long calculatePagesAmount(Long entitiesCount, Long pageSize) {
        return (long) Math.ceil(entitiesCount * 1.0 / pageSize);
    }

    public static Long validatePageNumber(Long pageNumber) {
        return pageNumber > 0 ? (pageNumber < MAX_PAGE_SIZE ? pageNumber : MAX_PAGE_SIZE) :
                Integer.valueOf(DEFAULT_PAGE_NUMBER);
    }

    public static Long validatePageSize(Long pageSize) {
        return pageSize > 0 ? pageSize : Integer.valueOf(DEFAULT_PAGE_SIZE);
    }
}