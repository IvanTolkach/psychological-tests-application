package dev.tolkach.attemptsservice.adapter.in.rest.endpoint;

public final class ApiEndpoints {

    public static final String API_BASE = "/api";

    public static class TestAttempt {
        public static final String BASE = API_BASE + "/test-attempts";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{testAttemptId}";
    }

    public static class StudentAnswer {
        public static final String BASE = API_BASE + "/student-answers";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{studentAnswerId}";
    }

    public static class TestAttemptScore {
        public static final String BASE = API_BASE + "/test-attempt-scores";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{testAttemptScoreId}";
    }

    public static class Report {
        public static final String BASE = API_BASE + "/reports";
    }
}
