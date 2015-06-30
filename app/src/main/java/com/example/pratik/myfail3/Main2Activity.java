package com.example.pratik.myfail3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
    public static final String gettotalfan="com.example.pratik.myfail3.Message7";
    public static final String gettotalbulb="com.example.pratik.myfail3.Message8";
    public static final String finalinetaddr="com.example.pratik.myfail3.Message9";
    public static final String finalport="com.example.pratik.myfail3.Message10";
    public static final String finalusername="com.example.pratik.myfail3.Message11";
    Intent intent7;
    String totalfan;
    String totalbulb;
    EditText editText1;
    EditText editText2;
    Intent intent8;
    String ip;
    String port;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        intent8=getIntent();
        ip = intent8.getStringExtra(HomeScreen.inetaddr);
        port=intent8.getStringExtra(HomeScreen.port);
        username =intent8.getStringExtra(HomeScreen.username);
    }

    public void sendNext(View view){
        intent7 = new Intent(this,MainActivity.class);
        editText1=(EditText) findViewById(R.id.editText1);
        totalfan=editText1.getText().toString();

        editText2=(EditText) findViewById(R.id.editText2);
        totalbulb=editText2.getText().toString();

        intent7.putExtra(gettotalfan, totalfan);
        intent7.putExtra(gettotalbulb,totalbulb);
        intent7.putExtra(finalinetaddr,ip);
        intent7.putExtra(finalport,port);
        intent7.putExtra(finalusername,username);
        startActivity(intent7);
        // fetch data
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
