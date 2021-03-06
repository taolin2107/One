package app.taolin.one.models;

import java.util.List;

import app.taolin.one.utils.DateUtil;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日文章
 */

public class OldArticle {

    public String res;
    public List<Data> data;

    public class ArticleItem {
        public String res;
        public Data data;
    }

    public class Data implements BaseArticle {
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

        @Override
        public String getId() {
            return content_id;
        }

        @Override
        public String getTitle() {
            return hp_title;
        }

        @Override
        public String getSubTitle() {
            return sub_title;
        }

        @Override
        public String getAuthor() {
            return author.get(0).user_name;
        }

        @Override
        public String getAuthorDesc() {
            return auth_it;
        }

        @Override
        public String getWeibo() {
            return author.get(0).wb_name;
        }

        @Override
        public String getAuthorIntro() {
            return hp_author_introduce;
        }

        @Override
        public String getContent() {
            return hp_content;
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
            final String date = hp_makettime;
            return DateUtil.getOldFormatDate(date);
        }
    }

    public class Author {
        public String user_id;
        public String user_name;
        public String web_url;
        public String desc;
        public String wb_name;
    }
}
