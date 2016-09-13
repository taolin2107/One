package app.taolin.one;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class DatabaseGenerator {

    public static void main(String[] args) {
        Schema schema = new Schema(2, "app.taolin.one.dao");
        addHome(schema);
        addArticle(schema);
        addQuestion(schema);
        try {
            new DaoGenerator().generateAll(schema, "app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addHome(final Schema schema) {
        Entity listItem = schema.addEntity("Home");
        listItem.setHasKeepSections(true);
        listItem.addStringProperty("id").primaryKey().notNull();
        listItem.addStringProperty("title").notNull();
        listItem.addStringProperty("imageUrl").notNull();
        listItem.addStringProperty("author").notNull();
        listItem.addStringProperty("content").notNull();
        listItem.addStringProperty("makeTime").unique().notNull();
        listItem.addStringProperty("webLink").notNull();
        listItem.addBooleanProperty("isloaded").notNull();
    }

    private static void addArticle(final Schema schema) {
        Entity listItem = schema.addEntity("Article");
        listItem.setHasKeepSections(true);
        listItem.addStringProperty("id").primaryKey().notNull();
        listItem.addStringProperty("title").notNull();
        listItem.addStringProperty("subTitle");
        listItem.addStringProperty("author");
        listItem.addStringProperty("authorDesc");
        listItem.addStringProperty("weibo");
        listItem.addStringProperty("authorIntro");
        listItem.addStringProperty("content");
        listItem.addStringProperty("webLink");
        listItem.addStringProperty("makeTime").unique().notNull();
        listItem.addStringProperty("guideWord").notNull();
        listItem.addBooleanProperty("isloaded").notNull();
    }

    private static void addQuestion(final Schema schema) {
        Entity listItem = schema.addEntity("Question");
        listItem.setHasKeepSections(true);
        listItem.addStringProperty("id").primaryKey().notNull();
        listItem.addStringProperty("questionTitle").notNull();
        listItem.addStringProperty("questionContent");
        listItem.addStringProperty("answerTitle").notNull();
        listItem.addStringProperty("answerContent").notNull();
        listItem.addStringProperty("editor");
        listItem.addStringProperty("webLink");
        listItem.addStringProperty("makeTime").unique().notNull();
        listItem.addBooleanProperty("isloaded");
    }
}
