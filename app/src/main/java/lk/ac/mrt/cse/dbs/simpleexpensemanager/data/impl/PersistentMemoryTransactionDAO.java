package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionCursorWrapper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.ExpenseManagerSchema.TransactionTable;

/**
 * Created by arunan on 11/16/16.
 */

public class PersistentMemoryTransactionDAO implements TransactionDAO {

    private SQLiteDatabase mDatabase;
    private final List<Transaction> transactions;

    public PersistentMemoryTransactionDAO(SQLiteDatabase mDatabase){
        //mDatabase = new ExpenseManageDBHelper(context.getApplicationContext()).getWritableDatabase();
        this.mDatabase = mDatabase;
        transactions = getAllTransactionLogs();
    }

    private static ContentValues getContentValues(Transaction transaction){
        ContentValues values = new ContentValues();
        values.put(TransactionTable.Cols.DATE, transaction.getDate().getTime());
        values.put(TransactionTable.Cols.ACCOUNT_NO, transaction.getAccountNo());
        values.put(TransactionTable.Cols.TYPE, transaction.getExpenseType().toString());
        values.put(TransactionTable.Cols.AMOUNT, transaction.getAmount());


        return values;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        String sqlQuery = "insert into " + TransactionTable.NAME + "(" +
                TransactionTable.Cols.DATE + ", " +
                TransactionTable.Cols.ACCOUNT_NO + ", " +
                TransactionTable.Cols.TYPE + ", " +
                TransactionTable.Cols.AMOUNT + ") values (?,?,?,?)";
        //Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        //ContentValues values = getContentValues(transaction);
        //mDatabase.insert(TransactionTable.NAME, null, values);

        SQLiteStatement sqlStatement = mDatabase.compileStatement(sqlQuery);

        sqlStatement.bindDouble(1, date.getTime());
        sqlStatement.bindString(2, accountNo);
        sqlStatement.bindString(3, expenseType.toString());
        sqlStatement.bindDouble(4, amount);

        sqlStatement.executeInsert();

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactions = new ArrayList<>();
        TransactionCursorWrapper cursor = queryTransactions(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                transactions.add(cursor.getTransaction());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return transactions;


    }

    private TransactionCursorWrapper queryTransactions(String whereClause, String[] whereArgs) {
       Cursor cursor = mDatabase.query(
               TransactionTable.NAME,
               null, // Columns - null selects all columns
               whereClause,
              whereArgs,
               null, // groupBy
               null, // having
               null // orderBy
                //null    //limit
       );
        //Cursor cursor = mDatabase.rawQuery("select * from transaction;", null);

        return new TransactionCursorWrapper(cursor);
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactions = new ArrayList<>();
        transactions = getAllTransactionLogs();
        int size = transactions.size();
        if (size <= limit) {
            return transactions;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactions.subList(size - limit, size);
    }
}
