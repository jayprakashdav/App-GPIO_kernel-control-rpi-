package com.example.pratik.myfail3;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView[] textviews;
    public Switch[] switches;
    int totalfan;
    int totalbulb;
    public Object Synctoken;
    public String ip ;
    public int port;
    public volatile String message="hii";
    public int count;
    Intent intent9;
    String username;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout=(RelativeLayout)findViewById(R.id.mainactivity);
        count=0;
        Synctoken=new Object();
        intent9=getIntent();
        totalfan=Integer.parseInt(intent9.getStringExtra(Main2Activity.gettotalfan));
        totalbulb=Integer.parseInt(intent9.getStringExtra(Main2Activity.gettotalbulb));
        ip=intent9.getStringExtra(Main2Activity.finalinetaddr);
        port=Integer.parseInt(intent9.getStringExtra(Main2Activity.finalport));
        username=intent9.getStringExtra(Main2Activity.finalusername);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        textviews=new TextView[totalfan+totalbulb];
        switches=new Switch[totalfan+totalbulb];
        textviews[0]=(TextView)findViewById(R.id.textView1);
        switches[0]=(Switch)findViewById(R.id.switch1);

        /*
        textviews=new TextView[]{(TextView)findViewById(R.id.textView1),(TextView)findViewById(R.id.textView2),(TextView)findViewById(R.id.textView3),
                (TextView)findViewById(R.id.textView4),(TextView)findViewById(R.id.textView5),(TextView)findViewById(R.id.textView6),
                (TextView)findViewById(R.id.textView7),(TextView)findViewById(R.id.textView8),(TextView)findViewById(R.id.textView9)};

        switches=new Switch[]{(Switch)findViewById(R.id.switch1),(Switch)findViewById(R.id.switch2),(Switch)findViewById(R.id.switch3),
                (Switch)findViewById(R.id.switch4),(Switch)findViewById(R.id.switch5),(Switch)findViewById(R.id.switch6),
                (Switch)findViewById(R.id.switch7),(Switch)findViewById(R.id.switch8),(Switch)findViewById(R.id.switch9)};


        RelativeLayout.LayoutParams params1 = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //params1 = (RelativeLayout.LayoutParams) textviews[totalbulb+totalfan-1].getLayoutParams();
        params1.addRule(RelativeLayout.BELOW,textviews[totalbulb+totalfan-1].getId());
        params1.addRule(RelativeLayout.ALIGN_START,textviews[totalbulb+totalfan-1].getId());
        textView=new TextView(this);
        textView.setLayoutParams(params1);
        textView.setPadding(10, 0, 10, 10);
        textView.setTextSize(20);
        textView.setText("CHECK_EXTRA");
        textView.setId(textView.generateViewId());
        relativeLayout.addView(textView);
        */

        //Thread receiverThread=new Thread(new ReceiverThread());
        //receiverThread.start();

        for(int i=0;i<totalfan;i++){

            if(i>0){
                textviews[i]=new TextView(this);
                switches[i]=new Switch(this);
                textviews[i].setId(textviews[i-1].getId()+1);
                switches[i].setId(textviews[i-1].getId()+1);

                RelativeLayout.LayoutParams params1 = new
                        RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params1.addRule(RelativeLayout.BELOW,textviews[i-1].getId());
                params1.addRule(RelativeLayout.ALIGN_START,textviews[i-1].getId());
                textviews[i].setLayoutParams(params1);
                textviews[i].setPadding(10,0,10,10);
                textviews[i].setTextSize(20);

                RelativeLayout.LayoutParams params2 = new
                        RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params2.addRule(RelativeLayout.ALIGN_BOTTOM,textviews[i].getId());
                params2.addRule(RelativeLayout.END_OF,textviews[i].getId());
                switches[i].setLayoutParams(params2);
                switches[i].setPadding(0,0,0,16);
                switches[i].setOnClickListener(this);
                textviews[i].setText(" FAN_"+(i+1));
                textviews[i].setTag((i+1)+"_");
                switches[i].setTag((i+1)+"_");
                switches[i].setTextOn("on");
                switches[i].setTextOff("off");
                switches[i].setShowText(true);
                switches[i].setThumbTextPadding(switches[0].getThumbTextPadding());
                relativeLayout.addView(switches[i]);
                relativeLayout.addView(textviews[i]);
            }
            else{

                textviews[i].setText("FAN_"+(i+1));
                textviews[i].setTag((i+1)+"_");
                switches[i].setTag((i+1)+"_");
                switches[i].setTextOn("on");
                switches[i].setTextOff("off");
                switches[i].setShowText(true);

            }

        }

        for(int i=0;i<totalbulb;i++){
            if(i==0 && totalfan==0){
                textviews[0].setText("BULB_"+(i+1));
                textviews[0].setTag((i+1)+"_");
                switches[0].setTag((i+1) + "_");
                switches[0].setTextOn("on");
                switches[0].setTextOff("off");
                switches[0].setShowText(true);
            }else{

                textviews[i+totalfan]=new TextView(this);
                switches[i+totalfan]=new Switch(this);
                textviews[i+totalfan].setId(textviews[i-1+totalfan].getId()+1);
                switches[i+totalfan].setId(textviews[i-1+totalfan].getId()+1);

                RelativeLayout.LayoutParams params3 = new
                        RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params3.addRule(RelativeLayout.BELOW,textviews[i-1+totalfan].getId());
                params3.addRule(RelativeLayout.ALIGN_START,textviews[i-1+totalfan].getId());
                textviews[i+totalfan].setLayoutParams(params3);
                textviews[i+totalfan].setPadding(10,0,10,10);
                textviews[i+totalfan].setTextSize(20);

                RelativeLayout.LayoutParams params4 = new
                        RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params4.addRule(RelativeLayout.ALIGN_BOTTOM,textviews[i+totalfan].getId());
                params4.addRule(RelativeLayout.END_OF,textviews[i+totalfan].getId());
                switches[i+totalfan].setLayoutParams(params4);
                switches[i+totalfan].setPadding(0,0,0,16);
                switches[i+totalfan].setThumbTextPadding(switches[0].getThumbTextPadding());
                switches[i+totalfan].setOnClickListener(this);
                textviews[i+totalfan].setText("BULB_"+(i+1));
                textviews[i+totalfan].setTag((i+totalfan+1)+"_");
                switches[i+totalfan].setTag((i + totalfan+1) + "_");
                switches[i+totalfan].setTextOn("on");
                switches[i+totalfan].setTextOff("off");
                switches[i+totalfan].setShowText(true);
                relativeLayout.addView(switches[i+totalfan]);
                relativeLayout.addView(textviews[i+totalfan]);

            }

        }

        /*
        for(int i=(totalbulb+totalfan);i<=8;i++){
            textviews[i].setVisibility(View.GONE);
            switches[i].setVisibility(View.GONE);
        }
        */



        Thread senderThread3 =new Thread(new SenderThread3(Synctoken));
        senderThread3.start();

        //Thread serverThread=new ServerThread();
        //serverThread.start();

    }

    @Override
    public void onClick(View v) {
        SendMsg2(v);
        return;
    }

    public class SenderThread3 implements Runnable {

        PrintWriter out;
        Socket socket=null;
        Object Synctoken;

        public SenderThread3(Object Synctoken){
            this.Synctoken=Synctoken;
        }
        @Override
        public void run() {

            try {
                socket=new Socket(ip,port);
                out =new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.println(totalbulb+totalfan);
                out.flush();
                /*

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                out.println(username);
                out.flush();
                */
                while(true ) {
                    //for(;check<count;check++) {
                    synchronized (Synctoken) {
                        try {
                            Synctoken.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        out.println(message);
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

    public void SendMsg2(View view){
        count=count+1;
        boolean on = ((Switch) view).isChecked();
        String str=view.getTag().toString();

        if(on){
            message=str+"1";
            synchronized (Synctoken){
                Synctoken.notifyAll();
            }
        }
        if(!on){
            message=str+"0";
            synchronized (Synctoken){
                Synctoken.notifyAll();
            }
        }
        //String str=editText.getText().toString();
        //updateHandler.post(new updateUIThread("SENT : "+str ,1));

        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
