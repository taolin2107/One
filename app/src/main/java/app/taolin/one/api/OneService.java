package app.taolin.one.api;

import app.taolin.one.models.LatestArticle;
import app.taolin.one.models.LatestHome;
import app.taolin.one.models.LatestQuestion;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Taolin on 16/5/26.
 *
 * 获取指定日期的图片,文章,问题
 */

public interface OneService {

    /**
     * 首页
     * Method: post
     * Params: strDate(yyyy-MM-dd), strRow(int)
     */
    @FormUrlEncoded
    @POST("getHp_N")
    Call<LatestHome> getHome(@Field("strDate") String date, @Field("strRow") int row);

    /**
     * 文章
     * Method: post
     * Params: strDate(yyyy-MM-dd), strRow(int), strMS(int)
     */
    @FormUrlEncoded
    @POST("getC_N")
    Call<LatestArticle> getArticle(@Field("strDate") String date, @Field("strRow") int row, @Field("strMS") int ms);

    /**
     * 问题
     * Method: post
     * Params: strDate(yyyy-MM-dd), strRow(int)
     */
    @FormUrlEncoded
    @POST("getQ_N")
    Call<LatestQuestion> getQuestion(@Field("strDate") String date, @Field("strRow") int row);
}
