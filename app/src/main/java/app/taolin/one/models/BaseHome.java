package app.taolin.one.models;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 08, 2016.
 * @description
 */

public interface BaseHome {

    //首页Id
    String getId();
    //标题
    String getTitle();
    //图片链接
    String getImageUrl();
    //图片作者
    String getAuthor();
    //每日一句
    String getContent();
    //web链接
    String getWebLink();
    //创建日期
    String getMakeTime();
}
