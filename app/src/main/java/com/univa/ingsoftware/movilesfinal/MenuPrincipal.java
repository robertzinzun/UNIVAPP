package com.univa.ingsoftware.movilesfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity {

    private int IDUsuario;

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
        Intent i=new Intent(this,CalifActivity.class);
        i.putExtra("IDUsuario", this.IDUsuario);
        startActivity(i);

    }

    public void buttonBiblioteca_onClick(View v)
    {

    }

    public void buttonBuzon_onClick(View v)
    {

    }

    public void buttonGalerias_onClick(View v)
    {

    }

    public void buttonUbicaciones_onClick(View v)
    {

    }
}
