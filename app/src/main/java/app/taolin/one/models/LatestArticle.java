package app.taolin.one.models;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日文章
 */

public class LatestArticle implements BaseArticle {

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

    @Override
    public String getId() {
        return contentEntity.strContentId;
    }

    @Override
    public String getTitle() {
        return contentEntity.strContTitle;
    }

    @Override
    public String getSubTitle() {
        return contentEntity.subTitle;
    }

    @Override
    public String getAuthor() {
        return contentEntity.strContAuthor;
    }

    @Override
    public String getAuthorDesc() {
        return contentEntity.sAuth;
    }

    @Override
    public String getWeibo() {
        return contentEntity.sWbN;
    }

    @Override
    public String getAuthorIntro() {
        return contentEntity.strContAuthorIntroduce;
    }

    @Override
    public String getContent() {
        return contentEntity.strContent;
    }

    @Override
    public String getWebLink() {
        return contentEntity.sWebLk;
    }

    @Override
    public String getGuideWord() {
        return contentEntity.sGW;
    }

    @Override
    public String getMakeTime() {
        final String date = contentEntity.strContMarketTime;
        return date.length() > 10? date.substring(0, 10): date;
    }
}
