package mg.lwdeveloper.ima.dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mg.lwdeveloper.ima.R;
import mg.lwdeveloper.ima.Utils;

public class ConfirmDialog extends SweetDialog {

    Utils utils;
    public Button btnOk;

    public ConfirmDialog(Context context, String title, String message)
    {
        super(context, R.layout.dialog_confirm);
        utils = new Utils(context);
        setView(R.id.title_dialog, title, utils.SEMI_BOLD);
        setView(R.id.description_dialog, message, utils.REGULAR);
        btnOk = (Button) setView(R.id.btn_ok, "Ok", utils.SEMI_BOLD);
        onCancel(R.id.btn_cancel);
    }
    @Override
    public void show() {
        super.show();
    }
}
