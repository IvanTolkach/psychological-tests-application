package dev.tolkach.usersservice.adapter.in.rest.endpoint;

public final class ApiEndpoints {

    public static final String API_BASE = "/api";

    public static class Student {
        public static final String BASE = API_BASE + "/students";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{studentId}";
    }

    public static class Faculty {
        public static final String BASE = API_BASE + "/faculties";
        public static final String SEARCH = BASE + "/search";
    }

    public static class Admin {
        public static final String BASE = API_BASE + "/admins";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{adminId}";
        public static final String CHANGE_PASSWORD = BY_ID + "/change-password";
    }
}
