package ro.pub.cs.systems.eim.practicaltest02colocviu.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest02colocviu.R;
import ro.pub.cs.systems.eim.practicaltest02colocviu.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02colocviu.network.ClientThread;
import ro.pub.cs.systems.eim.practicaltest02colocviu.network.ServerThread;

public class MainActivity extends AppCompatActivity {

    // Server widgets
    private EditText serverPortEditText = null;
    private Button connectButton = null;

    private EditText clientAddressEditText = null;
    private EditText clientPortEditText = null;
    private EditText keyEditText = null;
    private EditText valueEditText = null;

    private Button getData = null;
    private Button postData = null;

    private TextView weatherForecastTextView = null;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;

    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Constants.TAG, "[MAIN ACTIVITY] onCreate() callback method has been invoked");
        setContentView(R.layout.activity_main);

        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
        connectButton = (Button)findViewById(R.id.connect_button);
        connectButton.setOnClickListener(connectButtonClickListener);

        clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
        clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
        keyEditText = (EditText)findViewById(R.id.city_edit_text);
        valueEditText = (EditText)findViewById(R.id.value_edit_text);
        getData = (Button)findViewById(R.id.get_weather_forecast_button);
        postData = (Button)findViewById(R.id.get_weather_forecast_button2);
        // getData.setOnClickListener(getDataClickListener);
        weatherForecastTextView = (TextView)findViewById(R.id.weather_forecast_text_view);
    }

    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, "[MAIN ACTIVITY] onDestroy() callback method has been invoked");
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
}