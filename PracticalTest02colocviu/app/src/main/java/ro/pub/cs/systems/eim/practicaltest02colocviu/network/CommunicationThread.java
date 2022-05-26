package ro.pub.cs.systems.eim.practicaltest02colocviu.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;
import ro.pub.cs.systems.eim.practicaltest02colocviu.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02colocviu.general.Utilities;

public class CommunicationThread extends Thread {

    private ServerThread serverThread;
    private Socket socket;
    private HashMap<String, String> data = null;

    public CommunicationThread(ServerThread serverThread, Socket socket, HashMap data) {
        this.serverThread = serverThread;
        this.socket = socket;
        this.data = data;
    }

    @Override
    public void run() {
        try {
            Log.v(Constants.TAG, "Connection opened to " + socket.getLocalAddress() + ":" + socket.getLocalPort()+ " from " + socket.getInetAddress());
            PrintWriter printWriter = Utilities.getWriter(socket);
            printWriter.println(data.toString());
            socket.close();
            Log.v(Constants.TAG, "Connection closed");
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }

}

