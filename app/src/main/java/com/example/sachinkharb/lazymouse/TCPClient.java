package com.example.sachinkharb.lazymouse;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Sachin Kharb on 11-Jul-15.
 */
public class TCPClient {
    private String serverMessage;
    public static  String SERVERIP = "192.168.1.3";
    public static final int SERVERPORT = 8080;
    public static  String err="Nope";
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;
    DataOutputStream dataOutputStream = null;


    OutputStream os;

    public TCPClient(OnMessageReceived listener) {
        mMessageListener = listener;
    }

    public void sendMessage(String message) {

        if (out != null && !out.checkError()) {




        out.print(message);
        out.flush();
            out.close();

    }
}
    public static  boolean setip(String s)
    {
        try
    {            InetAddress serverAddr = InetAddress.getByName(s);
         SERVERIP = s;
//        Log.e("Ip Set");
    return true;
    }
    catch (Exception e)
    {  return false;}
    }
    public void stopClient(){
        mRun = false;
    }

    public void run() {

        mRun = true;

        try {

            InetAddress serverAddr = InetAddress.getByName(SERVERIP);

            Log.e("TCP Client", "C: Connecting...");

            Socket socket = new Socket(serverAddr, SERVERPORT);

            ///
            os = socket.getOutputStream();

            if(socket.isBound()){
                Log.i("SOCKET", "Socket: Connected");
            }
            else{
                Log.e("SOCKET", "Socket: Not Connected");
            }
            try {

                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                Log.e("TCP Client", "C: Sent.");

                Log.e("TCP Client", "C: Done.");

                if(out.checkError())
                {
                    Log.e("PrintWriter", "CheckError");
                }

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (mRun) {
                    serverMessage = in.readLine();

                    if (serverMessage != null && mMessageListener != null) {
                        mMessageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;

                }


                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");


            } catch (Exception e) {

                    Log.e("TCP", "S: Error", e);
                    err=e.getLocalizedMessage().toString();
            } finally {

                socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }

    }

    public interface OnMessageReceived {
        public void messageReceived(String message);
    }

}
