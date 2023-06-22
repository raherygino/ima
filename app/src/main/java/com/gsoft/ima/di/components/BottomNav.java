package com.gsoft.ima.di.components;


import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gsoft.ima.R;
import com.gsoft.ima.model.models.NavItem;
import com.gsoft.ima.ui.main.MainActivity;
import com.gsoft.ima.ui.main.home.HomeFragment;
import com.gsoft.ima.ui.main.profile.ProfileFragment;
import com.gsoft.ima.ui.main.scan.ScanFragment;
import com.gsoft.ima.ui.main.SettingFragment.FragmentSetting;
import com.gsoft.ima.utils.NavIcon;
import com.gsoft.ima.utils.Utils;

import java.util.ArrayList;

public class BottomNav
{
    public MainActivity mActivity;
    private final TableLayout tableLayout;
    private final ArrayList<NavItem> listItems;
    private String[] listIconItemsActive;
    private String[] listIconItems;

    public BottomNav(Context context) {
        this.initIcon();
        this.mActivity = (MainActivity) context;
        this.tableLayout = mActivity.findViewById(R.id.nav_table_layout);
        this.listItems = getAllItems();
    }

    private void initIcon() {
        listIconItemsActive = new String[NavIcon.values().length];
        listIconItems = new String[NavIcon.values().length];
        for (int i = 0 ; i < NavIcon.values().length; i++) {
            listIconItemsActive[i] = NavIcon.values()[i].active;
            listIconItems[i] = NavIcon.values()[i].inactive;
        }
    }

    public ArrayList<NavItem> getAllItems() {

        ArrayList<NavItem> allItems = new ArrayList<>();
        TableRow tableRow = (TableRow) tableLayout.getChildAt(0);
        for (int i = 0; i < tableRow.getChildCount(); i++) {
            int index = i;
            if (i != 2 ) {
                if (i > 2) {
                    index = i-1;
                }
                LinearLayout linearLayout = (LinearLayout) tableRow.getChildAt(i);
                NavItem item = new NavItem(linearLayout, listIconItems[index]);
                allItems.add(item);
            }
        }
        return allItems;
    }

    public void setItemActivate(int position) {
        for (NavItem navItem : listItems) {
            setActiveColor(navItem, R.color.grey_600, navItem.getIcon());
        }
        setActiveColor(listItems.get(position), R.color.blue_600, listIconItemsActive[position]);
    }

    private void setActiveColor(NavItem navItem, int color, String icon) {
        ImageView itemIcon = navItem.getImageView();
        TextView item = navItem.getTitleTextView();
        itemIcon.setImageDrawable(Utils.getDrawable(mActivity, icon));
        Utils.setColor(mActivity, item, color);
        Utils.setColor(mActivity, itemIcon, color);
    }

    private void effectClick(View view) {
        final Animation anim = AnimationUtils.loadAnimation(mActivity, R.anim.btn_click);
        view.startAnimation(anim);
    }

    public void setConfig() {
        for (int i = 0 ; i < getAllItems().size(); i++) {
            final int index = i;
            NavItem item = getAllItems().get(i);
            item.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    effectClick(view);
                    setItemActivate(index);
                    if (index == 0) {
                        mActivity.setFragment(new HomeFragment());
                    } else if (index == 1) {
                        mActivity.setFragment(new ScanFragment());
                    } else if (index == 2) {
                        mActivity.setFragment(new FragmentSetting());
                    } else {
                        mActivity.setFragment(new ProfileFragment());
                    }
                }
            });
        }
    }
}

