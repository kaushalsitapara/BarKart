package com.example.barkart;

import android.os.AsyncTask;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Recivedata extends AsyncTask<String,String,String> {
    int total=0;
    String Data1 ="";
    String z="0";

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



            Class.forName(classs).newInstance();

            int a=0;
            //int b=10;

            Connection conn = DriverManager.getConnection(url, un, password);

            Statement stmt = conn.createStatement();

            Statement stmt1 = conn.createStatement();
            int c=1;
            String name="";
            String price="";
            String bcodedb="";
            String weightdb="";
            String bcodesc="";

            for(a=0;a<=MainActivity.x;a++){
                String query = "select bcode from bill where val = '" + a + "'";


                ResultSet rs = stmt.executeQuery(query);
                while(rs.next())
                {
                bcodesc = bcodesc + rs.getString("bcode");}
                String query1 = "select * from barcode where bcode = '" + bcodesc + "'";
                ResultSet rs1 = stmt1.executeQuery(query1);
                while(rs1.next()) {z="1";
                    bcodedb = bcodedb + rs1.getString("bcode");

                    price = price + rs1.getString("price");
                    total =total+ Integer.parseInt(price);
                    name = name + rs1.getString("name");

                    weightdb += rs1.getString("weight");
                }

                Data1= Data1+"\n" + bcodedb+"     "+name+"    "+price;

                bcodesc = "";
                bcodedb="";
                price="";
                name="";
                weightdb="";



            }
        }

        catch (SQLException e) {
            z=z+e;

        } catch (Exception ex) {
            z=z+ex;

        }




        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        MainActivity.ft.setText(Data1);
        String amt=Integer.toString(total);
        MainActivity.tt.setText(amt);
    }

}

