package app.taolin.one.models;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日问题
 */

public class Question {

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
}
