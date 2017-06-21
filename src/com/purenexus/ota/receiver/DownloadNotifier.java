/*
 * Copyright (C) 2014 The CyanogenMod Project
 * Copyright (C) 2017 Henrique Silva (jhenrique09)
 *
 * * Licensed under the GNU GPLv2 license
 *
 * The text of the license can be found in the LICENSE file
 * or at https://www.gnu.org/licenses/gpl-2.0.txt
 */

package com.purenexus.ota.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.purenexus.ota.R;

import java.io.File;

public class DownloadNotifier {

    private DownloadNotifier() {
        // Don't instantiate me bro
    }

    public static void notifyDownloadComplete(Context context,
                                              Intent updateIntent, File updateFile) {

        NotificationCompat.Builder builder = createBaseContentBuilder(context, updateIntent)
                .setSmallIcon(R.drawable.ic_system_update)
                .setContentTitle(context.getString(R.string.not_download_success))
                .setContentText(updateFile.getName())
                .setTicker(context.getString(R.string.not_download_success));

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(R.string.not_download_success, builder.build());
    }

    public static void notifyDownloadError(Context context,
                                           Intent updateIntent, int failureMessageResId) {
        NotificationCompat.Builder builder = createBaseContentBuilder(context, updateIntent)
                .setSmallIcon(android.R.drawable.stat_notify_error)
                .setContentTitle(context.getString(R.string.not_download_failure))
                .setContentText(context.getString(failureMessageResId))
                .setTicker(context.getString(R.string.not_download_failure));

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(R.string.not_download_success, builder.build());
    }

    private static NotificationCompat.Builder createBaseContentBuilder(Context context,
                                                                       Intent updateIntent) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 1,
                updateIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(context)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(contentIntent)
                .setLocalOnly(true)
                .setAutoCancel(true);
    }
}
