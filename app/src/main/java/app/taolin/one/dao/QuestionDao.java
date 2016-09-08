package app.taolin.one.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "QUESTION".
*/
public class QuestionDao extends AbstractDao<Question, String> {

    public static final String TABLENAME = "QUESTION";

    /**
     * Properties of entity Question.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property QuestionTitle = new Property(1, String.class, "questionTitle", false, "QUESTION_TITLE");
        public final static Property QuestionContent = new Property(2, String.class, "questionContent", false, "QUESTION_CONTENT");
        public final static Property AnswerTitle = new Property(3, String.class, "answerTitle", false, "ANSWER_TITLE");
        public final static Property AnswerContent = new Property(4, String.class, "answerContent", false, "ANSWER_CONTENT");
        public final static Property Editor = new Property(5, String.class, "editor", false, "EDITOR");
        public final static Property WebLink = new Property(6, String.class, "webLink", false, "WEB_LINK");
        public final static Property MakeTime = new Property(7, String.class, "makeTime", false, "MAKE_TIME");
        public final static Property Isloaded = new Property(8, Boolean.class, "isloaded", false, "ISLOADED");
    };


    public QuestionDao(DaoConfig config) {
        super(config);
    }
    
    public QuestionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"QUESTION\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"QUESTION_TITLE\" TEXT NOT NULL ," + // 1: questionTitle
                "\"QUESTION_CONTENT\" TEXT," + // 2: questionContent
                "\"ANSWER_TITLE\" TEXT NOT NULL ," + // 3: answerTitle
                "\"ANSWER_CONTENT\" TEXT NOT NULL ," + // 4: answerContent
                "\"EDITOR\" TEXT," + // 5: editor
                "\"WEB_LINK\" TEXT," + // 6: webLink
                "\"MAKE_TIME\" TEXT NOT NULL ," + // 7: makeTime
                "\"ISLOADED\" INTEGER);"); // 8: isloaded
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"QUESTION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Question entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getId());
        stmt.bindString(2, entity.getQuestionTitle());
 
        String questionContent = entity.getQuestionContent();
        if (questionContent != null) {
            stmt.bindString(3, questionContent);
        }
        stmt.bindString(4, entity.getAnswerTitle());
        stmt.bindString(5, entity.getAnswerContent());
 
        String editor = entity.getEditor();
        if (editor != null) {
            stmt.bindString(6, editor);
        }
 
        String webLink = entity.getWebLink();
        if (webLink != null) {
            stmt.bindString(7, webLink);
        }
        stmt.bindString(8, entity.getMakeTime());
 
        Boolean isloaded = entity.getIsloaded();
        if (isloaded != null) {
            stmt.bindLong(9, isloaded ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Question entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getId());
        stmt.bindString(2, entity.getQuestionTitle());
 
        String questionContent = entity.getQuestionContent();
        if (questionContent != null) {
            stmt.bindString(3, questionContent);
        }
        stmt.bindString(4, entity.getAnswerTitle());
        stmt.bindString(5, entity.getAnswerContent());
 
        String editor = entity.getEditor();
        if (editor != null) {
            stmt.bindString(6, editor);
        }
 
        String webLink = entity.getWebLink();
        if (webLink != null) {
            stmt.bindString(7, webLink);
        }
        stmt.bindString(8, entity.getMakeTime());
 
        Boolean isloaded = entity.getIsloaded();
        if (isloaded != null) {
            stmt.bindLong(9, isloaded ? 1L: 0L);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    @Override
    public Question readEntity(Cursor cursor, int offset) {
        Question entity = new Question( //
            cursor.getString(offset + 0), // id
            cursor.getString(offset + 1), // questionTitle
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // questionContent
            cursor.getString(offset + 3), // answerTitle
            cursor.getString(offset + 4), // answerContent
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // editor
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // webLink
            cursor.getString(offset + 7), // makeTime
            cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0 // isloaded
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Question entity, int offset) {
        entity.setId(cursor.getString(offset + 0));
        entity.setQuestionTitle(cursor.getString(offset + 1));
        entity.setQuestionContent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAnswerTitle(cursor.getString(offset + 3));
        entity.setAnswerContent(cursor.getString(offset + 4));
        entity.setEditor(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setWebLink(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMakeTime(cursor.getString(offset + 7));
        entity.setIsloaded(cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(Question entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(Question entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
