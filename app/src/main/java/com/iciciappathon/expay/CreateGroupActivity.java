package com.iciciappathon.expay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        initComponents();
        try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        groupMemberListAdapter = new GroupMemberListAdapter(CreateGroupActivity.this, listItemArrayList);
        listViewMembers.setAdapter(groupMemberListAdapter);
    }

    private void initComponents() {
        etGroupName = (EditText) findViewById(R.id.etGroupName);
        listViewMembers = (ListView) findViewById(R.id.listView);
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
                    listItem = new GroupMemberListItem(members.getJSONObject(i));
                    listItemArrayList.add(listItem);
            }
        }
    }
}
