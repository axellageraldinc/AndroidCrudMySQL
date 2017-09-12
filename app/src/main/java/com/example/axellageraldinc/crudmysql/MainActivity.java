package com.example.axellageraldinc.crudmysql;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private EditText txtName, txtPassword;
    private Button btnInsert;
    private ListView userListView;

    private ListUserAdapter listUserAdapter;

    private List<UserModel> userModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        userListView = (ListView) findViewById(R.id.listUsers);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData(txtName.getText().toString(), txtPassword.getText().toString());
            }
        });
        getUsers();
    }

    private void insertData(String username, String password){
        HashMap<String, String> param = new HashMap<>();
        param.put("username", username);
        param.put("password", password);

        PerformNetworkRequest pnr = new PerformNetworkRequest(ApiConnect.ROOT_URL_CREATE, param, CODE_POST_REQUEST);
        pnr.execute();
    }

    private void getUsers(){
        PerformNetworkRequest pnr = new PerformNetworkRequest(ApiConnect.ROOT_URL_READ, null, CODE_GET_REQUEST);
        pnr.execute();
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String>{

        String url;
        HashMap<String, String> param;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> param, int requestCode){
            this.url = url;
            this.param = param;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "PLEASE WAIT, IN PROGRESS", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler rh = new RequestHandler();
            if (requestCode == CODE_POST_REQUEST){
                return rh.sendPostRequest(url, param);
            } else if(requestCode == CODE_GET_REQUEST){
                return rh.sendGetRequest(url);
            }
            return null;
        }

        private void refreshUserList(JSONArray users) throws JSONException{
            userModelList.clear();
            for (int i=0; i<users.length(); i++){
                JSONObject jsonObject = users.getJSONObject(i);
                userModelList.add(new UserModel(
                        jsonObject.getInt("id"),
                        jsonObject.getString("username")
                ));
            }
            listUserAdapter = new ListUserAdapter(getApplicationContext(), userModelList);
            userListView.setAdapter(listUserAdapter);
            listUserAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try{
                JSONObject jsonObject = new JSONObject(s);
                //Kalau gak response error kosong (alias gak error)
                if (!jsonObject.getBoolean("error")){
                    Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshUserList(jsonObject.getJSONArray("users"));
                }
            } catch (JSONException ex){
                System.out.println("Error postExecute : " + ex.toString());
            }
        }
    }
}
