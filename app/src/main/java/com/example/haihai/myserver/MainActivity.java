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
                    SecureServer server = new SecureServer();
                    server.startServer();
                    isStarted = true;
                    txtInfo.setText("DTLS server started!!!");
                }
            }
        });

        // init sms environment
        initSmsSocket();

        final EditText editPhoneNumber = (EditText)findViewById(R.id.PhoneNumber);
        final EditText editPort = (EditText)findViewById(R.id.Port);

        Button btnSmsServer = (Button)findViewById(R.id.SmsServer);
        btnSmsServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStarted) {
                    SmsSocketAddress smsSocketAddress = new SmsSocketAddress(editPhoneNumber.getText().toString(),
                            Short.parseShort(editPort.getText().toString()));
                    SmsSecureServer server = new SmsSecureServer(smsSocket, smsSocketAddress);
                    server.startServer();
                    isStarted = true;
                    txtInfo.setText("DTLS server started!!!");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        smsSocket.unInit();
    }

    private void initSmsSocket() {
        smsSocket = new SmsSocket(this, "8091");
        smsSocket.init();
    }
}
