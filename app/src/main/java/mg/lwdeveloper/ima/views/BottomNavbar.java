package mg.lwdeveloper.ima.views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import mg.lwdeveloper.ima.R;

public class BottomNavbar {
    Context context;
    Activity activity;
    public FloatingActionButton fab;
    int id = R.id.nav_table_layout;
    int idFab = R.id.fab_add;

    public ArrayList<View> items = new ArrayList<View>();
    public BottomNavbar(Context mContext) {
        this.context = mContext;
        this.activity = (Activity) mContext;
        TableLayout tableLayout = activity.findViewById(id);
        fab = activity.findViewById(idFab);
        TableRow row = (TableRow) tableLayout.getChildAt(0);
        for (int i = 0; i < row.getChildCount(); i++) {
            if (i != 2) {
                items.add(row.getChildAt(i));
            }
        }
    }
}
