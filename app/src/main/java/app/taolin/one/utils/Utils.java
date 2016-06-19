package app.taolin.one.utils;

import android.app.Activity;

import app.taolin.one.R;
import app.taolin.one.common.Constants;

/**
 * @author taolin
 * @version v1.0
 * @date Jun 19, 2016.
 * @description
 */

public class Utils {

    public static void setTheme(Activity activity) {
        if (SharedPreferenceUtil.readBoolean(Constants.KEY_NIGHT_MODE)) {
            switch (SharedPreferenceUtil.readInt(Constants.KEY_FONT_SIZE, 1)) {
                case 0:
                    activity.setTheme(R.style.NightSmallTextTheme);
                    break;
                case 1:
                    activity.setTheme(R.style.NightNormalTextTheme);
                    break;
                case 2:
                    activity.setTheme(R.style.NightLargeTextTheme);
                    break;
                case 3:
                    activity.setTheme(R.style.NightExtraTextTheme);
                    break;
            }
        } else {
            switch (SharedPreferenceUtil.readInt(Constants.KEY_FONT_SIZE, 1)) {
                case 0:
                    activity.setTheme(R.style.LightSmallTextTheme);
                    break;
                case 1:
                    activity.setTheme(R.style.LightNormalTextTheme);
                    break;
                case 2:
                    activity.setTheme(R.style.LightLargeTextTheme);
                    break;
                case 3:
                    activity.setTheme(R.style.LightExtraTextTheme);
                    break;
            }
        }
    }
}
