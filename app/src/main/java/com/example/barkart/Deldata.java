package com.example.barkart;

import android.os.AsyncTask;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Deldata extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... strings) {


        try {

            String classs = "com.mysql.jdbc.Driver";

            String url = "jdbc:mysql://192.168.43.114:3306/test";

            String un = "test";

            String password = "test";

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);

            //Connection conn = null;
            String ConnURL = null;


            Class.forName(classs).newInstance();

            Connection conn = DriverManager.getConnection(url, un, password);
            String query = "delete from bill where uid='"+1+"'";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            for(int o=0;o<MainActivity.strArray.length;o++)
            {
                MainActivity.strArray[MainActivity.x]="";

            }
            MainActivity.x=0;

        }
        catch (Exception e)
        {

        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        MainActivity.tt.setText("Payment Gatway WIP");
    }




}
