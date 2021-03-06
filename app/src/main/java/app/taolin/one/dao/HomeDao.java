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
 * DAO for table "HOME".
*/
public class HomeDao extends AbstractDao<Home, String> {

    public static final String TABLENAME = "HOME";

    /**
     * Properties of entity Home.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property ImageUrl = new Property(2, String.class, "imageUrl", false, "IMAGE_URL");
        public final static Property Author = new Property(3, String.class, "author", false, "AUTHOR");
        public final static Property Content = new Property(4, String.class, "content", false, "CONTENT");
        public final static Property MakeTime = new Property(5, String.class, "makeTime", false, "MAKE_TIME");
        public final static Property WebLink = new Property(6, String.class, "webLink", false, "WEB_LINK");
        public final static Property Isloaded = new Property(7, boolean.class, "isloaded", false, "ISLOADED");
    };


    public HomeDao(DaoConfig config) {
        super(config);
    }
    
    public HomeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HOME\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"TITLE\" TEXT NOT NULL ," + // 1: title
                "\"IMAGE_URL\" TEXT NOT NULL ," + // 2: imageUrl
                "\"AUTHOR\" TEXT NOT NULL ," + // 3: author
                "\"CONTENT\" TEXT NOT NULL ," + // 4: content
                "\"MAKE_TIME\" TEXT NOT NULL UNIQUE ," + // 5: makeTime
                "\"WEB_LINK\" TEXT NOT NULL ," + // 6: webLink
                "\"ISLOADED\" INTEGER NOT NULL );"); // 7: isloaded
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HOME\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Home entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getId());
        stmt.bindString(2, entity.getTitle());
        stmt.bindString(3, entity.getImageUrl());
        stmt.bindString(4, entity.getAuthor());
        stmt.bindString(5, entity.getContent());
        stmt.bindString(6, entity.getMakeTime());
        stmt.bindString(7, entity.getWebLink());
        stmt.bindLong(8, entity.getIsloaded() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Home entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getId());
        stmt.bindString(2, entity.getTitle());
        stmt.bindString(3, entity.getImageUrl());
        stmt.bindString(4, entity.getAuthor());
        stmt.bindString(5, entity.getContent());
        stmt.bindString(6, entity.getMakeTime());
        stmt.bindString(7, entity.getWebLink());
        stmt.bindLong(8, entity.getIsloaded() ? 1L: 0L);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    @Override
    public Home readEntity(Cursor cursor, int offset) {
        Home entity = new Home( //
            cursor.getString(offset + 0), // id
            cursor.getString(offset + 1), // title
            cursor.getString(offset + 2), // imageUrl
            cursor.getString(offset + 3), // author
            cursor.getString(offset + 4), // content
            cursor.getString(offset + 5), // makeTime
            cursor.getString(offset + 6), // webLink
            cursor.getShort(offset + 7) != 0 // isloaded
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Home entity, int offset) {
        entity.setId(cursor.getString(offset + 0));
        entity.setTitle(cursor.getString(offset + 1));
        entity.setImageUrl(cursor.getString(offset + 2));
        entity.setAuthor(cursor.getString(offset + 3));
        entity.setContent(cursor.getString(offset + 4));
        entity.setMakeTime(cursor.getString(offset + 5));
        entity.setWebLink(cursor.getString(offset + 6));
        entity.setIsloaded(cursor.getShort(offset + 7) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(Home entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(Home entity) {
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
