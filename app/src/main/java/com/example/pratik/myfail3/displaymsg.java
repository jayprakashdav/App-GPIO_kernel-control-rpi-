package com.example.pratik.myfail3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.LogRecord;


public class displaymsg extends AppCompatActivity  {
    String ip="192.168.41.1";
    public  TextView textview;
    public  Handler updateHandler=new Handler(Looper.getMainLooper());
    int port=3996;
    public  String username;
    volatile int count=0;
    public volatile int counthost=0;
    public Object Synctoken2;
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaymsg);

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = new String[]{"ram","shyam","Don1"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mPlanetTitles));
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.drawable.ic_drawer,R.string.drawer_open,R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        textview = (TextView)findViewById(R.id.text);
        Synctoken2=new Object();
        Intent intent = getIntent();
        ip = intent.getStringExtra(HomeScreen.inetaddr);
        port=Integer.parseInt(intent.getStringExtra(HomeScreen.port));
        username =intent.getStringExtra(HomeScreen.username);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread receiverThread=new Thread(new ReceiverThread());
        receiverThread.start();

        Thread senderThread =new Thread(new SenderThread(Synctoken2));
        senderThread.start();

        Thread serverThread=new ServerThread();
        serverThread.start();

    }
    /*


    @Override
    public void onFragmentInteraction() {
        EditText editText5=(EditText)findViewById(R.id.editText5);
        EditText editText6=(EditText)findViewById(R.id.editText6);
        String ip2=editText5.getText().toString();
        int port2=Integer.parseInt(editText6.getText().toString());
        ip=ip2;
        port=port2;
        Thread thread1=new Thread(new ReceiverThread());
        thread1.start();

        Thread thread2=new Thread(new SenderThread(Synctoken2));
        thread2.start();

        transaction.remove(blankFragment);
        transaction.commit();

    }
    */


    /*

    public static class AddFragment extends Fragment {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.add_view, container, false);
        }

        OnHeadlineSelectedListener mCallback;
        public interface OnHeadlineSelectedListener {
            public void onArticleSelected();
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (OnHeadlineSelectedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }


    }





    */


    public  class ServerThread extends Thread {
        int count;
        Socket socket;
        ServerSocket serverSocket;

        @Override
        public void run() {
            count=0;
            socket = null;
            serverSocket = null;
            try {
                serverSocket = new ServerSocket(3996);
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (!Thread.currentThread().isInterrupted()) {
                try {

                    socket = serverSocket.accept();
                    counthost=counthost+1;
                    updateHandler.post(new updateUIThread("New host connected :- "+socket.getInetAddress(),1));
                    updateHandler.post(new updateUIThread("Total host connected till now :- "+counthost,1));
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String str = br.readLine();
                    if (str.equalsIgnoreCase("Receiver")) {
                        SenderThread2 senderThread2 = new SenderThread2(socket,Synctoken2);
                        new Thread(senderThread2).start();
                    } else {
                        ReceiverThread2 receiverThread2 = new ReceiverThread2(socket);
                        new Thread(receiverThread2).start();
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }
    }


    public class SenderThread implements Runnable {

        PrintWriter out;
        Socket socket=null;
        Object Synctoken;

        public SenderThread(Object Synctoken){
            this.Synctoken=Synctoken;
        }
        @Override
        public void run() {

            try {
                socket=new Socket(ip,port);
                out =new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.println("Sender");
                out.flush();
                out.println(username);
                out.flush();
                while(true ) {
                    //for(;check<count;check++) {
                        synchronized (Synctoken) {
                            try {
                                Synctoken.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            EditText editText = (EditText) findViewById(R.id.editText2);
                            String str = editText.getText().toString();
                            out.println(str);
                            out.flush();
                        }
                        //updateHandler.post(new updateUIThread("SENT : "+str ,1))
                    //}

                }

            } catch (IOException e){
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }

        }
    }

    public  class SenderThread2 implements Runnable {

        PrintWriter out;
        Socket socket=null;
        //int loccount;
        //int loccheck;
        Object Synctoken;
        public SenderThread2(Socket socket,Object Synctoken){
            this.Synctoken=Synctoken;
            this.socket=socket;
        }
        @Override
        public void run() {

            try {
                out =new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                //out.println("Sender");
                //out.flush();
                out.println(username);
                out.flush();
            } catch (Exception e){
                e.printStackTrace();
            }
                while(true ) {
                    synchronized (Synctoken) {
                        try {
                            Synctoken.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //loccount=count;
                        //loccheck=check;
                        //for(;loccheck<loccount;loccheck++) {
                        EditText editText = (EditText) findViewById(R.id.editText2);
                        String str = editText.getText().toString();
                        out.println(str);
                        out.flush();
                        //updateHandler.post(new updateUIThread("SENT : "+str ,1))
                        //}
                    }


                }



        }
    }



    class ReceiverThread implements Runnable {
        Socket socket=null;
        BufferedReader br;
        String msgin=null;
        PrintWriter out;
        String username3=null;

        @Override
        public void run() {
            try {
                socket=new Socket(ip,port);
                out =new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.println("Receiver");
                out.flush();
                br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                username3=br.readLine();

                while(true) {
                    try {
                        msgin = br.readLine();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!msgin.equals(null)) {

                        updateHandler.post(new updateUIThread("RECEIVED FROM "+username3+" :- " + msgin,0));
                    }

                }

            } catch (IOException e){
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }

        }
    }

     class ReceiverThread2 implements Runnable {
        Socket socket = null;
        BufferedReader br;
        String msgin=null;
        String username2=null;
        public ReceiverThread2(Socket socket){
            try {
                this.socket=socket;
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                username2 = br.readLine();
            }catch (IOException e) {
                e.printStackTrace();
            }catch (NullPointerException e) {
                e.printStackTrace();
            }


                while(true) {
                    try {
                        msgin = br.readLine();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!msgin.equals(null)) {

                        updateHandler.post(new updateUIThread("RECEIVED FROM "+username2+" : -" + msgin,0));
                    }
                }
        }
    }



    public void SendMsg(View view){
        count=count+1;
        synchronized (Synctoken2){
            Synctoken2.notifyAll();
        }
        EditText editText=(EditText)findViewById(R.id.editText2);
        String str=editText.getText().toString();
        updateHandler.post(new updateUIThread("SENT : "+str ,1));
        //Intent intent =new Intent(this,MyIntentService.class);
        //intent.putExtra(extra_msg2, str);
        /*Thread thread2 =new Thread(){
            public void run(){
                startService(intent);
            }
        };
        thread2.start();
        updateHandler.post(new updateUIThread("SENT : "+str));
        */


        return;
    }

     public  class updateUIThread implements Runnable {
        String str2;
         int b;
        public updateUIThread(String str3,int c) {
            synchronized (this) {
                str2 = str3;
                b=c;
            }

        }
        @Override
        public void run() {
            synchronized (this) {
                textview.setText(textview.getText().toString() + "\n" + str2);
                if(b==1){
                    EditText editText2 = (EditText) findViewById(R.id.editText2);
                    editText2.setText("");
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_displaymsg, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_addmem:
                //composeMessage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /*
    public  class BlankFragment2 extends Fragment implements View.OnClickListener {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        public EditText editText5,editText6;
        public Button button2;

        public  BlankFragment2 newInstance(String param1, String param2) {
            BlankFragment2 fragment = new BlankFragment2();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        public BlankFragment2() {

            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_blank, container, false);
            editText5=(EditText)view.findViewById(R.id.editText5);
            editText6=(EditText)view.findViewById(R.id.editText6);
            button2=(Button)view.findViewById(R.id.button2);
            editText5.setOnClickListener(this);
            editText6.setOnClickListener(this);
            button2.setOnClickListener(this);
            view.setOnClickListener(this);
            view.clearFocus();
            view.requestFocus();
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            // Inflate the layout for this fragment
            return view;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editText5:
                    editText5.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getActivity(). getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(getView(), InputMethodManager.SHOW_IMPLICIT);
                    break;
                case R.id.editText6:
                    editText5.requestFocus();
                    InputMethodManager img = (InputMethodManager)getActivity(). getSystemService(Context.INPUT_METHOD_SERVICE);
                    img.showSoftInput(getView(), InputMethodManager.SHOW_IMPLICIT);
                    break;
                case R.id.button2:
                    Connect(v);
                    break;
            }
        }
        public  void Connect(View view){
            String ip2=editText5.getText().toString();
            int port2=Integer.parseInt(editText6.getText().toString());
            ip=ip2;
            port=port2;
            Thread thread1=new Thread(new ReceiverThread());
            thread1.start();

            Thread thread2=new Thread(new SenderThread(Synctoken));
            thread2.start();

            transaction.remove(blankFragment);
            transaction.commit();

            return;
        }

    }
    */


}
