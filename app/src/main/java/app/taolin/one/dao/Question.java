package app.taolin.one.dao;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "QUESTION".
 */
@Entity
public class Question {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String questiontitle;
    private String questioncontent;

    @NotNull
    private String answertitle;

    @NotNull
    private String answercontent;

    @NotNull
    private String makettime;
    private String editor;
    private String updatedate;
    private String weburl;
    private Integer readnum;
    private String guideword;
    private Integer praisenum;
    private Integer sharenum;
    private Integer commentnum;
    private Boolean isloaded;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Question() {
    }

    public Question(String id) {
        this.id = id;
    }

    @Generated
    public Question(String id, String questiontitle, String questioncontent, String answertitle, String answercontent, String makettime, String editor, String updatedate, String weburl, Integer readnum, String guideword, Integer praisenum, Integer sharenum, Integer commentnum, Boolean isloaded) {
        this.id = id;
        this.questiontitle = questiontitle;
        this.questioncontent = questioncontent;
        this.answertitle = answertitle;
        this.answercontent = answercontent;
        this.makettime = makettime;
        this.editor = editor;
        this.updatedate = updatedate;
        this.weburl = weburl;
        this.readnum = readnum;
        this.guideword = guideword;
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
    public String getQuestiontitle() {
        return questiontitle;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setQuestiontitle(@NotNull String questiontitle) {
        this.questiontitle = questiontitle;
    }

    public String getQuestioncontent() {
        return questioncontent;
    }

    public void setQuestioncontent(String questioncontent) {
        this.questioncontent = questioncontent;
    }

    @NotNull
    public String getAnswertitle() {
        return answertitle;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAnswertitle(@NotNull String answertitle) {
        this.answertitle = answertitle;
    }

    @NotNull
    public String getAnswercontent() {
        return answercontent;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAnswercontent(@NotNull String answercontent) {
        this.answercontent = answercontent;
    }

    @NotNull
    public String getMakettime() {
        return makettime;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMakettime(@NotNull String makettime) {
        this.makettime = makettime;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
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

    public Integer getReadnum() {
        return readnum;
    }

    public void setReadnum(Integer readnum) {
        this.readnum = readnum;
    }

    public String getGuideword() {
        return guideword;
    }

    public void setGuideword(String guideword) {
        this.guideword = guideword;
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

    public Boolean getIsloaded() {
        return isloaded;
    }

    public void setIsloaded(Boolean isloaded) {
        this.isloaded = isloaded;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}