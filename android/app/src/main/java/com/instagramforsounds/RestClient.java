/*Copyright 2014 Bhavit Singh Sengar
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.infotech.zeus.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;

import java.io.File;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import android.util.Log;

public class RestClient {


        JSONObject data = new JSONObject();
        String url;
        String headerName;
        String headerValue;

        public RestClient(String s){

            url = s;
        }


        public void addHeader(String name, String value){

            headerName = name;
            headerValue = value;

        }

        public void addParam(String key, String value){

            try {
                data.put(key, value);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

        public String executePost(){  // If you want to use post method to hit server

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader(headerName, headerValue);
            HttpResponse response = null;
            String result = null;
            try {
                StringEntity entity = new StringEntity(data.toString(), HTTP.UTF_8);
                httpPost.setEntity(entity);
                response = httpClient.execute(httpPost);
                HttpEntity entity1 = response.getEntity();
                result = EntityUtils.toString(entity1);
                Log.d("<- exceutePost", result);
                return result;
                //Toast.makeText(MainPage.this, result, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return result;



        }

        public String executePostAndSendFile(File file, String title){

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            //httpPost.setHeader(headerName, headerValue);
            HttpResponse response = null;
            String result = null;
            try {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

                String filename = file.getName();

                String json = "{\"filename\":\"" + filename + "\",\"title\":\"" + title + "\"}";
                builder.addPart("file", new FileBody(file));
                builder.addTextBody("info", json, ContentType.APPLICATION_JSON);
                // builder.addBinaryBody("file", file, ContentType.create("audio/3gp"), file.getName());
                // builder.addTextBody("json", data.toString());
                HttpEntity entity = builder.build();
                httpPost.setEntity(entity);
                response = httpClient.execute(httpPost);
                HttpEntity entity1 = response.getEntity();
                result = EntityUtils.toString(entity1);
                Log.d("<- exceutePostAndSendFile", result);
                return result;
                //Toast.makeText(MainPage.this, result, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return result;
        }

        public String executeGet(){ //If you want to use get method to hit server

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            String result = null;
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                result = httpClient.execute(httpget, responseHandler);
                Log.d("<- executeGet", result);
                return result;
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return result;

        }
}
