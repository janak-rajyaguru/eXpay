package com.iciciappathon.expay.Constants;

/**
 * Created by HeeRain on 4/5/2017.
 */

public class DatabaseConstants {

    public static class GroupTable {
        public static final String TABLE_GROUP = "GROUP_TABLE";
        public static final String GROUP_ID = "GROUP_ID";
        public static final String GROUP_NAME = "GROUP_NAME";

        public static final String CREATE_TABLE = " CREATE TABLE " + TABLE_GROUP + "(" + GROUP_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT ," + GROUP_NAME + " TEXT " + ")";

        public static final String SELECT_ALL = " SELECT * FROM " + TABLE_GROUP;

        public static final String SELECT_GROUP_NAME = "SELECT " + GROUP_NAME + " FROM " + TABLE_GROUP + " WHERE " + GROUP_ID + " = ";
    }

    public static class MembersTable{
        public static final String TABLE_MEMBERS = "MEMBERS_TABLE";
        public static final String MEMBER_ID = "MEMBER_ID";
        public static final String MEMBER_NAME = "MEMBER_NAME";
        public static final String MEMBER_UPI = "MEMBER_UPI";
        public static final String MEMBER_AMOUNT = "MEMBER_AMOUNT";
        public static final String MEMBER_GROUP_ID = "GROUP_ID";

        public static final String CREATE_TABLE = " CREATE TABLE " + TABLE_MEMBERS
                + "("
                + MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + MEMBER_NAME + " TEXT , "
                + MEMBER_UPI + " TEXT , "
                + MEMBER_AMOUNT + " TEXT , "
                + MEMBER_GROUP_ID + " TEXT"
                + ")";

        public static final String SELECT_ALL = "SELECT * FROM " + TABLE_MEMBERS;

        public static final String SELECT_ALL_FROM_GROUP = "SELECT * FROM " + TABLE_MEMBERS + " WHERE " + MEMBER_GROUP_ID + " = ";

    }

    public static class ExpenseTable {
        public static final String TABLE_EXPENSE = "EXPENSE_TABLE";
        public static final String EXPENSE_ID = "EXPENSE_ID";
        public static final String EXPENSE_DESC = "EXPENSE_DESC";
        public static final String EXPENSE_AMOUNT = "EXPENSE_AMOUNT";
        public static final String GROUP_ID = "GROUP_ID";

        public static final String CREATE_TABLE = " CREATE TABLE " + TABLE_EXPENSE
                + "("
                + EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + EXPENSE_DESC + " TEXT , "
                + EXPENSE_AMOUNT + " TEXT, "
                + GROUP_ID + " TEXT"
                + ")";

        public static final String SELECT_ALL = "SELECT * FROM " + TABLE_EXPENSE;

        public static final String SELECT_ALL_FROM_GROUP = "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + GROUP_ID + " = ";
    }
}
