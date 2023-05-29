package mg.lwdeveloper.ima;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
                // Bluetooth is enabled successfully
                // Perform any operations related to Bluetooth
            } else {
                // User declined or failed to enable Bluetooth
                // Handle the case accordingly
            }
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
    }

    @Override
    public void onBackPressed() {
        ConfirmDialog dialog = new ConfirmDialog(this, "Deconnexion", "Voulez-vous se deconnecter ?");
        dialog.btnOk.setOnClickListener(new onClick());
        dialog.show();
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
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.options, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return true;
                    }
                });
                popupMenu.show();
            } else if (id == R.id.fab_add) {
                AlertDialog dialog = new AlertDialog(MainActivity.this, "IMA Scan", "Pour continuer, installer IMASCan v1.0");
                dialog.onCancel(dialog.btnOk.getId());
                dialog.btnOk.setText("Installer maintenant");
                dialog.show();
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
                case 2:
                    setBluetooth();
                    break;
            }
        }
    }
}