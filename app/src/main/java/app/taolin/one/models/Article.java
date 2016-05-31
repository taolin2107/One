package app.taolin.one.models;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日文章
 */

public class Article {

    public String result;

    public ContentEntity contentEntity;

    public class ContentEntity {
        public String strContentId;
        public String strContent;
        public String sWebLk;
        public String wImgUrl;
        public String sRdNum;
        public String strPraiseNumber;
        public String strContDayDiffer;
        public String strContTitle;
        public String strContAuthor;
        public String strContAuthorIntroduce;
        public String strContMarketTime;
        public String sGW;
        public String sAuth;
        public String sWbN;
        public String subTitle;
        public String strLastUpdateDate;
    }
}
