package app.taolin.one.models;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 22, 2016.
 * @description
 */

public class MoreListModel {

    public String id;
    public String title;
    public String author;
    public String contentDesc;
    public String makeTime;
    public String imgUrl;

    public MoreListModel(String id, String title, String contentDesc) {
        this.id = id;
        this.title = title;
        this.contentDesc = contentDesc;
    }

    public MoreListModel(String id, String title, String author, String contentDesc) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.contentDesc = contentDesc;
    }

    public MoreListModel(String id, String title, String contentDesc, String makeTime, String imgUrl) {
        this.id = id;
        this.title = title;
        this.contentDesc = contentDesc;
        this.makeTime = makeTime;
        this.imgUrl = imgUrl;
    }
}
