package app.taolin.one.models;

import java.util.List;

import app.taolin.one.utils.DateUtil;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日图片
 */

public class OldHome {

    public String res;

    public List<Data> data;

    public class Data implements BaseHome {
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

        @Override
        public String getId() {
            return hpcontent_id;
        }

        @Override
        public String getTitle() {
            return hp_title;
        }

        @Override
        public String getImageUrl() {
            return hp_img_original_url;
        }

        @Override
        public String getAuthor() {
            return hp_author.replace("&", "\n");
        }

        @Override
        public String getContent() {
            return hp_content.trim();
        }

        @Override
        public String getWebLink() {
            return web_url;
        }

        @Override
        public String getMakeTime() {
            final String date = hp_makettime;
            return DateUtil.getOldFormatDate(date);
        }
    }
}
