package com.example.minerva;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class Connect {
	String recvMessage;
	String sendMessage;
	DataOutputStream outToServer;
	
	Socket clientSocket = null;
	connectThread ct;
	
	public Connect() {
		ct = new connectThread();
		ct.start();
	}
	
	public class connectThread extends Thread {
		public void run() {
			TCPConnect();
		}
		
		public void TCPConnect() {
			try {
				clientSocket = new Socket("192.168.0.30", 7290);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				Log.e("unknown", "unknownhost");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("IO", "IOexception");
			}
			
			try {
				outToServer = new DataOutputStream(clientSocket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("outToServer", "Error");
			}
		}
	}
	
	public void DataSend(String command) {
		try {
			outToServer.writeBytes(command + '\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("writeBytes", "Error");
		}		
	}
}
