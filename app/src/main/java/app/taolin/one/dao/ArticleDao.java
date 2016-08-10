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
 * DAO for table "ARTICLE".
*/
public class ArticleDao extends AbstractDao<Article, String> {

    public static final String TABLENAME = "ARTICLE";

    /**
     * Properties of entity Article.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Subtitle = new Property(2, String.class, "subtitle", false, "SUBTITLE");
        public final static Property Author = new Property(3, String.class, "author", false, "AUTHOR");
        public final static Property Authit = new Property(4, String.class, "authit", false, "AUTHIT");
        public final static Property Authorintro = new Property(5, String.class, "authorintro", false, "AUTHORINTRO");
        public final static Property Content = new Property(6, String.class, "content", false, "CONTENT");
        public final static Property Makettime = new Property(7, String.class, "makettime", false, "MAKETTIME");
        public final static Property Updatedate = new Property(8, String.class, "updatedate", false, "UPDATEDATE");
        public final static Property Weburl = new Property(9, String.class, "weburl", false, "WEBURL");
        public final static Property Guideword = new Property(10, String.class, "guideword", false, "GUIDEWORD");
        public final static Property Audio = new Property(11, String.class, "audio", false, "AUDIO");
        public final static Property Praisenum = new Property(12, Integer.class, "praisenum", false, "PRAISENUM");
        public final static Property Sharenum = new Property(13, Integer.class, "sharenum", false, "SHARENUM");
        public final static Property Commentnum = new Property(14, Integer.class, "commentnum", false, "COMMENTNUM");
        public final static Property Isloaded = new Property(15, boolean.class, "isloaded", false, "ISLOADED");
    };


    public ArticleDao(DaoConfig config) {
        super(config);
    }
    
    public ArticleDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ARTICLE\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"TITLE\" TEXT NOT NULL ," + // 1: title
                "\"SUBTITLE\" TEXT," + // 2: subtitle
                "\"AUTHOR\" TEXT," + // 3: author
                "\"AUTHIT\" TEXT," + // 4: authit
                "\"AUTHORINTRO\" TEXT," + // 5: authorintro
                "\"CONTENT\" TEXT," + // 6: content
                "\"MAKETTIME\" TEXT NOT NULL ," + // 7: makettime
                "\"UPDATEDATE\" TEXT," + // 8: updatedate
                "\"WEBURL\" TEXT," + // 9: weburl
                "\"GUIDEWORD\" TEXT NOT NULL ," + // 10: guideword
                "\"AUDIO\" TEXT," + // 11: audio
                "\"PRAISENUM\" INTEGER," + // 12: praisenum
                "\"SHARENUM\" INTEGER," + // 13: sharenum
                "\"COMMENTNUM\" INTEGER," + // 14: commentnum
                "\"ISLOADED\" INTEGER NOT NULL );"); // 15: isloaded
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ARTICLE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Article entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getId());
        stmt.bindString(2, entity.getTitle());
 
        String subtitle = entity.getSubtitle();
        if (subtitle != null) {
            stmt.bindString(3, subtitle);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(4, author);
        }
 
        String authit = entity.getAuthit();
        if (authit != null) {
            stmt.bindString(5, authit);
        }
 
        String authorintro = entity.getAuthorintro();
        if (authorintro != null) {
            stmt.bindString(6, authorintro);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
        stmt.bindString(8, entity.getMakettime());
 
        String updatedate = entity.getUpdatedate();
        if (updatedate != null) {
            stmt.bindString(9, updatedate);
        }
 
        String weburl = entity.getWeburl();
        if (weburl != null) {
            stmt.bindString(10, weburl);
        }
        stmt.bindString(11, entity.getGuideword());
 
        String audio = entity.getAudio();
        if (audio != null) {
            stmt.bindString(12, audio);
        }
 
        Integer praisenum = entity.getPraisenum();
        if (praisenum != null) {
            stmt.bindLong(13, praisenum);
        }
 
        Integer sharenum = entity.getSharenum();
        if (sharenum != null) {
            stmt.bindLong(14, sharenum);
        }
 
        Integer commentnum = entity.getCommentnum();
        if (commentnum != null) {
            stmt.bindLong(15, commentnum);
        }
        stmt.bindLong(16, entity.getIsloaded() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Article entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getId());
        stmt.bindString(2, entity.getTitle());
 
        String subtitle = entity.getSubtitle();
        if (subtitle != null) {
            stmt.bindString(3, subtitle);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(4, author);
        }
 
        String authit = entity.getAuthit();
        if (authit != null) {
            stmt.bindString(5, authit);
        }
 
        String authorintro = entity.getAuthorintro();
        if (authorintro != null) {
            stmt.bindString(6, authorintro);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
        stmt.bindString(8, entity.getMakettime());
 
        String updatedate = entity.getUpdatedate();
        if (updatedate != null) {
            stmt.bindString(9, updatedate);
        }
 
        String weburl = entity.getWeburl();
        if (weburl != null) {
            stmt.bindString(10, weburl);
        }
        stmt.bindString(11, entity.getGuideword());
 
        String audio = entity.getAudio();
        if (audio != null) {
            stmt.bindString(12, audio);
        }
 
        Integer praisenum = entity.getPraisenum();
        if (praisenum != null) {
            stmt.bindLong(13, praisenum);
        }
 
        Integer sharenum = entity.getSharenum();
        if (sharenum != null) {
            stmt.bindLong(14, sharenum);
        }
 
        Integer commentnum = entity.getCommentnum();
        if (commentnum != null) {
            stmt.bindLong(15, commentnum);
        }
        stmt.bindLong(16, entity.getIsloaded() ? 1L: 0L);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    @Override
    public Article readEntity(Cursor cursor, int offset) {
        Article entity = new Article( //
            cursor.getString(offset + 0), // id
            cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // subtitle
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // author
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // authit
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // authorintro
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // content
            cursor.getString(offset + 7), // makettime
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // updatedate
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // weburl
            cursor.getString(offset + 10), // guideword
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // audio
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12), // praisenum
            cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // sharenum
            cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14), // commentnum
            cursor.getShort(offset + 15) != 0 // isloaded
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Article entity, int offset) {
        entity.setId(cursor.getString(offset + 0));
        entity.setTitle(cursor.getString(offset + 1));
        entity.setSubtitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAuthor(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAuthit(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAuthorintro(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setContent(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMakettime(cursor.getString(offset + 7));
        entity.setUpdatedate(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setWeburl(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setGuideword(cursor.getString(offset + 10));
        entity.setAudio(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setPraisenum(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
        entity.setSharenum(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setCommentnum(cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14));
        entity.setIsloaded(cursor.getShort(offset + 15) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(Article entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(Article entity) {
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
