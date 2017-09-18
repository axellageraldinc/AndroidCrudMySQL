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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String urlGeneral = "http://axell.hostingmerahputih.id/PHP_MySQL/api.php?apicall=";
    String urlGetAll = urlGeneral + "getAll";
    String urlCreate = urlGeneral + "create";

    String reqTag = "json_object_req";

    JsonObjectRequest jsonObjectRequest;

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
                    HashMap<String, String> param = new HashMap<String, String>();
                    param.put("username", txtName.getText().toString());
                    param.put("password", txtPassword.getText().toString());
                    PerformNetworkRequest pnr = new PerformNetworkRequest(urlCreate, param);
                    pnr.execute();
//                        insertData(txtName.getText().toString(), txtPassword.getText().toString());
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
        PerformNetworkRequest pnr = new PerformNetworkRequest(urlGetAll, null);
        pnr.execute();
    }

//    private void refreshUserList(JSONArray users) throws JSONException{
//        userModelList.clear();
//        for (int i=0; i<users.length(); i++){
//            JSONObject jsonObject = users.getJSONObject(i);
//            userModelList.add(new UserModel(
//                    //array users tadi itu didalamnya kan JSONObject id, username, dan password, diambil disini.
//                    jsonObject.getInt("id"),
//                    jsonObject.getString("username"),
//                    jsonObject.getString("password")
//            ));
//        }
//        listUserAdapter = new ListUserAdapter(getApplicationContext(), userModelList);
//        userListView.setAdapter(listUserAdapter);
//        listUserAdapter.notifyDataSetChanged();
//    }

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

        PerformNetworkRequest(String url, HashMap<String, String> param){
            this.url = url;
            this.param = param;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "PLEASE WAIT, IN PROGRESS", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            if (url == urlCreate){
                try {
                    insertData(param.get("username"), param.get("password"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (url == urlGetAll){
                getUsers();
            }
            return null;
        }

        private void insertData(final String username, final String password) throws JSONException {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCreate, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(MainActivity.this, "CREATING...", Toast.LENGTH_SHORT).show();
                    getUsers();
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> param = new HashMap<>();
                    param.put("username", username);
                    param.put("password", password);
                    return param;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest, reqTag);
//        PerformNetworkRequest pnr = new PerformNetworkRequest(ApiConnect.ROOT_URL_CREATE, param, CODE_POST_REQUEST);
//        pnr.execute();
        }

        private void getUsers(){
            StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetAll, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Boolean error = jsonObject.getBoolean("error");
                        if (!error) {
                            JSONArray jsonArray = jsonObject.getJSONArray("users");
                            refreshUserList(jsonArray);
                        } else{
                            String errorMessage = jsonObject.getString("message");
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            //Add request to queue
            AppController.getInstance().addToRequestQueue(stringRequest);
//        PerformNetworkRequest pnr = new PerformNetworkRequest(ApiConnect.ROOT_URL_READ, null, CODE_GET_REQUEST);
//        pnr.execute();
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
            System.out.println(s);
//            try{
//                JSONObject jsonObject = new JSONObject(s);
//                //Kalau gak response error kosong (alias gak error)
//                if (!jsonObject.getBoolean("error")){
//                    Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                    refreshUserList(jsonObject.getJSONArray("users"));
//                }
//            } catch (JSONException ex){
//                System.out.println("Error postExecute : " + ex.toString());
//            }
        }
    }
}
