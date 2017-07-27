package cn.huangjiawen.hotfixproj;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cn.huangjiawen.hotfixlib.HotFix;

/**
 *  1. jar cvf patch.jar cn/huangjiawen/hotfixproj/Bug.class
 *
 *  2. dx --dex --output=patch_dex.jar patch.jar
 *
 *  3.adb push patch_dex.jar /sdcard/
 */
public class MainActivity extends FragmentActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        findViewById(R.id.fix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HotFix.patch(getApplicationContext(), "/sdcard/patch_dex.jar", "patch_dex.jar");
            }
        });
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadedBug bug = new LoadedBug();
                text.setText(bug.loadBug());
            }
        });
    }
}
