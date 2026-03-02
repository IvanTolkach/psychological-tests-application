package dev.tolkach.testsservice.adapter.in.rest.endpoint;

public final class ApiEndpoints {

    public static final String API_BASE = "/api";

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

    public static class AnswerOption {
        public static final String BASE = API_BASE + "/answer-options";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{answerOptionId}";
    }
}
