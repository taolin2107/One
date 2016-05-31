package app.taolin.one.models;

/**
 * Created by Taolin on 16/5/26.
 *
 * 每日图片
 */

public class Home {

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
