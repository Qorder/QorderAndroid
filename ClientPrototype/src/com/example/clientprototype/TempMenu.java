package com.example.clientprototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TempMenu extends Activity {
	
    Button back;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_menu);
                
        //button listener
        Button startButton = (Button) findViewById(R.id.buttonBack);
        
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //return 0
                Intent i = getIntent();
                i.putExtra("returnInt", 0);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

}