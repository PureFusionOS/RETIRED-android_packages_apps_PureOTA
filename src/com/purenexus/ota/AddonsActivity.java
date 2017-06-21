/*
 * Copyright (C) 2017 Henrique Silva (jhenrique09)
 *
 * * Licensed under the GNU GPLv2 license
 *
 * The text of the license can be found in the LICENSE file
 * or at https://www.gnu.org/licenses/gpl-2.0.txt
 */
package com.purenexus.ota;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AddonsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    private AppCompatDelegate mDelegate;
    private PreferenceScreen preferenceScreen;
    private List<Map<String, String>> addons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.addons);
        preferenceScreen = getPreferenceScreen();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        try {
            addons = (ArrayList<Map<String, String>>) getIntent().getSerializableExtra("addons");
        } catch (Exception ex) {
        }
        try {
            if (addons.size() > 0) {
                preferenceScreen.removeAll();
                for (Map<String, String> addon : addons) {
                    Preference preference = new Preference(preferenceScreen.getContext());
                    preference.setTitle(addon.get("title"));
                    preference.setSummary(addon.get("summary"));
                    preference.setKey("addon_" + addon.get("url"));
                    preference.setOnPreferenceClickListener(this);
                    preferenceScreen.addPreference(preference);
                }
            } else {
                Toast.makeText(AddonsActivity.this, getString(R.string.addons_error), Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception ex) {
            Toast.makeText(AddonsActivity.this, getString(R.string.addons_error), Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key;
        try {
            key = preference.getKey();
            if (key == null) {
                key = "";
            }
        } catch (Exception ex) {
            key = "";
        }

        if (key.startsWith("addon_")) {
            String url = key.substring(6);
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (Exception ex) {
                Toast.makeText(AddonsActivity.this, getString(R.string.error_open_url), Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        getDelegate().setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().addContentView(view, params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        getDelegate().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }
}