package com.example.trabajoprogrmoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rgBotones;
    private RadioButton rbSumar, rbRestar, rbMultiplicar, rbDividir;
    private Button bMostrar, bCalcular, bGuardar, bBorrar;
    private EditText tNumero1, tNumero2;
    private TextView tResultado;
    private SharedPreferences prefer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String appTitle = getResources().getString(R.string.app_title);
        setTitle(appTitle);

        rgBotones = findViewById(R.id.rgBotones);
        rbSumar = findViewById(R.id.rbSumar);
        rbRestar = findViewById(R.id.rbRestar);
        rbMultiplicar = findViewById(R.id.rbMultiplicar);
        rbDividir = findViewById(R.id.rbDividir);
        bMostrar = findViewById(R.id.bMostrar);
        bCalcular = findViewById(R.id.bCalcular);
        bGuardar = findViewById(R.id.bGuardar);
        bBorrar = findViewById(R.id.bBorrar);
        tNumero1 = findViewById(R.id.tNumero1);
        tNumero2 = findViewById(R.id.tNumero2);
        tResultado = findViewById(R.id.tResultado);
        prefer = getSharedPreferences(Preferencias.datos, Context.MODE_PRIVATE);
    }

    public void guardarDatos(View v) {
        String numero1 = tNumero1.getText().toString();
        String numero2 = tNumero2.getText().toString();

        if (numero1.isEmpty() || numero2.isEmpty()) {
            Toast.makeText(this, R.string.toast_enter_valid_values_saved, Toast.LENGTH_SHORT).show();
        } else {
            float n1 = Float.parseFloat(numero1);
            float n2 = Float.parseFloat(numero2);
            prefer.edit().putFloat(Preferencias.numero1, n1).apply();
            prefer.edit().putFloat(Preferencias.numero2, n2).apply();
            Toast.makeText(this, R.string.toast_data_saved, Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrarDatos(View v) {
        tNumero1.setText(String.valueOf(prefer.getFloat(Preferencias.numero1, 0)));
        tNumero2.setText(String.valueOf(prefer.getFloat(Preferencias.numero2, 0)));
    }

    public void borrarDatos(View v) {
        tNumero1.setText("");
        tNumero2.setText("");
        tResultado.setText("");

    }

    public void calcular(View v) {
        int selectedId = rgBotones.getCheckedRadioButtonId();

        if (selectedId == -1) {
            Toast.makeText(this, R.string.toast_enter_valid_operation, Toast.LENGTH_SHORT).show();
        } else {
            String numero1 = tNumero1.getText().toString();
            String numero2 = tNumero2.getText().toString();

            if (numero1.isEmpty() || numero2.isEmpty()) {
                Toast.makeText(this, R.string.toast_enter_valid_values_calculated, Toast.LENGTH_SHORT).show();
            } else {
                double n1 = Double.parseDouble(numero1);
                double n2 = Double.parseDouble(numero2);
                double resultado = 0.0;

                if (rbSumar.isChecked()) {
                    resultado = n1 + n2;
                } else if (rbRestar.isChecked()) {
                 //   tResultado.setText(String.valueOf(n1 - n2));
                    resultado = n1 - n2;
                } else if (rbMultiplicar.isChecked()) {
                    resultado = n1 * n2;
                } else if (rbDividir.isChecked()) {
                    if (n2 != 0) {
                        resultado = n1 / n2;
                    } else {
                        tResultado.setText(getString(R.string.cannot_divide_by_zero));
                        return;
                    }
                }
                String resultadoFormateado = resultado % 1 == 0 ? String.format("%.0f", resultado) : String.format("%.3f", resultado);
                tResultado.setText(resultadoFormateado);
            }
        }
    }
}