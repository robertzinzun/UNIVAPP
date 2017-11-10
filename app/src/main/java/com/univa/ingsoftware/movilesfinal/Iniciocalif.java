package com.univa.ingsoftware.movilesfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
public class Iniciocalif extends AppCompatActivity {

    String name, Err;
    TextView nameTV, err;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciocalif);

        nameTV = (TextView) findViewById(R.id.home_name);
        err = (TextView) findViewById(R.id.err);

        name = getIntent().getStringExtra("name");
        Err = getIntent().getStringExtra("err");

        nameTV.setText(name);
        err.setText(Err);
    }

}
