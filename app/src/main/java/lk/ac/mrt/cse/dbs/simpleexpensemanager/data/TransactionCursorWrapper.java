package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.sql.Date;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by arunan on 11/16/16.
 */

public class TransactionCursorWrapper  extends CursorWrapper{

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TransactionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Transaction getTransaction(){
        long date = getLong(getColumnIndex(ExpenseManagerSchema.TransactionTable.Cols.DATE));
        String accountNo = getString(getColumnIndex(ExpenseManagerSchema.TransactionTable.Cols.ACCOUNT_NO));
        String type = getString(getColumnIndex(ExpenseManagerSchema.TransactionTable.Cols.TYPE));
        double amount = getDouble(getColumnIndex(ExpenseManagerSchema.TransactionTable.Cols.AMOUNT));
        ExpenseType typeE;
        if (type=="EXPENSE"){
            typeE = ExpenseType.EXPENSE;
        }
        else{
            typeE = ExpenseType.INCOME;
        }

        return new Transaction(new Date(date), accountNo, typeE, amount);

    }
}
