package app.taolin.one.common;

import app.taolin.one.models.Article;
import app.taolin.one.models.Home;
import app.taolin.one.models.Question;
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

    @FormUrlEncoded
    @POST("getHp_N")
    Call<Home> getPhoto(@Field("strDate") String date, @Field("strRow") int row);

    @FormUrlEncoded
    @POST("getC_N")
    Call<Article> getArticle(@Field("strDate") String date, @Field("strRow") int row, @Field("strMS") int ms);

    @FormUrlEncoded
    @POST("getQ_N")
    Call<Question> getQuestion(@Field("strDate") String date, @Field("strRow") int row);
}
