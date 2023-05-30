package mg.lwdeveloper.ima;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {
    private final Context context;
    public final String BOLD = "BOLD";
    public final String MEDIUM = "MEDIUM";
    public final String REGULAR = "REGULAR";
    public final String SEMI_BOLD = "SEMI_BOLD";
    private final String SF_PRO = "SF_PRO_";

    public Utils(Context mContext) {
        this.context = mContext;
    }

    public void btnClick(View view){
        final Animation anim = AnimationUtils.loadAnimation(context, R.anim.btn_click);
        view.startAnimation(anim);
    }

    public void setFont(View view,String font) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),SF_PRO+font+".otf");
        if (view instanceof Button) {
            Button btn = (Button) view;
            btn.setTypeface(typeface);
        }
        else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTypeface(typeface);
        }
    }


    public String numberFormat(String number) {
        StringBuilder res = new StringBuilder();
        int len = number.length();
        for (int i = 0; i < len; i++) {
            if(i == len-4 || i == len-7 || i == len-10) {
                res.append(number.charAt(i)).append(" ");
            }
            else { res.append(number.charAt(i)); }
        }
        return res.toString();
    }
}
