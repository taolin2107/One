package app.taolin.one.common;

/**
 * Created by Taolin on 16/5/26.
 */

public class Api {
    /**
     * 主URL
     */
    public static final String URL_BASE = "http://rest.wufazhuce.com/OneForWeb/one/";
    /**
     * 首页
     * Method: post
     * Params: strDate(yyyy-MM-dd), strRow(int)
     */
    public static final String URL_HOME = "http://rest.wufazhuce.com/OneForWeb/one/getHp_N";

    /**
     * 文章
     * Method: post
     * Params: strDate(yyyy-MM-dd), strRow(int), strMS(int)
     */
    public static final String URL_ARTICLE = "http://rest.wufazhuce.com/OneForWeb/one/getC_N";

    /**
     * 问题
     * Method: post
     * Params: strDate(yyyy-MM-dd), strRow(int)
     */
    public static final String URL_QUESTION = "http://rest.wufazhuce.com/OneForWeb/one/getQ_N";
}
