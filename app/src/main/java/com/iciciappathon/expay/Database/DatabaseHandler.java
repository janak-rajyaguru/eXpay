package com.iciciappathon.expay.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.iciciappathon.expay.Constants.DatabaseConstants;
import com.iciciappathon.expay.POJOBeans.Contact;
import com.iciciappathon.expay.POJOBeans.Expense;
import com.iciciappathon.expay.POJOBeans.Group;
import com.iciciappathon.expay.POJOBeans.GroupMemberListItem;

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
        contentValues.put(DatabaseConstants.GroupTable.GROUP_NAME, group.getGroupName());

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
                groupList.add(group);
            } while (cursor.moveToNext());
        }
        return groupList;
    }

    public void addMember(GroupMemberListItem member){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstants.MembersTable.MEMBER_NAME, member.getName());
        contentValues.put(DatabaseConstants.MembersTable.MEMBER_UPI, member.getVPA_Id());
        contentValues.put(DatabaseConstants.MembersTable.MEMBER_GROUP_ID, member.getGroupId());
        db.insert(DatabaseConstants.MembersTable.TABLE_MEMBERS,null,contentValues);
    }

    public List<GroupMemberListItem> getMembers(String groupId) {
        List<GroupMemberListItem> memberList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        /*Cursor cursor = db.query(DatabaseConstants.MembersTable.TABLE_MEMBERS, new String[] { DatabaseConstants.MembersTable.MEMBER_ID,
                        DatabaseConstants.MembersTable.MEMBER_NAME, DatabaseConstants.MembersTable.MEMBER_UPI,DatabaseConstants.MembersTable.MEMBER_GROUP_ID }, DatabaseConstants.MembersTable.MEMBER_GROUP_ID + "=?",
                new String[] { groupId }, null, null, null, null);*/
        Cursor cursor = db.rawQuery(DatabaseConstants.MembersTable.SELECT_ALL_FROM_GROUP + "'" + groupId + "'", null);
        if (cursor.moveToFirst()) {
            do {
                GroupMemberListItem groupMemberListItem = new GroupMemberListItem();
                groupMemberListItem.setMemberId(String.valueOf(Integer.parseInt(cursor.getString(0))));
                groupMemberListItem.setName(cursor.getString(1));
                groupMemberListItem.setVPA_Id(cursor.getString(2));
                groupMemberListItem.setGroupId(cursor.getString(3));
                memberList.add(groupMemberListItem);
            } while (cursor.moveToNext());
        }
        return memberList;
    }

    public void addExpense(Expense expense){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseConstants.ExpenseTable.GROUP_ID, expense.getGroupId());
        contentValues.put(DatabaseConstants.ExpenseTable.EXPENSE_DESC, expense.getExpenseDesc());
        db.insert(DatabaseConstants.ExpenseTable.TABLE_EXPENSE,null,contentValues);
    }

    public List<Expense> getExpenses(String groupId) {
        List<Expense> expenseList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DatabaseConstants.ExpenseTable.TABLE_EXPENSE, new String[] { DatabaseConstants.ExpenseTable.EXPENSE_ID,
                        DatabaseConstants.ExpenseTable.EXPENSE_DESC, DatabaseConstants.ExpenseTable.GROUP_ID }, DatabaseConstants.ExpenseTable.GROUP_ID + "=?",
                new String[] { groupId }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setExpenseId(String.valueOf(Integer.parseInt(cursor.getString(0))));
                expense.setGroupId(cursor.getString(1));
                expense.setExpenseDesc(cursor.getString(2));
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }
        return expenseList;
    }

}
