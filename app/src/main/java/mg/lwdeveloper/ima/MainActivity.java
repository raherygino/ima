package mg.lwdeveloper.ima;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

import mg.lwdeveloper.ima.dialog.AlertDialog;
import mg.lwdeveloper.ima.dialog.ConfirmDialog;
import mg.lwdeveloper.ima.views.BottomNavbar;

public class MainActivity extends AppCompatActivity {

    private TextView titleUsername, titleWelcome, titleActif, actifValue, titleSuspend, suspendValue, updateDataTitle;
    private EditText imaValue, mgValue;
    private Button btnUpdate, btnOffer, btnConfirm;
    private FloatingActionButton fab;
    private ImageView btnMore;
    private Utils utils;
    private BluetoothAdapter bluetoothAdapter;
    private final int REQUEST_ENABLE_BT = 24;
    private static final int READ_REQUEST_CODE = 42;
    private onChangeEditText imaChangeEditText;
    private onChangeEditText mgChangeEditText;

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

    @SuppressLint("MissingPermission")
    private void setBluetooth() {

        // Get the default Bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Check if Bluetooth is supported on the device
        if (bluetoothAdapter == null) {
            // Bluetooth is not supported
            // Handle the case accordingly
            return;
        }

        // Check if Bluetooth is already enabled
        if (!bluetoothAdapter.isEnabled()) {
            // Create an intent to enable Bluetooth
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            // Start the activity with the intent, and a request code
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            // Bluetooth is already enabled
            // Perform any other operations related to Bluetooth
        }
    }


    // Override onActivityResult to handle the request enable Bluetooth result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "Bluetooth is enabled successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "User declined", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(MainActivity.this, data.getDataString(), Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        BottomNavbar navbar = new BottomNavbar(MainActivity.this);
        fab = navbar.fab;
        ArrayList<View> items = navbar.items;
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setOnClickListener(new onItemClick(i));
        }
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
        fab.setOnClickListener(new onClick());

        imaChangeEditText = new onChangeEditText(imaValue);
        mgChangeEditText = new onChangeEditText(mgValue);

        imaValue.setOnFocusChangeListener(new onFocusEditText());
        mgValue.setOnFocusChangeListener(new onFocusEditText());
    }

    @Override
    public void onBackPressed() {
        ConfirmDialog dialog = new ConfirmDialog(this, "Deconnexion", "Voulez-vous se deconnecter ?");
        dialog.btnOk.setOnClickListener(new onClick());
        dialog.show();
    }


    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    class onFocusEditText implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean isFocused) {
            int id = view.getId();
            mgValue.removeTextChangedListener(mgChangeEditText);
            imaValue.removeTextChangedListener(imaChangeEditText);

            if (isFocused) {
                if (id == R.id.ima_value) {
                    imaValue.addTextChangedListener(imaChangeEditText);
                } else if (id == R.id.mg_value) {
                    mgValue.addTextChangedListener(mgChangeEditText);
                }
            }
        }
    }

    class onChangeEditText implements TextWatcher {

        private View view;
        public onChangeEditText(View mView) {
            this.view = mView;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (view.getId() == R.id.ima_value) {
                if (charSequence.length() != 0 ) {
                    double ima = Double.parseDouble(String.valueOf(charSequence));
                    mgValue.setText(String.valueOf(ima * 200));
                } else {
                    mgValue.setText("0");
                }
            } else {
                if (charSequence.length() != 0 ) {
                    double mga = Double.parseDouble(String.valueOf(charSequence));
                    imaValue.setText(String.valueOf(mga / 200));
                } else {
                    imaValue.setText("0");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    class onClick implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            utils.btnClick(view);
            int id = view.getId();


            if (id == R.id.btn_ok) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else if (id == R.id.btn_more ) {
                AlertDialog dialog = new AlertDialog(MainActivity.this, "Question ou Problème", getString(R.string.help_title)+"\n033 91 346 87\n032 51 422 84");
                dialog.onCancel(dialog.btnOk.getId());
                dialog.btnOk.setText("Ok");
                dialog.show();

            } else if (id == R.id.fab_add) {
                AlertDialog dialog = new AlertDialog(MainActivity.this, "IMA Scan", "Pour continuer, installer IMASCan v1.0");
                dialog.onCancel(dialog.btnOk.getId());
                dialog.btnOk.setText("Installer maintenant");
                dialog.show();
            } else if (id == R.id.ima_value) {
                imaValue.addTextChangedListener(imaChangeEditText);
            } else if (id == R.id.mg_value) {
                mgValue.addTextChangedListener(mgChangeEditText);
            }
        }
    }

    class onItemClick implements View.OnClickListener {
        private final int position;
        public  onItemClick(int mPosition) {
            this.position = mPosition;
        }
        @Override
        public void onClick(View view) {
            utils.btnClick(view);
            switch (position) {
                case 1:
                    showFileChooser();
                    break;
                case 2:
                    setBluetooth();
                    break;
            }
        }
    }
}