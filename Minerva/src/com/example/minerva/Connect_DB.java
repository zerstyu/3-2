package com.example.minerva;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Connect_DB {
	final static String SERVER_NAME = "http://192.168.0.16/";
	public static final String GET_DATA_URL = SERVER_NAME + "data.php";
	
	//연결해서 데이터 받아오는 함수
	public static String getConnectString(String address){
		StringBuilder sb = new StringBuilder(); //문자열 링크드 리스트 생성
		try {
					
 			URL url = new URL(address); //주소 설정
  			BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
  			//데이터 BufferedReader로 받아오기
			
  			//데이터를 string으로 변환
			String str = "";
			while ((str = in.readLine()) != null){
				sb.append(str);	
			}

		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		//리턴
		return sb.toString();
	}
}
