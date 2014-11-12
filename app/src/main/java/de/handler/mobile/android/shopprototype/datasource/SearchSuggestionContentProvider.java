package de.handler.mobile.android.shopprototype.datasource;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import de.greenrobot.dao.DaoLog;
import de.handler.mobile.android.shopprototype.datasource.database.DaoSession;
import de.handler.mobile.android.shopprototype.datasource.database.SearchSuggestionDao;


public class SearchSuggestionContentProvider extends ContentProvider {

    public static final String AUTHORITY = "de.handler.mobile.android.shopprototype.datasource.database.searchprovider";
    public static final String BASE_PATH = "";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + BASE_PATH;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + BASE_PATH;

    private static final String TABLENAME = SearchSuggestionDao.TABLENAME;
    private static final String PK = SearchSuggestionDao.Properties.Id
            .columnName;

    private static final int SEARCHSUGGESTION_DIR = 0;
    private static final int SEARCHSUGGESTION_ID = 1;
    private static final int SEARCHSUGGESTION_QUERY = 2;

    private static final UriMatcher sURIMatcher;

    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, SEARCHSUGGESTION_DIR);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", SEARCHSUGGESTION_ID);
        sURIMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCHSUGGESTION_QUERY);
        sURIMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCHSUGGESTION_QUERY);
    }

    /**
     * This must be set from outside, it's recommended to do this inside your Application object.
     * Subject to change (static isn't nice).
     */
    public static DaoSession daoSession;

    @Override
    public boolean onCreate() {
        // if(daoSession == null) {
        // throw new IllegalStateException("DaoSession must be set before content provider is created");
        // }
        DaoLog.d("Content Provider started: " + CONTENT_URI);
        return true;
    }

    protected SQLiteDatabase getDatabase() {
        if(daoSession == null) {
            throw new IllegalStateException("DaoSession must be set during content provider is active");
        }
        return daoSession.getDatabase();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        long id = 0;
        String path = "";
        switch (uriType) {
            case SEARCHSUGGESTION_DIR:
                id = getDatabase().insert(TABLENAME, null, values);
                path = BASE_PATH + "/" + id;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(path);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = getDatabase();
        int rowsDeleted = 0;
        String id;
        switch (uriType) {
            case SEARCHSUGGESTION_DIR:
                rowsDeleted = db.delete(TABLENAME, selection, selectionArgs);
                break;
            case SEARCHSUGGESTION_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(TABLENAME, PK + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(TABLENAME, PK + "=" + id + " and "
                            + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = getDatabase();
        int rowsUpdated = 0;
        String id;
        switch (uriType) {
            case SEARCHSUGGESTION_DIR:
                rowsUpdated = db.update(TABLENAME, values, selection, selectionArgs);
                break;
            case SEARCHSUGGESTION_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(TABLENAME, values, PK + "=" + id, null);
                } else {
                    rowsUpdated = db.update(TABLENAME, values, PK + "=" + id
                            + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case SEARCHSUGGESTION_DIR:
                queryBuilder.setTables(TABLENAME);
                break;
            case SEARCHSUGGESTION_ID:
                queryBuilder.setTables(TABLENAME);
                queryBuilder.appendWhere(PK + "="
                        + uri.getLastPathSegment());
                break;
            case SEARCHSUGGESTION_QUERY:
                queryBuilder.setTables(TABLENAME);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = getDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public final String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case SEARCHSUGGESTION_DIR:
                return CONTENT_TYPE;
            case SEARCHSUGGESTION_ID:
                return CONTENT_ITEM_TYPE;
            default :
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
