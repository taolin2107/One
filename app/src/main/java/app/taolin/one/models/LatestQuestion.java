package app.taolin.one.models;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日问题
 */

public class LatestQuestion implements BaseQuestion {

    public String result;

    public QuestionAdEntity questionAdEntity;

    public class QuestionAdEntity {
        public String strQuestionId;
        public String strQuestionTitle;
        public String strQuestionContent;
        public String strQuestionMarketTime;
        public String strAnswerTitle;
        public String strAnswerContent;
        public String strPraiseNumber;
        public String sWebLk;
        public String strDayDiffer;
        public String sEditor;
        public String strLastUpdateDate;

        public EntQNCmt entQNCmt;
    }

    public class EntQNCmt {
        public String strId;
        public String strCnt;
        public String strD;
        public String pNum;
        public String upFg;
    }

    @Override
    public String getId() {
        return questionAdEntity.strQuestionId;
    }

    @Override
    public String getQuestionTitle() {
        return questionAdEntity.strQuestionTitle;
    }

    @Override
    public String getQuestionContent() {
        return questionAdEntity.strQuestionContent;
    }

    @Override
    public String getAnswerTitle() {
        return questionAdEntity.strAnswerTitle;
    }

    @Override
    public String getAnswerContent() {
        return questionAdEntity.strAnswerContent;
    }

    @Override
    public String getEditor() {
        return questionAdEntity.sEditor;
    }

    @Override
    public String getWebLink() {
        return questionAdEntity.sWebLk;
    }

    @Override
    public String getMakeTime() {
        final String date = questionAdEntity.strQuestionMarketTime;
        return date.length() > 10? date.substring(0, 10): date;
    }
}
