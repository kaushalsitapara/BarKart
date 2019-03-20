package com.example.barkart;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Sendata extends AsyncTask<String,String,String> {



  //  ProgressDialog progressDialog;


        String barcode=MainActivity.SharedStr;
        int valstr=MainActivity.x;
        String z="";
        String uid="1";

        boolean isSuccess=false;

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
               // String ConnURL = null;


                Class.forName(classs).newInstance();

                Connection conn = DriverManager.getConnection(url, un, password);
                int repeat=0;
                MainActivity.strArray[MainActivity.x]="0";
                for(int o=0;o<MainActivity.strArray.length;o++)
                {
                    if(barcode.equals(MainActivity.strArray[o]))
                    {

                        String query="delete from bill where bcode='"+barcode+"'";
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate(query);
                        z = "Delete Successful";
                        isSuccess = true;
                        repeat=1;
                    }
                }
                //conn = DriverManager.getConnection(ConnURL);
                if(repeat==0){
                if (conn == null || barcode.equals("Result") || barcode.equals("Sent successful") || barcode.equals("Delete successful") ) {

                    z = "Please check your internet connection or scan code first";

                } else {

                    int scwt=0;
                    String query = "insert into bill values('" + barcode + "','" + valstr + "','"+ uid +"','"+scwt+"')";

                    Statement stmt = conn.createStatement();

                    stmt.executeUpdate(query);


                    z = "Sent Successful";
                    isSuccess = true;
                    //valstr=valstr+1;
                    MainActivity.strArray[MainActivity.x]=barcode;
                    MainActivity.x=MainActivity.x+1;


                    //  }
                }}


                return null;
            } catch (SQLException e) {
                z = z + "05"+"Exceptions" + e;
                e.printStackTrace();
            } catch (Exception ex) {
                isSuccess = false;
                z = z + "03"+"Exceptions" + ex;
            }
            return null;
        }

            //  @Override
        //protected void onPreExecute() {
       //     progressDialog.setMessage("Loading...");
       //     progressDialog.show();
       // }

        @Override
        protected void onPostExecute(String s) {
            MainActivity.resultTextView.setText(z);
        }

}
