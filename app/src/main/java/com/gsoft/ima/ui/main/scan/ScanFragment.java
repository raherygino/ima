package com.gsoft.ima.ui.main.scan;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.WriterException;
import com.gsoft.ima.R;
import com.gsoft.ima.databinding.FragmentScanBinding;
import com.gsoft.ima.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static com.gsoft.ima.constants.main.MainConstants.FEMALE;
import static com.gsoft.ima.constants.main.MainConstants.MALE;

public class ScanFragment extends Fragment {

    FragmentScanBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScanBinding.inflate(inflater, container, false);
        Utils.setColorBarStatusDefault(getContext());
        QRGEncoder qrgEncoder = new QRGEncoder("sender:0346500700;value:5000000", null, QRGContents.Type.TEXT, 500);
        qrgEncoder.setColorBlack(Color.WHITE);
        qrgEncoder.setColorWhite(Color.DKGRAY);
        try {
            Bitmap bitmap = qrgEncoder.getBitmap();
            binding.qrImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return binding.getRoot();
    }
}
