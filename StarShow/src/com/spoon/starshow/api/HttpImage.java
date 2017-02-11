package com.spoon.starshow.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

public class HttpImage extends Thread{
	
	private String url;
	
	private Handler handler;

	private ImageView imageView;
	
	
	
	public HttpImage(String url, Handler handler, ImageView imageView) {
		this.url = url;
		this.handler = handler;
		this.imageView = imageView;
	}



	@Override
	public void run() {
		try {
			URL httpUrl=new URL(url);
			System.out.println(url);
			HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			InputStream in=conn.getInputStream();
			final Bitmap bitmap=BitmapFactory.decodeStream(in);
			
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					imageView.setImageBitmap(bitmap);
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
