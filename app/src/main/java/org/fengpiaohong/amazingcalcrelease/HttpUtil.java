package org.fengpiaohong.amazingcalcrelease;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 乘耀 on 2017/2/17.
 */

class HttpUtil {
    private String urlString;

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    HttpUtil(){

    }
    HttpUtil(String url){
        this.urlString=url;
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void doGet(final HttpResponse response){
        final StringBuffer htmlContent=new StringBuffer();
        new AsyncTask<String,Void,String>(){

            @Override
            protected void onPostExecute(String s) {
                response.response(s);
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoInput(true);
                    InputStream is = con.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is,"utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    while ((line=br.readLine())!=null){
                        htmlContent.append(line);
                    }
                    br.close();
                    isr.close();
                    is.close();
                    return  htmlContent.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }
        }.execute(urlString);
    }


    public void doPost(final HttpResponse response, final String postData){
        final StringBuffer htmlContent=new StringBuffer();
        new AsyncTask<String,Void,String>(){

            @Override
            protected void onPostExecute(String s) {
                response.response(s);
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestMethod("POST");

                    OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(),"utf-8");
                    BufferedWriter bw = new BufferedWriter(osw);
                    bw.write(postData);
                    bw.flush();


                    InputStream is = con.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is,"utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    while ((line=br.readLine())!=null){
                        htmlContent.append(line);
                    }
                    br.close();
                    isr.close();
                    is.close();
                    return  htmlContent.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }
        }.execute(urlString);
    }














}
