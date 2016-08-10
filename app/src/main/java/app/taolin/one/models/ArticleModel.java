package app.taolin.one.models;

import java.util.List;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日文章
 */

public class ArticleModel {

    public String res;
    public List<Data> data;

    public class ArticleItem {
        public String res;
        public Data data;
    }

    public class Data {
        public String content_id;
        public String hp_title;
        public String sub_title;
        public String hp_author;
        public String auth_it;
        public String hp_author_introduce;
        public String hp_content;
        public String hp_makettime;
        public String wb_name;
        public String wb_img_url;
        public String last_update_date;
        public String web_url;
        public String guide_word;
        public String audio;
        public List<Author> author;
        public int praisenum;
        public int sharenum;
        public int commentnum;
    }

    public class Author {
        public String user_id;
        public String user_name;
        public String web_url;
        public String desc;
        public String wb_name;
    }
}
