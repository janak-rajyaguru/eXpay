package com.iciciappathon.expay.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.iciciappathon.expay.Adapters.GroupMemberListAdapter;
import com.iciciappathon.expay.Database.DatabaseHandler;
import com.iciciappathon.expay.POJOBeans.Contact;
import com.iciciappathon.expay.POJOBeans.Group;
import com.iciciappathon.expay.POJOBeans.GroupMemberListItem;
import com.iciciappathon.expay.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText etGroupName =null;
    private ListView listViewMembers = null;
    private GroupMemberListAdapter groupMemberListAdapter;
    private ArrayList<GroupMemberListItem> listItemArrayList = new ArrayList<>();
    private GroupMemberListItem listItem = null;
    DatabaseHandler databaseHandler;
    public SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private Toolbar mToolbar;
 //   private Button btnAddMembers;

    private Button btnCreateGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        setUpToolbar();
        initComponents();
        GroupMemberListItem firstMember = new GroupMemberListItem();
        firstMember.setName("");
        firstMember.setVPA_Id("");
        GroupMemberListItem selfMember = new GroupMemberListItem();
        selfMember.setName("Nitesh");
        selfMember.setVPA_Id("nitesh@axisbank");
        selfMember.setIsMainMember(1);
        selfMember.setMemberAmount("0");
        listItemArrayList.add(firstMember);
        listItemArrayList.add(selfMember);
       /* try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //setTempData();
        //setDataToList();
        groupMemberListAdapter = new GroupMemberListAdapter(CreateGroupActivity.this, listItemArrayList);
        listViewMembers.setAdapter(groupMemberListAdapter);

        listViewMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    Intent addMemberIntent = new Intent(CreateGroupActivity.this, AddMembertoGroupActivity.class);
                    startActivityForResult(addMemberIntent,99);
                }
            }
        });

      /*  btnAddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMemberIntent = new Intent(CreateGroupActivity.this,AddMembertoGroupActivity.class);
                startActivityForResult(addMemberIntent,99);
            }
        });*/

        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maxGId = databaseHandler.getMaxGroupId() + 1;
                Group group = new Group(String.valueOf(maxGId),etGroupName.getText().toString().trim());
                databaseHandler.addGroup(group);
                for (GroupMemberListItem member:listItemArrayList){
                    if(member.getVPA_Id() != null & !member.getVPA_Id().equals(""))
                    member.setGroupId(String.valueOf(maxGId));
                    databaseHandler.addMember(member);
                }
                Intent intent = new Intent(CreateGroupActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
            mToolbar.setTitleTextColor(Color.WHITE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initComponents() {
        etGroupName = (EditText) findViewById(R.id.etGroupName);
        listViewMembers = (ListView) findViewById(R.id.memberListViewforCreateGroup);
     //   btnAddMembers = (Button) findViewById(R.id.btn_add_member);
        btnCreateGroup = (Button) findViewById(R.id.btn_create_group);
        databaseHandler = new DatabaseHandler(this);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("members.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void getData() throws JSONException {
        JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
        JSONArray members = jsonObject.getJSONArray("members");

        if(null != members){
            for (int i = 0; i < members.length(); i++) {
                 //   listItem = new GroupMemberListItem(members.getJSONObject(i));
                    listItemArrayList.add(listItem);
            }
        }
    }

    private void setTempData(){
        databaseHandler.addContact(new Contact("", ""));
        databaseHandler.addContact(new Contact("Nitesh", "nitesh@axisbank.com"));
        databaseHandler.addContact(new Contact("Hiren", "nitesh@axisbank.com"));
        databaseHandler.addContact(new Contact("Janak", "nitesh@axisbank.com"));
        databaseHandler.addContact(new Contact("Kavita", "nitesh@axisbank.com"));
        databaseHandler.addContact(new Contact("Nitesh", "nitesh@axisbank.com"));
        databaseHandler.addContact(new Contact("Hiren", "nitesh@axisbank.com"));
        databaseHandler.addContact(new Contact("Janak", "nitesh@axisbank.com"));
        databaseHandler.addContact(new Contact("Kavita", "nitesh@axisbank.com"));
        databaseHandler.addContact(new Contact("Nitesh", "nitesh@axisbank.com"));
        databaseHandler.addContact(new Contact("Hiren", "nitesh@axisbank.com"));
        databaseHandler.addContact(new Contact("Janak", "nitesh@axisbank.com"));
        databaseHandler.addContact(new Contact("Kavita", "nitesh@axisbank.com"));
    }

    public void setDataToList() {
        sqLiteDatabase = databaseHandler.getWritableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM eXpayMembers", null);

        listItemArrayList.clear();

        if (cursor.moveToFirst()) {
            do {
                listItem = new GroupMemberListItem(cursor.getString(cursor.getColumnIndex(databaseHandler.getName())),
                        cursor.getString(cursor.getColumnIndex(databaseHandler.getVPA())));
                listItemArrayList.add(listItem);

            } while (cursor.moveToNext());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 99 && resultCode == 15){
            GroupMemberListItem memberData = (GroupMemberListItem) data.getExtras().getSerializable("memberToGroup");
            listItemArrayList.add(memberData);
            groupMemberListAdapter = new GroupMemberListAdapter(CreateGroupActivity.this, listItemArrayList);
            listViewMembers.setAdapter(groupMemberListAdapter);

            if(listItemArrayList.size() > 0){
                btnCreateGroup.setVisibility(View.VISIBLE);
            }else{
                btnCreateGroup.setVisibility(View.INVISIBLE);
            }
        }
    }

}
