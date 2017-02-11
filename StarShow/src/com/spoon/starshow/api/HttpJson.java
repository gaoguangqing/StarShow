package com.spoon.starshow.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.spoon.starshow.bean.Person;
import com.spoon.starshow.bean.SchoolInfo;
import com.spoon.starshow.dapter.JsonAdapter;

import android.content.Context;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

public class HttpJson extends Thread {
	
	private String url;
		
	private Context context;
	
	private ListView listView;
	
	private JsonAdapter adapter;
	
	private Handler handler;

	public HttpJson(String url,  ListView listView,
			JsonAdapter adapter,Handler handler) {
		this.url = url;
		this.listView = listView;
		this.adapter = adapter;
		this.handler=handler;
	}

	@Override
	public void run() {
		try {
			URL httpUrl=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb=new StringBuffer();
			String str;
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
			final List<Person>data=parseJson(sb.toString());
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("Ö´ÐÐ");
					adapter.setData(data);
					listView.setAdapter(adapter);
				}
			});
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private List<Person> parseJson(String json){
		try {
			JSONObject object=new JSONObject(json);
			//============================================
			List<Person> persons=new ArrayList<Person>();
			int result=object.getInt("result");
			if(result==1){
				JSONArray personDataArray=object.getJSONArray("personData");
				for(int i=0;i<personDataArray.length();i++){
					
					Person personObject=new Person();
					persons.add(personObject);
					//==========================================
					JSONObject person=personDataArray.getJSONObject(i);
					String name=person.getString("name");
					int age=person.getInt("age");
					String url=person.getString("url");
					//=============================================
					personObject.setName(name);
					personObject.setAge(age);
					personObject.setUrl(url);
					
					//=======================================
					List<SchoolInfo> schInfos=new ArrayList<SchoolInfo>();
					
					personObject.setSchoolInfo(schInfos);
					//=========================================
					JSONArray schoolInfo=person.getJSONArray("schoolInfo");
					for(int j=0;j<schoolInfo.length();j++)
					{
						JSONObject school=schoolInfo.getJSONObject(j);
						String schoolName=school.getString("school_name");
						
						
						//============================================
						SchoolInfo info=new SchoolInfo();
						info.setSchool_name(schoolName);
						schInfos.add(info);
					}
					
				}
				return persons;
			}else{
				Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
