package com.spoon.starshow.dapter;

import java.util.List;

import com.spoon.starshow.R;
import com.spoon.starshow.api.HttpImage;
import com.spoon.starshow.bean.Person;
import com.spoon.starshow.bean.SchoolInfo;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class JsonAdapter extends BaseAdapter{

	private List<Person> list;
	
	private Context context;
	
	private LayoutInflater inflater;
	
	private Handler handler=new Handler();
	
	public JsonAdapter(Context context){
		this.context=context;
		this.inflater = LayoutInflater.from(context);
	}
	public void setData(List<Person> data){
		this.list=data;
	}
	public JsonAdapter(List<Person> list, Context context,
			LayoutInflater inflater) {
		this.list = list;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder=null;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.item, null);
			holder=new Holder(convertView);
			convertView.setTag(holder);
			
			}else{
				holder=(Holder)convertView.getTag();
			}
			Person person=list.get(position);
			holder.name.setText(person.getName());
			holder.age.setText(""+person.getAge());
			
			
			
			
			List<SchoolInfo> schools=person.getSchoolInfo();
			SchoolInfo schoolInfo1=schools.get(0);
			SchoolInfo schoolInfo2=schools.get(1);
			
			
			holder.school1.setText(schoolInfo1.getSchool_name());
			holder.school2.setText(schoolInfo2.getSchool_name());
			
			new HttpImage( person.getUrl(), handler,holder.imageView).start();
		
//			System.out.println(person.getUrl());
			holder.imageView.setImageResource(R.drawable.ic_launcher);
		return convertView;
	}
	class Holder{
		private TextView name;
		private TextView age;
		
		private TextView school1;
		private TextView school2;
		
		private ImageView imageView;
		
		public Holder(View view){
			name=(TextView)view.findViewById(R.id.name);
			age=(TextView)view.findViewById(R.id.age);
			school1=(TextView)view.findViewById(R.id.school1);
			school2=(TextView)view.findViewById(R.id.school2);
			imageView=(ImageView)view.findViewById(R.id.imageView);
		}
	}

}
