package dev.tolkach.methodologiesservice.adapter.in.rest.endpoint;

public final class ApiEndpoints {

    public static final String API_BASE = "/api";

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

    public static class ScaleQuestion {
        public static final String BASE = API_BASE + "/scale-questions";
        public static final String SEARCH = BASE + "/search";
        public static final String BY_ID = BASE + "/{scaleQuestionId}";
    }
}
