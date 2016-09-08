package app.taolin.one.models;

import java.util.List;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日文章
 */

public class OldArticle {

    public String res;
    public List<Data> data;

    public class ArticleItem implements BaseArticle {
        public String res;
        public Data data;

        @Override
        public String getId() {
            return data.content_id;
        }

        @Override
        public String getTitle() {
            return data.hp_title;
        }

        @Override
        public String getSubTitle() {
            return data.sub_title;
        }

        @Override
        public String getAuthor() {
            return data.hp_author;
        }

        @Override
        public String getAuthorDesc() {
            return data.auth_it;
        }

        @Override
        public String getWeibo() {
            return data.author.get(0).wb_name;
        }

        @Override
        public String getAuthorIntro() {
            return data.hp_author_introduce;
        }

        @Override
        public String getContent() {
            return data.hp_content;
        }

        @Override
        public String getWebLink() {
            return data.web_url;
        }

        @Override
        public String getGuideWord() {
            return data.guide_word;
        }

        @Override
        public String getMakeTime() {
            final String date = data.hp_makettime;
            return date.length() > 10? date.substring(0, 10): date;
        }
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
