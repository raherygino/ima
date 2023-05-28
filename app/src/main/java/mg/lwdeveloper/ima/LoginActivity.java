package mg.lwdeveloper.ima;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Utils utils;
    private Button buttonLogin, buttonRegister;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        viewListener();
    }

    private void initView () {
        utils = new Utils(this);
        buttonLogin = findViewById(R.id.btn_login);
        buttonRegister = findViewById(R.id.btn_register);
    }

    private void viewListener() {
        buttonLogin.setOnClickListener(new onClick());
        buttonRegister.setOnClickListener(new onClick());
    }

    class onClick implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int id = view.getId();
            utils.btnClick(view);

            if (id == R.id.btn_login) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
