package com.example.pratik.myfail3;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Handler;


public class HomeScreen extends ActionBarActivity {

    public static final String inetaddr="com.example.pratik.myfail3.Message";
    public static final String port="com.example.pratik.myfail3.Message1";
    public static final String username="com.example.pratik.myfail3.Message2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
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
    public void sendMsg(View view){
        final Intent intent = new Intent(this,Main2Activity.class);
        EditText edit=(EditText) findViewById(R.id.editText);
        String msg=edit.getText().toString();

        EditText edit2=(EditText) findViewById(R.id.editText3);
        String portno=edit2.getText().toString();

        EditText edit3=(EditText) findViewById(R.id.editText4);
        String name=edit3.getText().toString();

        intent.putExtra(inetaddr, msg);
        intent.putExtra(port,portno);
        intent.putExtra(username,name);
        startActivity(intent);
            // fetch data
        }

    }




