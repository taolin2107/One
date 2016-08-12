package app.taolin.one;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import app.taolin.one.utils.Constants;
import app.taolin.one.utils.SharedPreferenceUtil;

/**
 * @author taolin
 * @version v1.0
 * @date Jun 28, 2016.
 * @description
 */

public class FontSettingsDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.font_size_settings)
                .setCancelable(true)
                .setSingleChoiceItems(R.array.text_size_items, SharedPreferenceUtil.readInt(Constants.KEY_FONT_SIZE, 1),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String, String> map = new HashMap<>(1);
                        map.put("font_size_index", "" + which);
                        MobclickAgent.onEvent(App.getInstance(), "switch_text_size", map);
                        SharedPreferenceUtil.writeInt(Constants.KEY_FONT_SIZE, which);
                        dismiss();
                        getActivity().recreate();
                    }
                })
                .create();
    }
}
