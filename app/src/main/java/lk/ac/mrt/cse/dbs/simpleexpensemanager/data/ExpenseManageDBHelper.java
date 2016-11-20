package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.ExpenseManagerSchema.AccountTable;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.ExpenseManagerSchema.TransactionTable;

/**
 * Created by arunan on 11/16/16.
 */

public class ExpenseManageDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "140045E.db";

    public ExpenseManageDBHelper(Context context){
        super(context,DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("create table " + TransactionTable.NAME + "(" +

                TransactionTable.Cols.DATE + ", " +
                TransactionTable.Cols.ACCOUNT_NO + " primary key, " + ", " +
                TransactionTable.Cols.TYPE + ", " +
                TransactionTable.Cols.AMOUNT +

                ")"

        );


        db.execSQL("create table " + AccountTable.NAME + "(" +

                AccountTable.Cols.ACCOUNT_NO + " primary key, " +
                AccountTable.Cols.BANK_NAME + ", " +
                AccountTable.Cols.HOLDER + ", " +
                AccountTable.Cols.BALANCE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

