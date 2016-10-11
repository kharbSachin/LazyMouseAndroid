package com.example.sachinkharb.lazymouse;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {


    static public TCPClient mTcpClient;
    static public float xSc=1.0f;
    static public float ySc=1.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = (EditText) findViewById(R.id.edit_message);
        editText.setHint(TCPClient.SERVERIP ) ;
         editText.setText(TCPClient.SERVERIP )      ;
        Button send = (Button)findViewById(R.id.send_button);

        final Point p= getsize();
        String sizestr= "Phone Screen Size :"+p.x+","+p.y;

        TextView t = (TextView )findViewById(R.id.textView );
        t.setText(sizestr );




        Button b= (Button) findViewById(R.id.close_butt );
        EditText wt= (EditText ) findViewById(R.id.wText);
        EditText ht= (EditText ) findViewById(R.id.hText);
        wt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String input;
                int w=0;

                float sw=0f;

                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    input= v.getText().toString();
                    if(input !=null) {
                        w = Integer.parseInt(input);
                        TextView t = (TextView) findViewById(R.id.x_scaling);
                        xSc = (float )w / (float )p.x;
                        t.setText("X scale = " + xSc);
                    }return true; // consume.
                }
                return false;
            }
        });
        wt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String input;
                EditText e= (EditText )v;
              if(!hasFocus ) {
                  input = e.getText().toString();
                 if(input!=null)
                 {
                     try{int w = Integer.parseInt(input);
                  TextView t = (TextView) findViewById(R.id.x_scaling);
                  xSc = (float )w /(float ) p.x;
                  t.setText("X scale = " + xSc);
              } catch(Exception ex)
                     {}
                 }

              }
            }
        });
        ht.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String input;
                int h=0;

                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    input = v.getText().toString();
                   if(input!=null) {
                      try{ h = Integer.parseInt(input);
                       TextView t = (TextView) findViewById(R.id.y_scaling);
                       ySc = (float ) h /(float ) p.y;
                       t.setText("Y scale = " + ySc);}
                      catch(Exception e)
                      {}
                   }
                    return true; // consume.
                }
                    return false;
            }
        }) ;
        ht.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String input;
                EditText e= (EditText )v;
                if(!hasFocus ) {
                    input = e.getText().toString();
                    if(input!=null)
                    {
                        try{int w = Integer.parseInt(input);
                            TextView t = (TextView) findViewById(R.id.y_scaling);
                            xSc = (float )w /(float ) p.y;
                            t.setText("Y scale = " + xSc);
                        } catch(Exception ex)
                        {}
                    }

                }
            }
        });
        /*b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //              String message = editText.getText().toString();
                new connectTask().execute("");
                //sends the message to the server
                if (mTcpClient != null) {

                    mTcpClient.sendMessage("quit");
                }

                editText.setText("closed");
            }
        });*/
    }
    float x;
    float y;
/*
  */  @Override
    public boolean onCreateOptionsMenu(Menu menu) {

      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

  public Point getsize()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public void onConClick(View view) {
            EditText e=(EditText )findViewById(R.id.edit_message );
        String message = e.getText().toString();

        EditText wt= (EditText ) findViewById(R.id.wText);
        EditText ht= (EditText ) findViewById(R.id.hText);
        String xr,yr;
        xr=wt.getText().toString() ; yr=ht.getText().toString();
        String s;
        if((xr!=null) ) {
            if (message != null) {
                try {

                    if (TCPClient.setip(message)) {//Holy shit!!!! why does this work
                     s=TCPClient.SERVERIP;

                        Toast.makeText(this, "Connecting to " + s , Toast.LENGTH_SHORT).show();



                        Intent myIntent = new Intent(this, touchListener.class);

                        this.startActivity(myIntent);


                    }
                    else {

                        Toast.makeText(this, "Please Enter a valid Ip address", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e2) {
                    Toast.makeText(this, "Ohh fuck" + e2.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        /*
        //sends the message to the server
        if (mTcpClient != null) {

            mTcpClient.sendMessage(message);
        }*/

        }
        if((xr==null)&&(yr!=null))
        {          Toast.makeText(this, "Bro, Enter the width", Toast.LENGTH_SHORT ).show();}
        if((xr!=null)&&(yr==null))
        {          Toast.makeText(this, "Bro, Enter the height", Toast.LENGTH_SHORT ).show();}
        if((xr==null)&&(yr==null))
        {          Toast.makeText(this, "You are kiddding ..right?", Toast.LENGTH_SHORT ).show();}

//        e.setText("");
    }

}
