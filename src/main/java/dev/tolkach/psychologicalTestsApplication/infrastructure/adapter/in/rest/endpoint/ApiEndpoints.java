package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint;

public final class ApiEndpoints {

    public static final String API_BASE = "/api";

    public static class Student {
        public static final String BASE = API_BASE + "/students";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{studentId}";
    }
}
