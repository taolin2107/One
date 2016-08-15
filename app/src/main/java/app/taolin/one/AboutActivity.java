package app.taolin.one;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import app.taolin.one.utils.CommonUtil;
import app.taolin.one.utils.Utils;

/**
 * @author taolin
 * @version v1.0
 * @date Jun 19, 2016.
 * @description
 */

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setTheme(this);
        setContentView(R.layout.about);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView content = (TextView) findViewById(R.id.content);
        content.setText(getString(R.string.about_content, CommonUtil.getAppVersionName(this)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
