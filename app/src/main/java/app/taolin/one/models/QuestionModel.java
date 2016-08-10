package app.taolin.one.models;

import java.util.List;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日问题
 */

public class QuestionModel {

    public String res;
    public List<Data> data;

    public class QuestionItem {
        public String res;
        public Data data;
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
