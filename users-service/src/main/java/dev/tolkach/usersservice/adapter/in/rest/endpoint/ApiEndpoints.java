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
        public static final String CHANGE_PASSWORD = BASE + "/change-password";
        public static final String ACTIVATE = BY_ID + "/activate";
        public static final String CHANGE_ROLE = BY_ID + "/change-role";
        public static final String CURRENT = BASE + "/current";

    }

    public static class Authentication {
        public static final String BASE = API_BASE + "/auth";
        public static final String SIGN_IN = BASE + "/sign-in";
        public static final String SIGN_UP = BASE + "/sign-up";
    }
}
