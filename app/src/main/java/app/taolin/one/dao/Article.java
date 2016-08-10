package app.taolin.one.dao;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "ARTICLE".
 */
@Entity
public class Article {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String title;
    private String subtitle;
    private String author;
    private String authit;
    private String authorintro;
    private String content;

    @NotNull
    private String makettime;
    private String updatedate;
    private String weburl;

    @NotNull
    private String guideword;
    private String audio;
    private Integer praisenum;
    private Integer sharenum;
    private Integer commentnum;
    private boolean isloaded;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Article() {
    }

    public Article(String id) {
        this.id = id;
    }

    @Generated
    public Article(String id, String title, String subtitle, String author, String authit, String authorintro, String content, String makettime, String updatedate, String weburl, String guideword, String audio, Integer praisenum, Integer sharenum, Integer commentnum, boolean isloaded) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.authit = authit;
        this.authorintro = authorintro;
        this.content = content;
        this.makettime = makettime;
        this.updatedate = updatedate;
        this.weburl = weburl;
        this.guideword = guideword;
        this.audio = audio;
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthit() {
        return authit;
    }

    public void setAuthit(String authit) {
        this.authit = authit;
    }

    public String getAuthorintro() {
        return authorintro;
    }

    public void setAuthorintro(String authorintro) {
        this.authorintro = authorintro;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    @NotNull
    public String getGuideword() {
        return guideword;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setGuideword(@NotNull String guideword) {
        this.guideword = guideword;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Integer getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(Integer praisenum) {
        this.praisenum = praisenum;
    }

    public Integer getSharenum() {
        return sharenum;
    }

    public void setSharenum(Integer sharenum) {
        this.sharenum = sharenum;
    }

    public Integer getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Integer commentnum) {
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