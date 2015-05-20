package com.example.minerva;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

class InventoryIfo {
	public String id, name, cnt, mkdate;
	public int img;

	public InventoryIfo(String id, String name, String cnt, String mkdate, int img) {
		this.id = id;
		this.name = name;
		this.cnt = cnt;
		this.mkdate = mkdate;
		this.img = img;
	}
}

public class ListAdapter extends BaseAdapter {
	public ArrayList<InventoryIfo> invifo;
	LayoutInflater inflater;
	Context con;

	public ListAdapter(Context con) {
		this.con = con;
		
		invifo = new ArrayList<InventoryIfo>();
		loadDB();
		inflater = (LayoutInflater) con
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	private void loadDB(){
		String res = Connect_DB.getConnectString(Connect_DB.GET_DATA_URL);
		
		if (res.equals("")) return;
		
		String [] ressp = res.split("\\|");
		for (int c=0; c<ressp.length; c++){
			String [] sp = ressp[c].split("\\+");
			
			int drawid = 0;
			if (sp[1].equals("snack")){
				drawid = R.drawable.snack;
			}
			else if (sp[1].equals("pencil_case")){
				drawid = R.drawable.pencase;
			}
			else if (sp[1].equals("pencil")){
				drawid = R.drawable.pen;
			}
			else if (sp[1].equals("chocolate")){
				drawid = R.drawable.choco;
			}
			else if (sp[1].equals("eraser")){
				drawid = R.drawable.eraser;
			}
			else if (sp[1].equals("juice")){
				drawid = R.drawable.juice;
			}
			else if (sp[1].contains("book")){
				drawid = R.drawable.book;
			}
			
			invifo.add(new InventoryIfo(sp[0], sp[1], sp[2], sp[3], drawid));
		}
	}
	
	public void addItem(InventoryIfo ifo){
		invifo.add(ifo);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return invifo.size();
	}

	@Override
	public Object getItem(int position) {
		return invifo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.lvitem, parent, false);
		}
		
		InventoryIfo ifo = invifo.get(position);
		
		ImageView img;
		TextView name, id, out, num;
		
		id = (TextView)convertView.findViewById(R.id.id);
		name = (TextView)convertView.findViewById(R.id.name);
		out = (TextView)convertView.findViewById(R.id.out);
		num = (TextView)convertView.findViewById(R.id.num);
		img = (ImageView)convertView.findViewById(R.id.img);
		
		name.setText(ifo.name);
		id.setText(ifo.id);
		out.setText(ifo.mkdate);
		num.setText(ifo.cnt);
		
		img.setImageDrawable(convertView.getResources().getDrawable(ifo.img));
		
		return convertView;
	}
}
