package com.example.haihai.myserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.californium.examples.SecureServer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtInfo = (TextView)findViewById(R.id.textView);

        Button btnServer = (Button)findViewById(R.id.server_btn);
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
    }

    private boolean isStarted = false;
    private TextView txtInfo = null;
}
