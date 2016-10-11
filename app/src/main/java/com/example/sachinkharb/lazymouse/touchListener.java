package com.example.sachinkharb.lazymouse;

import android.app.Activity;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.sachinkharb.lazymouse.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class touchListener extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;
    public TCPClient mTcpClient;
    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;
    public  Point  p;
    public Point getsize()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Tried like hell to try to read default hideUi(),
        // but it's a bitch.
        // This is the lazy workaraoud
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_touch_listener);
    //    Toast.makeText(this, "Connected " + TCPClient.SERVERIP, Toast.LENGTH_SHORT).show();
      p=getsize();
    }
    Point p1=new Point();
    int x1,y1;
    boolean mover;
    public Point jittCorr(int x2,int y2)
    { float delx,dely;
        delx=x2-x1; dely=y2-y1;
      float delX_perc =Math.abs(delx/(float) p.x);
      float delY_perc= Math.abs( dely/(float) p.y);
        if(delX_perc <0.05)
        {
            x2=x1;
        }
        if(delX_perc <0.05)
        { y2=y1;
        }
        if((delX_perc < 0.05)&& (delY_perc <0.05))
        {
           mover=false;
        }
        p1.x=x2;
        p1.y=y2;
        x1=x2;
        y1=y2;
        return p1;
    }
    int x,y;
    @Override
    public boolean onTouchEvent(MotionEvent event )
    {  TextView t= (TextView)findViewById(R.id.textView2 );
       /* if (event.getAction() == MotionEvent.ACTION_MOVE  )
        {
            //  Toast.makeText(this, "x= " + event.getX() + "\t y=" + event.getY(), Toast.LENGTH_SHORT).show() ;
        // Why the fuck did i do this, what a horrible idea !

            t.setText("x= " + Math.round(event.getX()) + "\t y=" + Math.round((event.getY())));
            String message="Move,"+Math.round(event.getX()) + "," + Math.round(event.getY());

            new connectTask().execute("");
            //sends the message to the server

            if (mTcpClient != null) {

                mTcpClient.sendMessage(message);
            }

            //send();

        }*/
        Point p3=new Point() ;

        if (event.getAction() == MotionEvent.ACTION_MOVE   )
        {

            t.setText("x= " + Math.round(event.getX()) + "\t y=" + Math.round((event.getY())));
            x=Math.round(event.getX());
            y=Math.round(event.getY());


            //  p3=jittCorr(x,y);

            String message="move,"+Math.round(x*MainActivity .xSc) + "," + Math.round(y*MainActivity .ySc);
            // t.setText(TCPClient.err );
            new connectTask().execute("");
            //sends the message to the server

            if (mTcpClient != null) {

                mTcpClient.sendMessage(message);
            }

            //send();

        }
        if (event.getAction() == MotionEvent.ACTION_DOWN   )
        {

            t.setText("x= " + Math.round(event.getX()) + "\t y=" + Math.round((event.getY())));
            x=Math.round(event.getX());
            y=Math.round(event.getY());


            //  p3=jittCorr(x,y);

            String message="lmdn,"+Math.round(x*MainActivity .xSc) + "," + Math.round(y*MainActivity .ySc);
            // t.setText(TCPClient.err );
            new connectTask().execute("");
            //sends the message to the server

            if (mTcpClient != null) {

                mTcpClient.sendMessage(message);
            }

            //send();

        }




        return super.
                onTouchEvent(event );
    }
    public void lefty(View v)
    {

        String message="lmdn,"+Math.round(x*MainActivity .xSc) + "," + Math.round(y*MainActivity .ySc);
        // t.setText(TCPClient.err );
        new connectTask().execute("");
        //sends the message to the server

        if (mTcpClient != null) {

            mTcpClient.sendMessage(message);
        }


    }
    public void righty(View v)
    {

        String message="rtdn,"+Math.round(x*MainActivity .xSc) + "," + Math.round(y*MainActivity .ySc);
        // t.setText(TCPClient.err );
        new connectTask().execute("");
        //sends the message to the server

        if (mTcpClient != null) {

            mTcpClient.sendMessage(message);
        }


    }

    public class connectTask extends AsyncTask<String,String,TCPClient> {

        @Override
        protected TCPClient doInBackground(String... message) {

            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override

                public void messageReceived(String message) {

                    publishProgress(message);
                }
            });



            mTcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }


    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            // TODO: If Settings has multiple levels, Up should navigate up
            // that hierarchy.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





  }
