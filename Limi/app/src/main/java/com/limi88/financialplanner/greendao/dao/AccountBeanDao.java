package com.limi88.financialplanner.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.limi88.financialplanner.greendao.bean.AccountBean;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "ACCOUNT_BEAN".
*/
public class AccountBeanDao extends AbstractDao<AccountBean, String> {

    public static final String TABLENAME = "ACCOUNT_BEAN";

    /**
     * Properties of entity AccountBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property UserMobile = new Property(0, String.class, "userMobile", true, "USER_MOBILE");
        public final static Property UserToken = new Property(1, String.class, "userToken", false, "USER_TOKEN");
        public final static Property UserAvatar = new Property(2, String.class, "userAvatar", false, "USER_AVATAR");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Authenticated = new Property(4, Boolean.class, "authenticated", false, "AUTHENTICATED");
    };


    public AccountBeanDao(DaoConfig config) {
        super(config);
    }
    
    public AccountBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ACCOUNT_BEAN\" (" + //
                "\"USER_MOBILE\" TEXT PRIMARY KEY NOT NULL ," + // 0: userMobile
                "\"USER_TOKEN\" TEXT," + // 1: userToken
                "\"USER_AVATAR\" TEXT," + // 2: userAvatar
                "\"NAME\" TEXT," + // 3: name
                "\"AUTHENTICATED\" INTEGER);"); // 4: authenticated
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ACCOUNT_BEAN\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, AccountBean entity) {
        stmt.clearBindings();
 
        String userMobile = entity.getUserMobile();
        if (userMobile != null) {
            stmt.bindString(1, userMobile);
        }
 
        String userToken = entity.getUserToken();
        if (userToken != null) {
            stmt.bindString(2, userToken);
        }
 
        String userAvatar = entity.getUserAvatar();
        if (userAvatar != null) {
            stmt.bindString(3, userAvatar);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        Boolean authenticated = entity.getAuthenticated();
        if (authenticated != null) {
            stmt.bindLong(5, authenticated ? 1L: 0L);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public AccountBean readEntity(Cursor cursor, int offset) {
        AccountBean entity = new AccountBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // userMobile
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userToken
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userAvatar
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0 // authenticated
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, AccountBean entity, int offset) {
        entity.setUserMobile(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserToken(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserAvatar(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAuthenticated(cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(AccountBean entity, long rowId) {
        return entity.getUserMobile();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(AccountBean entity) {
        if(entity != null) {
            return entity.getUserMobile();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}