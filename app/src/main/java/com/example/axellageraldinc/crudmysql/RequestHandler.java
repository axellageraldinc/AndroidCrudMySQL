package com.example.axellageraldinc.crudmysql;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by axellageraldinc on 9/11/17.
 */

public class RequestHandler {

    //Method untuk mengirim HttpRequest
    public String sendPostRequest(String requestUrl, HashMap<String, String> params){
        URL url;

        //StringBuilder untuk store message yang diterima
        StringBuilder sb = new StringBuilder();
        try{
            //Membuat url dari parameter requestUrl
            url = new URL(requestUrl);

            //Open connection url nya.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //Setting properties koneksinya
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            bw.write(getPostDataString(params));
            bw.flush();
            bw.close();
            os.close();

            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;
                //Membaca response dari server
                while((response = br.readLine()) != null){
                    sb.append(response);
                }
            }
        } catch (Exception ex){
            System.out.println("Error sendPostRequest : " + ex.toString());
        }
        return sb.toString();
    }

    public String sendGetRequest(String requestUrl){
        StringBuilder sb = new StringBuilder();
        try{
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String s;
            while((s = br.readLine()) != null){
                sb.append(s + "\n");
            }
        } catch (Exception ex){
            System.out.println("Error send Get Request : " + ex.toString());
        }
        return sb.toString();
    }

    public String getPostDataString(HashMap<String, String> params){
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()){
            if (first){
                first = false;
            } else{
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey()));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue()));
        }
        return result.toString();
    }
}
