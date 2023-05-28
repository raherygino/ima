package mg.lwdeveloper.ima;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Utils {
    private final Context context;

    public Utils(Context mContext) {
        this.context = mContext;
    }

    public void btnClick(View view){
        final Animation anim = AnimationUtils.loadAnimation(context, R.anim.btn_click);
        view.startAnimation(anim);
    }
}
