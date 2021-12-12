package com.example.project6;

import android.content.ContentValues;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RequestHttpConnection {
    public String request(String _url, ContentValues params){
        HttpURLConnection urlConn= null;
        StringBuffer sbParams =new StringBuffer();

        if(params==null)
            sbParams.append("");
        else
        {
            boolean isAnd=false;
            String key;
            String value;
            for(Map.Entry<String,Object> parameter: params.valueSet())
            {
                key=parameter.getKey();
                value=parameter.getValue().toString();
                if(isAnd)
                    sbParams.append("&");

                sbParams.append(key).append("=").append(value);

                if(!isAnd)
                    if(params.size()>1)
                        isAnd=true;
            }
        }
        try {
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("Accept-Charset", "UTP - 8");
            urlConn.setRequestProperty("ConText_Type", "application/json");
            urlConn.setDoInput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;
            String line;
            String page = "";
            while ((line = br.readLine()) != null) {
                page += line;

            }
            return page;
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(urlConn!=null)
                urlConn.disconnect();
        }
        return null;
    }


    public String requestSearch(String _url, String Address){
        HttpURLConnection urlConn= null;
        StringBuffer sbParams =new StringBuffer();
        String json="";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.accumulate("address", Address);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept-Charset", "application/json");
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);

            json=jsonObject.toString();
            OutputStream os=urlConn.getOutputStream();
            os.write(json.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            int check=urlConn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;
            String line;
            String page = "";
            while ((line = br.readLine()) != null) {
                page += line;
            }
            return page;
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if(urlConn!=null)
                urlConn.disconnect();
        }
        return null;
    }



}
