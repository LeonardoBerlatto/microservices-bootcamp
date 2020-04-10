package br.com.photoapp.api.usermanagement.utils;

public class JdbiUtils {

    public static void validateInsert(Long id) {
        if (id <= 0) {
            throw new RuntimeException("Something went wrong, review the data you are trying to submit.");
        }
    }
}
