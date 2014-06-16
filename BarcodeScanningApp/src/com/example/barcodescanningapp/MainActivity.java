package com.example.barcodescanningapp;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener{
	
	private Button scanBtn, saveBtn;
	private TextView formatTxt, contentTxt;
	ListView lv;
	String scanContent;
	String scanFormat;
	private EditText text;
	CardDatabase card_db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		scanBtn = (Button)findViewById(R.id.scan_button);
		saveBtn = (Button)findViewById(R.id.save_button);
		formatTxt = (TextView)findViewById(R.id.scan_format);
		contentTxt = (TextView)findViewById(R.id.scan_content);
		lv = (ListView)findViewById(R.id.listView1);
		
		text = (EditText)findViewById(R.id.editText);
		
		scanBtn.setOnClickListener(this);
		saveBtn.setOnClickListener(this);
		
		card_db = new CardDatabase(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			scanContent = scanningResult.getContents();
			scanFormat = scanningResult.getFormatName();
			formatTxt.setText("FORMAT: " + scanFormat);
			contentTxt.setText("CONTENT: " + scanContent);
		}
		else {
		    Toast toast = Toast.makeText(getApplicationContext(), 
		        "No scan data received!", Toast.LENGTH_SHORT);
		    toast.show();
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.scan_button){
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
		}
		if(v.getId()==R.id.save_button){
			card_db.addCard(scanContent, text.getText().toString());
			card_db = getCardDatabase();
			List<String> list= card_db.readDataFromCardTable(); 
			System.out.println("lll" +list);
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list); 
		    lv.setAdapter(adapter);
		}
	}
	
	
	 public CardDatabase getCardDatabase() {
	        return new CardDatabase(this);
	    }

}
