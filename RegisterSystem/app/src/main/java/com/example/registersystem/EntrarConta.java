package com.example.registersystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class EntrarConta extends AppCompatActivity {
    private static final int RADIO_CNPJ = R.id.radio_cnpj;
    private static final int RADIO_CPF = R.id.radio_cpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_conta);
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (view.getId()==RADIO_CNPJ && checked) {
            changeLabelToCNPJ();
        } else if (view.getId()==RADIO_CPF && checked) {
            changeLabelToCPF();
        }
    }

    private void changeLabelToCNPJ() {
        TextView labelCpfCnpj = findViewById(R.id.labelCpfCnpj);
        labelCpfCnpj.setText("CNPJ:");
    }

    // Método para alterar o texto para "CPF"
    private void changeLabelToCPF() {
        TextView labelCpfCnpj = findViewById(R.id.labelCpfCnpj);
        labelCpfCnpj.setText("CPF:");
    }

}