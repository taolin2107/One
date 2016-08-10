package app.taolin.one.utils;

/**
 * Created by Taolin on 16/5/26.
 */

public class Api {

    private static final String URL_BASE = "http://v3.wufazhuce.com:8000/api";

    public static final String URL_HOME_LIST = URL_BASE + "/hp/bymonth/";

    public static final String URL_ARTICLE_LIST = URL_BASE + "/essay/bymonth/";

    public static final String URL_ARTICLE = URL_BASE + "/essay/";

    public static final String URL_QUESTION_LIST = URL_BASE + "/question/bymonth/";

    public static final String URL_QUESTION = URL_BASE + "/question/";
}
