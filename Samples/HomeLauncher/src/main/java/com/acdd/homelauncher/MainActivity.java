package com.acdd.homelauncher;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.acdd.homelauncher.fragment.ScreenSlidePagerAdapter;

import org.acdd.framework.ACDD;
import org.osgi.framework.BundleException;

import java.io.File;


public class MainActivity extends FragmentActivity {
    /* The number of pages (wizard steps) to show in this demo.
    */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent mDelye = new Intent();
            mDelye.setClassName(this, "com.acdd.android.appcenter.main.GcContainerActivity");
            startActivity(mDelye);

            return true;
        }else if (id==R.id.action_updateQR){
            File file=new File("/sdcard/app-debug.apk");
            if (!file.exists()){

                Toast.makeText(MainActivity.this, "QRCode Update  pkg not exist", Toast.LENGTH_SHORT).show();
            }
            try {
                ACDD.getInstance().updateBundle("com.acdd.qrcode",file);
            } catch (BundleException e) {
                e.printStackTrace();
            }
        }else  if (id==R.id.action_about){

            AlertDialog.Builder mBuild=new AlertDialog.Builder(this);
            mBuild.setTitle("About");
            mBuild.setMessage("Home Version:1.0\n ACDD Version:1.0.0-dev");
            mBuild.create().show();
        }else  if (id==R.id.action_nativeFragment){
            startActivity(new Intent(this,NativeFragmentActivity.class));
        }else if (id==R.id.action_daemon){
            startService(new Intent(this,DaemonService.class));
            Toast.makeText(MainActivity.this, "DaemonService started", Toast.LENGTH_SHORT).show();
            return  true;
        }


        return super.onOptionsItemSelected(item);
    }
}
