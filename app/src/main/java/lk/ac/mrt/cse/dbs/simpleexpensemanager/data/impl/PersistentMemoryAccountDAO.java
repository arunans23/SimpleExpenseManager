package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountCursorWrapper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.ExpenseManagerSchema;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;



/**
 * Created by arunan on 11/16/16.
 */

public class PersistentMemoryAccountDAO implements AccountDAO {

    private Map<String, Account> accounts;

    private SQLiteDatabase mDatabase;

    public PersistentMemoryAccountDAO(SQLiteDatabase mDatabase){
        //mDatabase = new ExpenseManageDBHelper(context.getApplicationContext()).getWritableDatabase();
        this.mDatabase = mDatabase;
        this.accounts = updateHashMap();
    }

    public Map<String, Account> updateHashMap(){
        Map<String, Account> accounts = new HashMap<>();
        //List<Account> accounts = new ArrayList<>();
        AccountCursorWrapper cursor = queryAccounts(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Account currentAccount = cursor.getAccount();
                accounts.put(currentAccount.getAccountNo(), currentAccount);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return accounts;
    }

    @Override
    public List<String> getAccountNumbersList() {
        this.accounts = updateHashMap();
        return new ArrayList<>(accounts.keySet());
    }

    @Override
    public List<Account> getAccountsList() {
        this.accounts = updateHashMap();
        return new ArrayList<>(accounts.values());

    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        if (accounts.containsKey(accountNo)) {
            return accounts.get(accountNo);
        }
        String msg = "Account " + accountNo + " is invalid.";
        throw new InvalidAccountException(msg);
    }

    @Override
    public void addAccount(Account account) {
        String sqlQuery = "insert into " + ExpenseManagerSchema.AccountTable.NAME + "(" +
                ExpenseManagerSchema.AccountTable.Cols.ACCOUNT_NO + ", " +
                ExpenseManagerSchema.AccountTable.Cols.BANK_NAME + ", " +
                ExpenseManagerSchema.AccountTable.Cols.HOLDER + ", " +
                ExpenseManagerSchema.AccountTable.Cols.BALANCE +
                ") values (?,?,?,?)";
        //ContentValues values = getContentValues(account);
        SQLiteStatement sqlStatement = mDatabase.compileStatement(sqlQuery);

        sqlStatement.bindString(1, account.getAccountNo());
        sqlStatement.bindString(2, account.getBankName());
        sqlStatement.bindString(3, account.getAccountHolderName());
        sqlStatement.bindDouble(4, account.getBalance());
        sqlStatement.executeInsert();
        accounts = updateHashMap();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String sqlQuery = "DELETE FROM " + ExpenseManagerSchema.AccountTable.NAME + " where " +
                ExpenseManagerSchema.AccountTable.Cols.ACCOUNT_NO + "=  ?";
        SQLiteStatement sqlStatement = mDatabase.compileStatement(sqlQuery);
        sqlStatement.bindString(1, accountNo);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        if (!accounts.containsKey(accountNo)) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        Account account = accounts.get(accountNo);
        // specific implementation based on the transaction type
        switch (expenseType) {
            case EXPENSE:
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account.setBalance(account.getBalance() + amount);
                break;
        }
        removeAccount(account.getAccountNo());
        addAccount(account);
    }

    private static ContentValues getContentValues(Account account){
        ContentValues values = new ContentValues();
        values.put(ExpenseManagerSchema.AccountTable.Cols.ACCOUNT_NO, account.getAccountNo());
        values.put(ExpenseManagerSchema.AccountTable.Cols.BANK_NAME, account.getBankName());
        values.put(ExpenseManagerSchema.AccountTable.Cols.HOLDER, account.getAccountHolderName());
        values.put(ExpenseManagerSchema.AccountTable.Cols.BALANCE, account.getBalance());

        return values;
    }

    private AccountCursorWrapper queryAccounts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ExpenseManagerSchema.AccountTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new AccountCursorWrapper(cursor);
    }



}
