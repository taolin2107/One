package app.taolin.one;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import app.taolin.one.utils.Utils;

/**
 * @author taolin
 * @version v1.0
 * @date Jun 19, 2016.
 * @description
 */

public class CopyrightActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setTheme(this);
        setContentView(R.layout.coryright);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
