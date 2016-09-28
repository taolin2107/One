package app.taolin.one.models;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 08, 2016.
 * @description
 */

public interface BaseArticle {

    //文章ID
    String getId();
    //文章标题
    String getTitle();
    //文章副标题
    String getSubTitle();
    //文章作者
    String getAuthor();
    //作者简介
    String getAuthorDesc();
    //作者微博
    String getWeibo();
    //责任编辑
    String getAuthorIntro();
    //正文内容
    String getContent();
    //web链接
    String getWebLink();
    //引导语
    String getGuideWord();
    //创建日期
    String getMakeTime();
}
