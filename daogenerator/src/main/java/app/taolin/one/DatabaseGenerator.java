package app.taolin.one;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class DatabaseGenerator {

    public static void main(String[] args) {
        Schema schema = new Schema(1, "app.taolin.one.dao");
        addHome(schema);
        addQuestion(schema);
        addArticle(schema);
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
        listItem.addStringProperty("imgurl").notNull();
        listItem.addStringProperty("author").notNull();
        listItem.addStringProperty("content").notNull();
        listItem.addStringProperty("makettime").notNull();
        listItem.addStringProperty("updatedate").notNull();
        listItem.addStringProperty("weburl").notNull();
        listItem.addIntProperty("praisenum").notNull();
        listItem.addIntProperty("sharenum").notNull();
        listItem.addIntProperty("commentnum").notNull();
        listItem.addBooleanProperty("isloaded").notNull();
    }

    private static void addQuestion(final Schema schema) {
        Entity listItem = schema.addEntity("Question");
        listItem.setHasKeepSections(true);
        listItem.addStringProperty("id").primaryKey().notNull();
        listItem.addStringProperty("questiontitle").notNull();
        listItem.addStringProperty("questioncontent").notNull();
        listItem.addStringProperty("answertitle").notNull();
        listItem.addStringProperty("answercontent").notNull();
        listItem.addStringProperty("makettime").notNull();
        listItem.addStringProperty("editor").notNull();
        listItem.addStringProperty("updatedate").notNull();
        listItem.addStringProperty("weburl").notNull();
        listItem.addIntProperty("readnum").notNull();
        listItem.addStringProperty("guideword").notNull();
        listItem.addIntProperty("praisenum").notNull();
        listItem.addIntProperty("sharenum").notNull();
        listItem.addIntProperty("commentnum").notNull();
        listItem.addBooleanProperty("isloaded").notNull();
    }

    private static void addArticle(final Schema schema) {
        Entity listItem = schema.addEntity("Article");
        listItem.setHasKeepSections(true);
        listItem.addStringProperty("id").primaryKey().notNull();
        listItem.addStringProperty("title").notNull();
        listItem.addStringProperty("subtitle").notNull();
        listItem.addStringProperty("author").notNull();
        listItem.addStringProperty("authit").notNull();
        listItem.addStringProperty("authorintro").notNull();
        listItem.addStringProperty("content").notNull();
        listItem.addStringProperty("makettime").notNull();
        listItem.addStringProperty("updatedate").notNull();
        listItem.addStringProperty("weburl").notNull();
        listItem.addStringProperty("guideword").notNull();
        listItem.addStringProperty("audio").notNull();
        listItem.addIntProperty("praisenum").notNull();
        listItem.addIntProperty("sharenum").notNull();
        listItem.addIntProperty("commentnum").notNull();
        listItem.addBooleanProperty("isloaded").notNull();
    }
}
