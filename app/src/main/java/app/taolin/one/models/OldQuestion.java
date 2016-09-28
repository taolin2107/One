package app.taolin.one.models;

import java.util.List;

import app.taolin.one.utils.DateUtil;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日问题
 */

public class OldQuestion {

    public String res;
    public List<Data> data;

    public class QuestionItem {
        public String res;
        public Data data;
    }

    public class Data implements BaseQuestion {
        public String question_id;
        public String question_title;
        public String question_content;
        public String answer_title;
        public String answer_content;
        public String question_makettime;
        public String recommend_flag;
        public String charge_edt;
        public String last_update_date;
        public String web_url;
        public int read_num;
        public String guide_word;
        public int praisenum;
        public int sharenum;
        public int commentnum;

        @Override
        public String getId() {
            return question_id;
        }

        @Override
        public String getQuestionTitle() {
            return question_title;
        }

        @Override
        public String getQuestionContent() {
            return question_content;
        }

        @Override
        public String getAnswerTitle() {
            return answer_title;
        }

        @Override
        public String getAnswerContent() {
            return answer_content;
        }

        @Override
        public String getEditor() {
            return charge_edt;
        }

        @Override
        public String getWebLink() {
            return web_url;
        }

        @Override
        public String getGuideWord() {
            return guide_word;
        }

        @Override
        public String getMakeTime() {
            final String date = question_makettime;
            return DateUtil.getOldFormatDate(date);
        }
    }
}
