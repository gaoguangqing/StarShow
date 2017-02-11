package com.spoon.starshow;

import com.spoon.starshow.api.HttpJson;
import com.spoon.starshow.dapter.JsonAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.ListView;
import android.app.Activity;

public class MainActivity extends Activity {

	
	private ListView listView;
	private JsonAdapter adapter;
	private Handler handler=new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView=(ListView) findViewById(R.id.listView);
		adapter=new JsonAdapter(this);
		//http://192.168.0.104:8080/Person/servlet/JsonSeverlet
		//https://gaoguangqing.github.io/StarShow/star.json
		String url="https://gaoguangqing.github.io/StarShow/star.json";
		new  HttpJson(url,listView,adapter,handler).start();
		
	}
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            finish();

        }
        return super.onKeyDown(keyCode, event);

    }

}
