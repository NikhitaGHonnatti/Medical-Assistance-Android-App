package com.example.testing.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class RunsOutReminder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "rur")
                //.setSmallIcon(//give path here); //HEY VANISHA THIS IS FOR YOU TO ADD AN APP PHOTO SO THAT IT DISPLAYS WITH THE NOTIF
                  .setContentTitle("MedApp Reminder!")
                  .setContentText("Your prescription runs out in 2 days! Make sure to refill or go in for another consultation")
                  .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat nmc =  NotificationManagerCompat.from(context);
        nmc.notify(200,builder.build());
    }
}
