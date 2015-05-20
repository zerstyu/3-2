package com.example.minerva;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Connect_DB {
	final static String SERVER_NAME = "http://192.168.0.16/";
	public static final String GET_DATA_URL = SERVER_NAME + "data.php";
	
	//�����ؼ� ������ �޾ƿ��� �Լ�
	public static String getConnectString(String address){
		StringBuilder sb = new StringBuilder(); //���ڿ� ��ũ�� ����Ʈ ����
		try {
					
 			URL url = new URL(address); //�ּ� ����
  			BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
  			//������ BufferedReader�� �޾ƿ���
			
  			//�����͸� string���� ��ȯ
			String str = "";
			while ((str = in.readLine()) != null){
				sb.append(str);	
			}

		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		//����
		return sb.toString();
	}
}
