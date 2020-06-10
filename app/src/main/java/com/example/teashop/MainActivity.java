package com.example.teashop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLogin;  // переменные, кот. хранят данные пользователя
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void onClickCreateOrder(View view) {

        // получаем данные
        String login = editTextLogin.getText().toString().trim();  // метод trim - чтобы избавиться от возможных пробелов
        String password = editTextPassword.getText().toString().trim();
        if (!login.isEmpty() && !password.isEmpty()) {
            Intent intent = new Intent(this, CreateOrderActivity.class);
            intent.putExtra("login", login);  // ключ - значение
            intent.putExtra("password", password);
            startActivity(intent);  //передаём значения, которые ввёл пользователь
        } else {
            Toast.makeText(this, R.string.warning_toast, Toast.LENGTH_SHORT).show();
        }
    }
}