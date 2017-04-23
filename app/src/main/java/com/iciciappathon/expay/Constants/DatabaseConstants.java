package com.iciciappathon.expay.Constants;

/**
 * Created by HeeRain on 4/5/2017.
 */

public class DatabaseConstants {

    public static class GroupTable {
        public static final String TABLE_GROUP = "GROUP_TABLE";
        public static final String GROUP_ID = "GROUP_ID";
        public static final String GROUP_NAME = "GROUP_NAME";
        public static final String GROUP_TOTAL = "GROUP_TOTAL";

        public static final String CREATE_TABLE = " CREATE TABLE " + TABLE_GROUP + "(" + GROUP_ID +
                " INTEGER PRIMARY KEY ," + GROUP_NAME + " TEXT, " + GROUP_TOTAL + " TEXT " + ")";

        public static final String SELECT_ALL = " SELECT * FROM " + TABLE_GROUP;

        public static final String SELECT_GROUP_NAME = "SELECT " + GROUP_NAME + " FROM " + TABLE_GROUP + " WHERE " + GROUP_ID + " = ";
        public static final String SELECT_GROUP_TOTAL = "SELECT " + GROUP_TOTAL + " FROM " + TABLE_GROUP + " WHERE " + GROUP_ID + " = ";
        public static final String UPDATE_GROUP_TOTAL = "UPDATE " + TABLE_GROUP + " SET " + GROUP_TOTAL + " = ? WHERE " + GROUP_ID + " = ?";
    }

    public static class MembersTable{
        public static final String TABLE_MEMBERS = "MEMBERS_TABLE";
        public static final String MEMBER_ID = "MEMBER_ID";
        public static final String MEMBER_NAME = "MEMBER_NAME";
        public static final String MEMBER_UPI = "MEMBER_UPI";
        public static final String MEMBER_AMOUNT = "MEMBER_AMOUNT";
        public static final String MEMBER_GROUP_ID = "GROUP_ID";
        public static final String MEMBER_EXPENSE_TOTAL = "MEMBER_EXPENSE_TOTAL";
        public static final String IS_MAIN_MEMBER = "IS_MAIN_MEMBER";

        public static final String CREATE_TABLE = " CREATE TABLE " + TABLE_MEMBERS
                + "("
                + MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + MEMBER_NAME + " TEXT , "
                + MEMBER_UPI + " TEXT , "
                + MEMBER_AMOUNT + " TEXT , "
                + MEMBER_GROUP_ID + " TEXT , "
                + MEMBER_EXPENSE_TOTAL + " TEXT , "
                + IS_MAIN_MEMBER + " INTEGER "
                + ")";

        public static final String SELECT_ALL = "SELECT * FROM " + TABLE_MEMBERS;
        public static final String SELECT_MID = "SELECT " + MEMBER_ID + " FROM " + TABLE_MEMBERS + " WHERE " + MEMBER_NAME + " = ";
        public static final String SELECT_ALL_FROM_GROUP = "SELECT * FROM " + TABLE_MEMBERS + " WHERE " + MEMBER_GROUP_ID + " = ";
        public static final String GET_MEMBER_AMOUNT = "SELECT " + MEMBER_AMOUNT + " FROM " + TABLE_MEMBERS + " WHERE " + MEMBER_ID + " = ";
        public static final String UPDATE_MEMBER_TOTAL = "UPDATE " + TABLE_MEMBERS + " SET " + MEMBER_AMOUNT + " = ? WHERE " + MEMBER_ID + " = ? AND " + MEMBER_GROUP_ID + " = ?";
        public static final String UPDATE_MEMBER_EXPENSE_TOTAL = "UPDATE " + TABLE_MEMBERS + " SET " + MEMBER_EXPENSE_TOTAL + " = ?  WHERE " + MEMBER_ID + " = ?";
        public static final String GET_MEMBER_EXPENSE_TOTAl = "SELECT " + MEMBER_EXPENSE_TOTAL + " FROM " + TABLE_MEMBERS + " WHERE " + MEMBER_ID + " = ";
    }

    public static class ExpenseTable {
        public static final String TABLE_EXPENSE = "EXPENSE_TABLE";
        public static final String EXPENSE_ID = "EXPENSE_ID";
        public static final String EXPENSE_DESC = "EXPENSE_DESC";
        public static final String EXPENSE_AMOUNT = "EXPENSE_AMOUNT";
        public static final String GROUP_ID = "GROUP_ID";
        public static final String MEMBER_ID = "MEMBER_ID";
        public static final String MEMBER_NAME = "MEMBER_NAME";

        public static final String CREATE_TABLE = " CREATE TABLE " + TABLE_EXPENSE
                + "("
                + EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + EXPENSE_DESC + " TEXT , "
                + EXPENSE_AMOUNT + " TEXT, "
                + GROUP_ID + " TEXT, "
                + MEMBER_ID + " TEXT, "
                + MEMBER_NAME + " TEXT "
                + ")";

        public static final String SELECT_ALL = "SELECT * FROM " + TABLE_EXPENSE + " ORDER BY EXPENSE_ID DESC";

        public static final String SELECT_ALL_FROM_GROUP = "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + GROUP_ID + " = ";
    }
}
