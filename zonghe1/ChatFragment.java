package com.example.zonghe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ChatFragment extends Fragment {
    EditText etip,etreceiveport,etzhenceport,etmess;
    Button btnok,btnsend;
    TextView tvshow;
    InetAddress inetaddress = null;
    DatagramPacket pack;
    DatagramSocket sendsocket=null,receivesocket=null;
    Message m;
    String sendmessage,ip;
    int receiveport,zhenceport;

    static final int RECEIVE_WHAT=1,SEND_WHAT=2;

    

//    public ChatFragment() {
//        // Required empty public constructor
//    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.activity_chat, container, false);
        View view = View.inflate(getActivity(),R.layout.activity_chat,null);
        //初始化
        etip = (EditText) view.findViewById(R.id.et_ip);
        etreceiveport = (EditText)view.findViewById(R.id.et_port);
        etzhenceport = (EditText)view.findViewById(R.id.et_port);
        etmess = (EditText)view.findViewById(R.id.et_mess);
        btnok = (Button)view.findViewById(R.id.btn_ok);
        btnsend = (Button)view.findViewById(R.id.btn_send);
        tvshow = (TextView)view.findViewById(R.id.tv_show);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = etip.getText().toString();
                receiveport = Integer.parseInt(etreceiveport.getText().toString());
                zhenceport = Integer.parseInt(etzhenceport.getText().toString());
                new Thread(new ReceiveMessage()).start();
            }
        });
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmessage = etmess.getText().toString();
                new Thread(new SendMessage()).start();
            }
        });
        return view;
    }


    public class SendMessage implements Runnable{

        @Override
        public void run() {

            byte[] buffer = sendmessage.getBytes();
            try {


                inetaddress = InetAddress.getByName(ip);


                pack = new DatagramPacket(buffer,buffer.length,inetaddress,receiveport);
                sendsocket = new DatagramSocket();
                sendsocket.send(pack);
            } catch (UnknownHostException ex) {
                ex.printStackTrace();
            }
            catch (SocketException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle();
            bundle.putString("send",sendmessage);
            m = new Message();
            m.what = SEND_WHAT;
            m.setData(bundle);

            handler.sendMessage(m);
            sendsocket.close();
        }
    }
    public class ReceiveMessage implements Runnable{
        byte data[] = new byte[8192];
        String message = null,str;

        @Override
        public void run() {
            pack = new DatagramPacket(data,data.length);
            try {
                receivesocket = new DatagramSocket(null);
                receivesocket.setReuseAddress(true);
                receivesocket.bind(new InetSocketAddress(zhenceport));
            } catch (SocketException e) {
                e.printStackTrace();
            }
            while(!"quit".equalsIgnoreCase(message))
            {
                try {
                    receivesocket.receive(pack);
                    message = new String(pack.getData(),0,pack.getLength());
                    str = pack.getAddress().getHostAddress();
                    m = new Message();
                    m.what=RECEIVE_WHAT;

                    Bundle bundle = new Bundle();
                    bundle.putString("receiveip",str);
                    bundle.putString("receivedata",message);
                    m.setData(bundle);
                    Thread.sleep(100);
                    handler.sendMessage(m);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            receivesocket.close();
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case RECEIVE_WHAT:
                    tvshow.append("收到"+msg.getData().getString("receiveip")+"的消息："+
                            msg.getData().getString("receivedata")+"\n");
                    break;
                case SEND_WHAT:
                    tvshow.append("发送的消息："+msg.getData().getString("send").toString()+"\n");
                    break;
            }
        }
    };
        
    
}