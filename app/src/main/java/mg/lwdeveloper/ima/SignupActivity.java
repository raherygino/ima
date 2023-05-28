package mg.lwdeveloper.ima;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SignupActivity extends AppCompatActivity {

    Toolbar topAppBarSignup;
    Button btnSignup, btnLogin;
    EditText genre;
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
        genre = findViewById(R.id.genre);
    }

    private void viewListener() {
        btnLogin.setOnClickListener(new onClick());
        btnSignup.setOnClickListener(new onClick());
        topAppBarSignup.setNavigationOnClickListener(new onClick());
        genre.setOnClickListener(new onClick());
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
            } else if (id == R.id.genre) {

                PopupMenu popupMenu = new PopupMenu(SignupActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.gender, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        EditText genderEditText = (EditText) view;
                        genderEditText.setText(menuItem.getTitle());
                        return true;
                    }
                });
                popupMenu.show();
            }
        }
    }
}
