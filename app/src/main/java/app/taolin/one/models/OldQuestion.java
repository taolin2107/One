package app.taolin.one.models;

import java.util.List;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日问题
 */

public class OldQuestion {

    public String res;
    public List<Data> data;

    public class QuestionItem implements BaseQuestion {
        public String res;
        public Data data;

        @Override
        public String getId() {
            return data.question_id;
        }

        @Override
        public String getQuestionTitle() {
            return data.question_title;
        }

        @Override
        public String getQuestionContent() {
            return data.question_content;
        }

        @Override
        public String getAnswerTitle() {
            return data.answer_title;
        }

        @Override
        public String getAnswerContent() {
            return data.answer_content;
        }

        @Override
        public String getEditor() {
            return data.charge_edt;
        }

        @Override
        public String getWebLink() {
            return data.web_url;
        }

        @Override
        public String getMakeTime() {
            final String date = data.question_makettime;
            return date.length() > 10? date.substring(0, 10): date;
        }
    }

    public class Data {
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
    }
}
