package cn.zql.vgbill.Tools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE =  "bills";
    public static final String ID =  "_id";
    public static final String CUSTOMERNAME =  "customername";
    public static final String CUSTOMERPHONE =  "customerphone";
    public static final String CUSTOMERADDRESS =  "customeraddress";
    public static final String SIZE =  "size";
    public static final String BUYDATE =  "buydate";
    public static final String MONEY =  "money";
    public static final String MAKER =  "maker";
    public static final String MAKERDATE =  "makerdate";
    public static final String PAYMENT =  "payment";
    public static final String SERVICE =  "service";

    public DBHelper(Context context) {
        super(context,"bill.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + "( "+ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CUSTOMERNAME +" VARCHAR(30) , " +
                CUSTOMERPHONE + " VARCHAR(30) , " +
                CUSTOMERADDRESS + " TEXT , " +
                BUYDATE + " TEXT , " +
                SIZE + " TEXT , " +
                MONEY + " VARCHAR(30) , " +
                MAKER + " VARCHAR(30) , " +
                MAKERDATE + " TEXT , " +
                PAYMENT + " TEXT , " +
                SERVICE + " TEXT )";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Cursor findByName(Context context, String name) {
        SQLiteDatabase db = new DBHelper(context).getReadableDatabase();
        return db.rawQuery("select * from " + DBHelper.TABLE + " where customername =?", new String[]{name + ""});
    }
}
