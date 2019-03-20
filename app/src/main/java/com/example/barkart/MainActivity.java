package com.example.barkart;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    public static TextView resultTextView;
    public static TextView ft;
    public static TextView tt;
    Button Scan_but;
    Button Send_but;
    Button Del_but;
    public static String SharedStr="";
    public static String[] strArray = new String[100];;
    public static int x=0;
    //public static String count="011";
   // public static String count1="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView=(TextView)findViewById(R.id.txt1);
        ft=(TextView)findViewById(R.id.txtfinal);
        tt=(TextView)findViewById(R.id.txttotal);
        Scan_but=(Button)findViewById(R.id.butscan);
        Scan_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ScanCode.class));
            }
        });
        Send_but=(Button)findViewById(R.id.regbut);
        Send_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedStr=resultTextView.getText().toString();
                Sendata sd = new Sendata();
                sd.execute();
            }
        });
        Scan_but=(Button)findViewById(R.id.fetbut);
        Scan_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recivedata rd = new Recivedata();
                rd.execute();
            }
        });
        Del_but=(Button)findViewById(R.id.paybut);
        Del_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deldata dd = new Deldata();
                dd.execute();
            }
        });
    }


}
