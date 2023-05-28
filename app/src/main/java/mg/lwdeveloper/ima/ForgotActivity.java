package mg.lwdeveloper.ima;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotActivity extends AppCompatActivity {

    private Button ButtonSend, ButtonCancel;
    private Utils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        initView();
        viewListener();
    }

    private void initView() {
        utils = new Utils(this);
        ButtonSend = findViewById(R.id.btn_send);
        ButtonCancel = findViewById(R.id.btn_cancel);
    }

    private void viewListener() {
        ButtonSend.setOnClickListener(new onClick());
        ButtonCancel.setOnClickListener(new onClick());
    }

    private void showLoginActivity() {
        Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
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

            if (id == R.id.btn_cancel) {
                showLoginActivity();
            }
        }
    }
}
