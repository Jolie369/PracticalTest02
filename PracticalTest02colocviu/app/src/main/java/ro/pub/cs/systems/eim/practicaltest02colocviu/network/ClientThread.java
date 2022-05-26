package ro.pub.cs.systems.eim.practicaltest02colocviu.network;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02colocviu.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02colocviu.general.Utilities;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private String city;
    private String informationType;
    private TextView DataTextView;

    private String key;
    private String value;

    private Socket socket;

    public ClientThread(String address, int port, String key, String value, TextView DataTextView) {
        this.address = address;
        this.port = port;
        this.key = key;
        this.value = value;
        this.DataTextView = DataTextView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            printWriter.println(key);
            printWriter.flush();
            printWriter.println(value);
            printWriter.flush();
            String dataInformation;
            while ((dataInformation = bufferedReader.readLine()) != null) {
                final String finalizedDataInformation = dataInformation;
                DataTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        DataTextView.setText(finalizedDataInformation);
                    }
                });
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }

}
