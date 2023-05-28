package mg.lwdeveloper.ima;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import mg.lwdeveloper.ima.dialog.ConfirmDialog;

public class MainActivity extends AppCompatActivity {

    private TextView titleUsername, titleWelcome, titleActif, actifValue, titleSuspend, suspendValue, updateDataTitle;
    private EditText imaValue, mgValue;
    private Button btnUpdate, btnOffer, btnConfirm;
    private ImageView btnMore;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.grey_200));
        initView();
        setViewStyle();
        setViewClickListener();
    }

    private void initView() {
        utils = new Utils(this);
        titleUsername = findViewById(R.id.title_username);
        titleWelcome = findViewById(R.id.title_welcome);
        titleActif = findViewById(R.id.title_actif);
        actifValue = findViewById(R.id.actif_value);
        titleSuspend = findViewById(R.id.title_suspend);
        suspendValue = findViewById(R.id.suspend_value);
        updateDataTitle = findViewById(R.id.update_data_title);

        imaValue = findViewById(R.id.ima_value);
        mgValue = findViewById(R.id.mg_value);

        btnMore = findViewById(R.id.btn_more);
        btnUpdate = findViewById(R.id.btn_update);
        btnOffer = findViewById(R.id.offer);
        btnConfirm = findViewById(R.id.confirm);
    }

    private void setViewStyle() {

        utils.setFont(titleUsername, utils.SEMI_BOLD);
        utils.setFont(titleWelcome, utils.REGULAR);

        utils.setFont(titleActif, utils.SEMI_BOLD);
        utils.setFont(actifValue, utils.BOLD);

        utils.setFont(titleSuspend, utils.SEMI_BOLD);
        utils.setFont(suspendValue, utils.BOLD);

        utils.setFont(imaValue, utils.REGULAR);
        utils.setFont(mgValue, utils.REGULAR);
        utils.setFont(btnUpdate, utils.REGULAR);
        utils.setFont(updateDataTitle, utils.REGULAR);
        utils.setFont(btnOffer, utils.REGULAR);
        utils.setFont(btnConfirm, utils.REGULAR);
    }

    private void setViewClickListener() {
        btnMore.setOnClickListener(new onClick());
        btnUpdate.setOnClickListener(new onClick());
        btnOffer.setOnClickListener(new onClick());
        btnConfirm.setOnClickListener(new onClick());
    }

    @Override
    public void onBackPressed() {
        ConfirmDialog dialog = new ConfirmDialog(this, "Deconnexion", "Voulez-vous se deconnecter ?");
        dialog.btnOk.setOnClickListener(new onClick());
        dialog.show();
    }

    class onClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            utils.btnClick(view);
            int id = view.getId();

            if (id == R.id.btn_ok) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}