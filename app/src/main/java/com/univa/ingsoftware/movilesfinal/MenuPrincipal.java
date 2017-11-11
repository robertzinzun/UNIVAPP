package com.univa.ingsoftware.movilesfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }

    public void buttonPerfil_onClick(View v)
    {

    }

    public void buttonCalificaciones_onClick(View v)
    {
        String id = getIntent().getStringExtra("id");

        Intent i=new Intent(this,CalifActivity.class);
        i.putExtra("id", id);
        startActivity(i);

    }

    public void buttonBiblioteca_onClick(View v)
    {
        Intent intent = new Intent(this, biblioteca.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));

        startActivity(intent);
    }

    public void buttonBuzon_onClick(View v)
    {
        startActivity(new Intent(this, BuzonActivity.class));
    }

    public void buttonGalerias_onClick(View v)
    {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void buttonUbicaciones_onClick(View v)
    {
        startActivity(new Intent(this, SeleccionarMarca.class));
    }
}
