package com.iciciappathon.expay.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import com.iciciappathon.expay.Constants.DatabaseConstants;
import com.iciciappathon.expay.POJOBeans.Contact;
import com.iciciappathon.expay.POJOBeans.Expense;
import com.iciciappathon.expay.POJOBeans.Group;
import com.iciciappathon.expay.POJOBeans.GroupMemberListItem;
import com.iciciappathon.expay.POJOBeans.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 03-04-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "eXpay";
    private static final String TABLE_CONTACTS = "eXpayMembers";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_VPA_ID = "vpa_id";

    public String  getName(){
        return KEY_NAME;
    }

    public String getVPA(){
        return KEY_VPA_ID;
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_VPA_ID + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(DatabaseConstants.GroupTable.CREATE_TABLE);
        db.execSQL(DatabaseConstants.MembersTable.CREATE_TABLE);
        db.execSQL(DatabaseConstants.ExpenseTable.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.GroupTable.TABLE_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.MembersTable.TABLE_MEMBERS);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseConstants.ExpenseTable.TABLE_EXPENSE);
        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_VPA_ID, contact.getPhoneNumber()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_VPA_ID }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // code to get all contacts in a list view
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // code to update the single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_VPA_ID, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    public void addGroup(Group group){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstants.GroupTable.GROUP_ID, group.getGroupId());
        contentValues.put(DatabaseConstants.GroupTable.GROUP_NAME, group.getGroupName());
        contentValues.put(DatabaseConstants.GroupTable.GROUP_TOTAL, group.getGroupTotal());

        db.insert(DatabaseConstants.GroupTable.TABLE_GROUP,null,contentValues);
    }

    public List<Group> getAllGroups() {
        List<Group> groupList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(DatabaseConstants.GroupTable.SELECT_ALL, null);

        if (cursor.moveToFirst()) {
            do {
                Group group = new Group();
                group.setGroupId(String.valueOf(Integer.parseInt(cursor.getString(0))));
                group.setGroupName(cursor.getString(1));
                group.setGroupTotal(cursor.getString(2));
                groupList.add(group);
            } while (cursor.moveToNext());
        }
        return groupList;
    }

    public int getMaxGroupId(){
        int groupId=0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + DatabaseConstants.GroupTable.GROUP_ID + ") FROM " + DatabaseConstants.GroupTable.TABLE_GROUP,null);
        cursor.moveToFirst();
        groupId = cursor.getInt(0);
        return groupId!=0?groupId:0;
    }

    public void addMember(GroupMemberListItem member){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstants.MembersTable.MEMBER_NAME, member.getName());
        contentValues.put(DatabaseConstants.MembersTable.MEMBER_UPI, member.getVPA_Id());
        contentValues.put(DatabaseConstants.MembersTable.MEMBER_AMOUNT,member.getMemberAmount());
        contentValues.put(DatabaseConstants.MembersTable.MEMBER_GROUP_ID, member.getGroupId());
        contentValues.put(DatabaseConstants.MembersTable.MEMBER_EXPENSE_TOTAL, member.getMemberExpenseTotal());
        contentValues.put(DatabaseConstants.MembersTable.IS_MAIN_MEMBER,member.getIsMainMember());
        db.insert(DatabaseConstants.MembersTable.TABLE_MEMBERS,null,contentValues);
    }

    public List<GroupMemberListItem> getMembers(String groupId) {
        List<GroupMemberListItem> memberList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(DatabaseConstants.MembersTable.SELECT_ALL_FROM_GROUP + "'" + groupId + "'", null);
        if (cursor.moveToFirst()) {
            do {
                GroupMemberListItem groupMemberListItem = new GroupMemberListItem();
                groupMemberListItem.setMemberId(String.valueOf(Integer.parseInt(cursor.getString(0))));
                groupMemberListItem.setName(cursor.getString(1));
                groupMemberListItem.setVPA_Id(cursor.getString(2));
                groupMemberListItem.setMemberAmount(cursor.getString(3));
                groupMemberListItem.setGroupId(cursor.getString(4));
                groupMemberListItem.setMemberExpenseTotal(cursor.getString(5));
                groupMemberListItem.setIsMainMember((cursor.getInt(6)));
                memberList.add(groupMemberListItem);
            } while (cursor.moveToNext());
        }
        return memberList;
    }

    public String[] getMemberNames(String groupId) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(DatabaseConstants.MembersTable.SELECT_ALL_FROM_GROUP + "'" + groupId + "'", null);
        String[] memberNameList = new String[cursor.getCount()];
        if (cursor.moveToFirst()) {
            int i=0;
            do {
                memberNameList[i] = cursor.getString(1);
                i++;
            } while (cursor.moveToNext() && i < 20);
        }
        return memberNameList;
    }

    public void addExpense(Expense expense){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstants.ExpenseTable.EXPENSE_DESC, expense.getExpenseDesc());
        contentValues.put(DatabaseConstants.ExpenseTable.EXPENSE_AMOUNT, expense.getExpenseAmount());
        contentValues.put(DatabaseConstants.ExpenseTable.GROUP_ID, expense.getGroupId());
        contentValues.put(DatabaseConstants.ExpenseTable.MEMBER_ID, expense.getMemberId());
        contentValues.put(DatabaseConstants.ExpenseTable.MEMBER_NAME, expense.getMemberName());
        db.insert(DatabaseConstants.ExpenseTable.TABLE_EXPENSE,null,contentValues);
    }

    public List<Expense> getExpenses(String groupId) {
        List<Expense> expenseList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(DatabaseConstants.ExpenseTable.SELECT_ALL_FROM_GROUP + "'" + groupId + "'", null);


        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setExpenseId(String.valueOf(Integer.parseInt(cursor.getString(0))));
                expense.setExpenseDesc(cursor.getString(1));
                expense.setExpenseAmount(cursor.getString(2));
                expense.setGroupId(cursor.getString(3));
                expense.setMemberId(cursor.getString(4));
                expense.setMemberName(cursor.getString(5));
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }
        return expenseList;
    }

    public List<Transaction> getTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(DatabaseConstants.ExpenseTable.SELECT_ALL,null);

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setExpenseId(String.valueOf(Integer.parseInt(cursor.getString(0))));
                transaction.setExpenseDesc(cursor.getString(1));
                transaction.setExpenseAmount(cursor.getString(2));
                transaction.setGroupName(getGroupNameFromGroupId(cursor.getString(3)));
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        return transactionList;
    }

    public String getGroupNameFromGroupId(String groupId){
        String strGroupName = null;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(DatabaseConstants.GroupTable.SELECT_GROUP_NAME + "'" + groupId + "'",null);

        if(cursor.moveToFirst()){
            strGroupName = cursor.getString(0);
        }else{
            strGroupName = groupId ;
        }
        return strGroupName;
    }

    public String getGroupTotal(String groupId){
        String strGroupTotal = null;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(DatabaseConstants.GroupTable.SELECT_GROUP_TOTAL + "'" + groupId + "'",null);

        if(cursor.moveToFirst()){
            strGroupTotal = cursor.getString(0);
        }else{
            strGroupTotal = "0" ;
        }
        return strGroupTotal;
    }

    public String getMIDFromMname(String mName,String gid){
        String memberId = null;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(DatabaseConstants.MembersTable.SELECT_MID + "'" + mName + "' AND " + DatabaseConstants.MembersTable.MEMBER_GROUP_ID + " ='" + gid + "'",null);
        if(cursor!=null) {
            cursor.moveToFirst();
            memberId = cursor.getString(0);
        }

        return memberId;
    }

    public void updateMemberTotal(String totalAmount,String mId,String gId){
        SQLiteDatabase db = this.getWritableDatabase();

        SQLiteStatement sqLiteStatement = db.compileStatement(DatabaseConstants.MembersTable.UPDATE_MEMBER_TOTAL);
        sqLiteStatement.bindString(1,totalAmount);
        sqLiteStatement.bindString(2,mId);
        sqLiteStatement.bindString(3,gId);
        sqLiteStatement.execute();
    }

    public void updateGroupTotal(Group group){
        SQLiteDatabase db = this.getWritableDatabase();

        SQLiteStatement sqLiteStatement = db.compileStatement(DatabaseConstants.GroupTable.UPDATE_GROUP_TOTAL);
        sqLiteStatement.bindString(1,group.getGroupTotal());
        sqLiteStatement.bindString(2,group.getGroupId());
        sqLiteStatement.execute();
    }

    public void updateMemberExpenseTotal(String mExTotal,String mId){
        SQLiteDatabase db = this.getWritableDatabase();

        SQLiteStatement sqLiteStatement = db.compileStatement(DatabaseConstants.MembersTable.UPDATE_MEMBER_EXPENSE_TOTAL);
        sqLiteStatement.bindString(1,mExTotal);
        sqLiteStatement.bindString(2,mId);
        sqLiteStatement.execute();
    }

    public String getMemberExpenseTotal(String mId){
        String memberExpenseTotal="0";

        SQLiteDatabase db= this.getWritableDatabase();

        Cursor cursor = db.rawQuery(DatabaseConstants.MembersTable.GET_MEMBER_EXPENSE_TOTAl + "'" + mId + "'",null);
        if(cursor.moveToFirst()){
           memberExpenseTotal = cursor.getString(0);
        }else{
            memberExpenseTotal = "0";
        }
        return memberExpenseTotal;
    }

    public String getMemberAmount(String memberId,String gId){
        String memberAmount= null;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(DatabaseConstants.MembersTable.GET_MEMBER_AMOUNT + "'" + memberId+ "' AND " + DatabaseConstants.MembersTable.MEMBER_GROUP_ID + " = '" + gId + "'" ,null);
        if(cursor!=null) {
            cursor.moveToFirst();
            memberAmount = cursor.getString(0);
        }
        return memberAmount;
    }

}
