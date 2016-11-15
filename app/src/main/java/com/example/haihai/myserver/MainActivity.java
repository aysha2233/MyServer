package com.example.haihai.myserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.eclipse.californium.examples.SecureServer;
import org.eclipse.californium.examples.SmsSecureServer;

import cn.sms.util.SmsSocket;
import cn.sms.util.SmsSocketAddress;

public class MainActivity extends AppCompatActivity {
    private  SmsSocket smsSocket;
    private boolean isStarted = false;
    private TextView txtInfo = null;
    private EditText editPhoneNumber;
    private EditText editPort;
    private EditText editLocalPort;

    private Worker smsWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtInfo = (TextView)findViewById(R.id.textView);

        Button btnServer = (Button)findViewById(R.id.Server);
        btnServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStarted) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SecureServer server = new SecureServer();
                            server.startServer();
                        }
                    }).start();

                    isStarted = true;
                    txtInfo.setText("DTLS server started!!!");
                }
            }
        });


        editPhoneNumber = (EditText)findViewById(R.id.PhoneNumber);
        editPort = (EditText)findViewById(R.id.Port);
        editLocalPort = (EditText)findViewById(R.id.LocalPort);

        Button btnSmsServer = (Button)findViewById(R.id.SmsServer);
        btnSmsServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStarted) {
                    smsSocket = new SmsSocket(MainActivity.this, editLocalPort.getText().toString());
                    smsSocket.init();
                    SmsSocketAddress dstSmsSocketAddress = new SmsSocketAddress(editPhoneNumber.getText().toString(), Short.parseShort(editPort.getText().toString()));
                    SmsSecureServer server = new SmsSecureServer(smsSocket, dstSmsSocketAddress);
                    server.startServer();

                    isStarted = true;
                    txtInfo.setText("DTLS server started!!!");
                    v.setEnabled(false);
                }
            }
        });
    }

    private abstract class Worker extends Thread {
        @Override
        public void run() {
            doWork();
        }
        protected abstract void doWork();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        smsSocket.unInit();
    }
}
