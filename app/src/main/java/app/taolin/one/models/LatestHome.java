package app.taolin.one.models;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日图片
 */

public class LatestHome implements BaseHome {

    public String result;

    public HpEntity hpEntity;

    public class HpEntity {
        public String strHpId;
        public String strHpTitle;
        public String strThumbnailUrl;
        public String strOriginalImgUrl;
        public String strAuthor;
        public String strContent;
        public String sWebLk;
        public String strPn;
        public String wImgUrl;
        public String strMarketTime;
        public String strLastUpdateDate;
        public String strDayDiffer;
    }

    @Override
    public String getId() {
        return hpEntity.strHpId;
    }

    @Override
    public String getTitle() {
        return hpEntity.strHpTitle;
    }

    @Override
    public String getImageUrl() {
        return hpEntity.strOriginalImgUrl;
    }

    @Override
    public String getAuthor() {
        return hpEntity.strAuthor.replace("&", "\n");
    }

    @Override
    public String getContent() {
        return hpEntity.strContent;
    }

    @Override
    public String getWebLink() {
        return hpEntity.sWebLk;
    }

    @Override
    public String getMakeTime() {
        final String date = hpEntity.strMarketTime;
        return date.length() > 10? date.substring(0, 10): date;
    }

    @Override
    public String toString() {
        return "{result: " + result + "; hpEntity: {strHpId: " + hpEntity.strHpId
                + ", strHpTitle: " + hpEntity.strHpTitle
                + ", strThumbnailUre: " + hpEntity.strThumbnailUrl
                + ", strOriginalImgUrl: " + hpEntity.strOriginalImgUrl
                + ", strAuthor: " + hpEntity.strAuthor
                + ", strContent: " + hpEntity.strContent
                + ", sWebLk: " + hpEntity.sWebLk
                + ", strPn: " + hpEntity.strPn
                + ", wImgUrl: " + hpEntity.wImgUrl
                + ", strMarketTime: " + hpEntity.strMarketTime
                + ", strLastUpdateDate: " + hpEntity.strLastUpdateDate
                + ", strDayDiffer: " + hpEntity.strDayDiffer
                + " } }";
    }
}
