package app.taolin.one.dao;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "HOME".
 */
@Entity
public class Home {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String title;

    @NotNull
    private String imgurl;

    @NotNull
    private String author;

    @NotNull
    private String content;

    @NotNull
    private String makettime;

    @NotNull
    private String updatedate;

    @NotNull
    private String weburl;
    private int praisenum;
    private int sharenum;
    private int commentnum;
    private boolean isloaded;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Home() {
    }

    public Home(String id) {
        this.id = id;
    }

    @Generated
    public Home(String id, String title, String imgurl, String author, String content, String makettime, String updatedate, String weburl, int praisenum, int sharenum, int commentnum, boolean isloaded) {
        this.id = id;
        this.title = title;
        this.imgurl = imgurl;
        this.author = author;
        this.content = content;
        this.makettime = makettime;
        this.updatedate = updatedate;
        this.weburl = weburl;
        this.praisenum = praisenum;
        this.sharenum = sharenum;
        this.commentnum = commentnum;
        this.isloaded = isloaded;
    }

    @NotNull
    public String getId() {
        return id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setId(@NotNull String id) {
        this.id = id;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    @NotNull
    public String getImgurl() {
        return imgurl;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setImgurl(@NotNull String imgurl) {
        this.imgurl = imgurl;
    }

    @NotNull
    public String getAuthor() {
        return author;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAuthor(@NotNull String author) {
        this.author = author;
    }

    @NotNull
    public String getContent() {
        return content;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setContent(@NotNull String content) {
        this.content = content;
    }

    @NotNull
    public String getMakettime() {
        return makettime;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMakettime(@NotNull String makettime) {
        this.makettime = makettime;
    }

    @NotNull
    public String getUpdatedate() {
        return updatedate;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUpdatedate(@NotNull String updatedate) {
        this.updatedate = updatedate;
    }

    @NotNull
    public String getWeburl() {
        return weburl;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setWeburl(@NotNull String weburl) {
        this.weburl = weburl;
    }

    public int getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public int getSharenum() {
        return sharenum;
    }

    public void setSharenum(int sharenum) {
        this.sharenum = sharenum;
    }

    public int getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(int commentnum) {
        this.commentnum = commentnum;
    }

    public boolean getIsloaded() {
        return isloaded;
    }

    public void setIsloaded(boolean isloaded) {
        this.isloaded = isloaded;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
