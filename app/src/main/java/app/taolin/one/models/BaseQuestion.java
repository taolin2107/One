package app.taolin.one.models;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 08, 2016.
 * @description
 */

public interface BaseQuestion {

    //问题Id
    String getId();
    //问题标题
    String getQuestionTitle();
    //问题内容
    String getQuestionContent();
    //回答标题
    String getAnswerTitle();
    //回答内容
    String getAnswerContent();
    //责任编辑
    String getEditor();
    //web链接
    String getWebLink();
    //引导语
    String getGuideWord();
    //创建日期
    String getMakeTime();
}
