package com.sriyanksiddhartha.speechtotext;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.*;


public class MainActivity extends AppCompatActivity {


	EditText sres,patient_details,symptoms,diagnosis,prescription,advice;
	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sres   = (EditText)findViewById(R.id.sres);
		btn   = (Button) findViewById(R.id.button);
		patient_details = (EditText)findViewById(R.id.patient_details);
		symptoms = (EditText)findViewById(R.id.editText9);
		diagnosis = (EditText)findViewById(R.id.editText10);
		prescription =(EditText)findViewById(R.id.editText8);





		}



	public void getSpeechInput(View view) {

		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(intent, 10);
		} else {
			Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
		}
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case 10:
				if (resultCode == RESULT_OK && data != null) {
					ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
					sres.append(" "+result.get(0));

				}
				break;
		}

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String text = String.valueOf(sres.getText());

				Pattern pattern = Pattern.compile("(?<=patient details).*.(?=symptoms)");

				Matcher matcher = pattern.matcher(text);

				boolean found = false;
				while (matcher.find())
				{
					String result = matcher.group().toString();
					result.replace("symptoms",".");
					patient_details.setText(result);
					sres.append(result);
					found = true;
				}
				if (!found)
				{
					sres.append("\nI didn't find the text");
				}



				pattern = Pattern.compile("(?<=symptoms).*.(?=diagnosis)");

				matcher = pattern.matcher(text);

				found = false;
				while (matcher.find())
				{
					String result = matcher.group().toString();
					result.replace("diagnosis",".");
					symptoms.setText(result);
					sres.append(result);
					found = true;
				}
				if (!found)
				{
					sres.append("\nI didn't find the text");
				}



				pattern = Pattern.compile("(?<=diagnosis).*.(?=prescription)");

				matcher = pattern.matcher(text);

				found = false;
				while (matcher.find())
				{
					String result = matcher.group().toString();
					result.replace("prescription",".");
					diagnosis.setText(result);
					sres.append(result);
					found = true;
				}
				if (!found)
				{
					sres.append("\nI didn't find the text");
				}


				pattern = Pattern.compile("(?<=prescription).*.(?=advice|advise)");

				matcher = pattern.matcher(text);

				found = false;
				while (matcher.find())
				{
					String result = matcher.group().toString();
					result.replace("advice",".");
					prescription.setText(result);
					sres.append(result);
					found = true;
				}
				if (!found)
				{
					sres.append("\nI didn't find the text");
				}


				pattern=Pattern.compile("(?<=advice|advise).*");

				matcher=pattern.matcher(text);

				found = false;
				while (matcher.find())
				{
					String result = "Advice : "+matcher.group().toString();
					prescription.append("\n\n"+result);
					sres.append(result);
					found = true;
				}
				if (!found)
				{
					sres.append("\nI didn't find the text");
				}



			}
		});
	}
}
