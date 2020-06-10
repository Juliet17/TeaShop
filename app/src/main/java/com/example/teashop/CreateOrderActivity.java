package com.example.teashop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView textViewHello;
    private TextView textViewAdditions;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;
    private Spinner spinnerGreenTea;
    private Spinner spinnerBlackTea;

    private String drink;  // хранит название напитка
    private String login; // логин
    private String password; // psswd
    private StringBuilder builderAdditions;  // формирует список добавок

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        Intent intent = getIntent();
        if (intent.hasExtra("login") && intent.hasExtra("password")) {
            login = intent.getStringExtra("login");
            password = intent.getStringExtra("password");
        } else {
            login = getString(R.string.default_login);
            password = getString(R.string.default_password);
        }
        drink = getString(R.string.green_tea);  // значение по умолчанию
        textViewHello = findViewById(R.id.textViewHello);
        String hello = String.format(getString(R.string.hello), login);
        textViewHello.setText(hello);
        textViewAdditions = findViewById(R.id.textViewAdditions);
        String additions = String.format(getString(R.string.additions), drink);
        textViewAdditions.setText(additions);
        checkBoxMilk = findViewById(R.id.checkBoxMilk);
        checkBoxSugar = findViewById(R.id.checkBoxSugar);
        checkBoxLemon = findViewById(R.id.checkBoxLemon);
        spinnerBlackTea = findViewById(R.id.spinnerBlackTea);
        spinnerGreenTea = findViewById(R.id.spinnerGreenTea);
        builderAdditions = new StringBuilder();

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.types_of_black_tea,
                R.layout.spinner_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerBlackTea.setAdapter(adapter);
        spinnerBlackTea.setOnItemSelectedListener(this);

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,
                R.array.types_of_green_tea,
                R.layout.spinner_dropdown_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerGreenTea.setAdapter(adapter1);
        spinnerGreenTea.setOnItemSelectedListener(this);
    }

    public void onClickChangeDrink(View view) {
        RadioButton button = (RadioButton) view; // getting the button (приведение типа)
        int id = button.getId();
        if (id == R.id.radioButtonGreenTea) {
            drink = getString(R.string.green_tea);
            spinnerGreenTea.setVisibility(View.VISIBLE);
            spinnerBlackTea.setVisibility(View.INVISIBLE);
        } else if (id == R.id.radioButtonBlackTea) {
            drink = getString(R.string.black_tea);
            spinnerGreenTea.setVisibility(View.INVISIBLE);
            spinnerBlackTea.setVisibility(View.VISIBLE);
        }
        String additions = String.format(getString(R.string.additions), drink);
        textViewAdditions.setText(additions);
    }

    public void onClickSendOrder(View view) {

        builderAdditions.setLength(0);  // очищаем stringBuilder на всякий случай
        if (checkBoxMilk.isChecked()) {
            builderAdditions.append(getString(R.string.milk)).append(" ");
        }
        if (checkBoxSugar.isChecked()) {
            builderAdditions.append(getString(R.string.sugar)).append(" ");
        }
        if (checkBoxLemon.isChecked()) {
            builderAdditions.append(getString(R.string.lemon)).append(" ");
        }
        String teaType = "";
        if (drink.equals(getString(R.string.green_tea))) {
            teaType = spinnerGreenTea.getSelectedItem().toString(); // т.к. getSelectedItem возвращает объект
        } else {
            teaType = spinnerBlackTea.getSelectedItem().toString();
        }
        String order = String.format(getString(R.string.order), login, password, drink, teaType);
        String additions;
        if (builderAdditions.length() > 0) {
            additions = getString(R.string.needAdditions) + " " + builderAdditions.toString();
        } else {
            additions = "";
        }
        String fullOrder = order + additions;

        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("order", fullOrder);  // передаём заказ в OrderActivity
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}