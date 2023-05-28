package mg.lwdeveloper.ima;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SignupActivity extends AppCompatActivity {

    Toolbar topAppBarSignup;
    Button btnSignup, btnLogin;
    Utils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
        viewListener();
    }

    private void initView() {
        utils = new Utils(this);
        btnSignup = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);
        topAppBarSignup = findViewById(R.id.topAppBarSignup);
    }

    private void viewListener() {
        btnLogin.setOnClickListener(new onClick());
        btnSignup.setOnClickListener(new onClick());
        topAppBarSignup.setNavigationOnClickListener(new onClick());
    }

    private void showLoginActivity() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        showLoginActivity();
    }

    class onClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            utils.btnClick(view);

            if (id == R.id.topAppBarSignup || id == R.id.btn_login) {
                showLoginActivity();
            } else if (id == R.id.btn_signup) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
