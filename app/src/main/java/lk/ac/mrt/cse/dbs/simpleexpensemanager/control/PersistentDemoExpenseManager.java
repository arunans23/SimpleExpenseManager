package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.ExpenseManagerSchema;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentMemoryTransactionDAO;

/**
 * Created by arunan on 11/16/16.
 */

public class PersistentDemoExpenseManager extends ExpenseManager {
    Context context;
    public PersistentDemoExpenseManager(Context context){
        this.context = context;
        setup();
    }

    @Override
    public void setup() {
        SQLiteDatabase mDatabase = context.openOrCreateDatabase("140045e", context.MODE_PRIVATE, null);

        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + ExpenseManagerSchema.TransactionTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ExpenseManagerSchema.TransactionTable.Cols.DATE + ", " +
                ExpenseManagerSchema.TransactionTable.Cols.ACCOUNT_NO + ", " +
                ExpenseManagerSchema.TransactionTable.Cols.TYPE + ", " +
                ExpenseManagerSchema.TransactionTable.Cols.AMOUNT +

                ");"

        );


        mDatabase.execSQL("create table if not exists " + ExpenseManagerSchema.AccountTable.NAME + "(" +

                        ExpenseManagerSchema.AccountTable.Cols.ACCOUNT_NO + " primary key, " +
                        ExpenseManagerSchema.AccountTable.Cols.BANK_NAME + ", " +
                        ExpenseManagerSchema.AccountTable.Cols.HOLDER + ", " +
                        ExpenseManagerSchema.AccountTable.Cols.BALANCE +
                        ")"
        );

        TransactionDAO persistentMemoryTransactionDAO = new PersistentMemoryTransactionDAO(mDatabase);
        setTransactionsDAO(persistentMemoryTransactionDAO);

        AccountDAO persistentMemoryAccountDAO = new PersistentMemoryAccountDAO(mDatabase);
        setAccountsDAO(persistentMemoryAccountDAO);

        // dummy data
        /*
        Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        getAccountsDAO().addAccount(dummyAcct1);
        getAccountsDAO().addAccount(dummyAcct2);
        */
    }
}
