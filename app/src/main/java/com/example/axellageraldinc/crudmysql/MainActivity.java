package com.example.axellageraldinc.crudmysql;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String urlGetAll = "http://axell.hostingmerahputih.id/PHP_MySQL/api.php?apicall=getAll";

    private boolean statusUpdate = false;

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private TextView txtId;
    private EditText txtName, txtPassword;
    private Button btnInsert;
    private ListView userListView;

    private ListUserAdapter listUserAdapter;

    private List<UserModel> userModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtId = (TextView) findViewById(R.id.txtId);
        txtName = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        userListView = (ListView) findViewById(R.id.listUsers);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (statusUpdate==false){
                    //Jika update false, maka insert
                    insertData(txtName.getText().toString(), txtPassword.getText().toString());
                    txtId.setText("");
                    txtName.setText("");
                    txtPassword.setText("");
                } else{
                    //Jika update true, maka update
                    update(Integer.parseInt(txtId.getText().toString()), txtName.getText().toString(), txtPassword.getText().toString());
                    statusUpdate = false;
                    txtId.setText("");
                    txtName.setText("");
                    txtPassword.setText("");
                }
            }
        });
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                statusUpdate = true;
                UserModel userModel = userModelList.get(i);
                int id = userModel.getId();
                String username = userModel.getUsername();
                String password = userModel.getPassword();
                txtId.setText(String.valueOf(id));
                txtName.setText(username);
                txtPassword.setText(password);
            }
        });
        getUsers();
    }

    private void insertData(String username, String password){
        HashMap<String, String> param = new HashMap<>();
        param.put("username", username);
        param.put("password", password);

//        PerformNetworkRequest pnr = new PerformNetworkRequest(ApiConnect.ROOT_URL_CREATE, param, CODE_POST_REQUEST);
//        pnr.execute();
    }

    private void getUsers(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlGetAll, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    //Parsing JSON
                    //Ambil array users dari response JSON, dimana di dalam array users itu ada id, username dan password
                    JSONArray usersArray = response.getJSONArray("users");
                    refreshUserList(usersArray);
                } catch (JSONException ex){
                    System.out.println("Error get Users : " + ex.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //Add request to queue
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
//        PerformNetworkRequest pnr = new PerformNetworkRequest(ApiConnect.ROOT_URL_READ, null, CODE_GET_REQUEST);
//        pnr.execute();
    }

    private void refreshUserList(JSONArray users) throws JSONException{
        userModelList.clear();
        for (int i=0; i<users.length(); i++){
            JSONObject jsonObject = users.getJSONObject(i);
            userModelList.add(new UserModel(
                    //array users tadi itu didalamnya kan JSONObject id, username, dan password, diambil disini.
                    jsonObject.getInt("id"),
                    jsonObject.getString("username"),
                    jsonObject.getString("password")
            ));
        }
        listUserAdapter = new ListUserAdapter(getApplicationContext(), userModelList);
        userListView.setAdapter(listUserAdapter);
        listUserAdapter.notifyDataSetChanged();
    }

    private void update(int id, String username, String password){
        HashMap<String, String> param = new HashMap<>();
        param.put("id", String.valueOf(id));
        param.put("username", username);
        param.put("password", password);
//        PerformNetworkRequest pnr = new PerformNetworkRequest(ApiConnect.ROOT_URL_UPDATE, param, CODE_POST_REQUEST);
//        pnr.execute();
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
                        jsonObject.getString("username"),
                        jsonObject.getString("password")
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
