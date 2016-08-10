package app.taolin.one.models;

import java.util.List;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日图片
 */

public class HomeModel {

    public String res;

    public List<Data> data;

    public class Data {
        public String hpcontent_id;
        public String hp_title;
        public String author_id;
        public String hp_img_url;
        public String hp_img_original_url;
        public String hp_author;
        public String ipad_url;
        public String hp_content;
        public String hp_makettime;
        public String last_update_date;
        public String web_url;
        public String wb_img_url;
        public int praisenum;
        public int sharenum;
        public int commentnum;
    }
}
