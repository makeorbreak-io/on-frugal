package io.makeorbreak.hackohollics.onfrugal.presentation;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;import android.view.View;
import io.makeorbreak.hackohollics.onfrugal.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String version;
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            version = "???";
        }

        Element versionElement = new Element();
        versionElement.setTitle("Version " + version);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(getString(R.string.about_description))
                .addItem(versionElement)
                .addGroup(getString(R.string.contact_developer))
                .addEmail("duartemr.pinto@gmail.com", getString(R.string.about_email_label))
                .addGitHub("makeorbreak-io/on-frugal", getString(R.string.about_github_label))
                .create();
        setContentView(aboutPage);
    }
}
