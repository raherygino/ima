package mg.lwdeveloper.ima;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mg.lwdeveloper.ima.dialog.ConfirmDialog;

public class LoginActivity extends AppCompatActivity {

    private Utils utils;
    private Button buttonLogin, buttonSignup;
    private TextView forgot;
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
        buttonSignup = findViewById(R.id.btn_signup);
        forgot = findViewById(R.id.forgot);
    }

    private void viewListener() {
        buttonLogin.setOnClickListener(new onClick());
        buttonSignup.setOnClickListener(new onClick());
        forgot.setOnClickListener(new onClick());
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
            } else if (id == R.id.btn_signup) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.forgot) {
                Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.btn_ok) {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        ConfirmDialog confirmDialog = new ConfirmDialog(this, "Quitter", "Voulez-vous quitter vraiment ?");
        confirmDialog.btnOk.setOnClickListener(new onClick());
        confirmDialog.show();
    }
}
