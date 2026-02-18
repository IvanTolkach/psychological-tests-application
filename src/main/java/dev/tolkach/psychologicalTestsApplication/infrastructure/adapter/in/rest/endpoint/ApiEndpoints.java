package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint;

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

    public static class Methodology {
        public static final String BASE = API_BASE + "/methodologies";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{methodologyId}";
    }

    public static class Scale {
        public static final String BASE = API_BASE + "/scales";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{scaleId}";
    }

    public static class ScoreRange {
        public static final String BASE = API_BASE + "/score-ranges";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{scoreRangeId}";
    }

    public static class Test {
        public static final String BASE = API_BASE + "/tests";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{testId}";
        public static final String UPDATE_STATUS = BY_ID + "/update-status";
    }

    public static class Question {
        public static final String BASE = API_BASE + "/questions";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{questionId}";
    }
}
