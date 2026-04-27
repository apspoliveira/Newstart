package newstart.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import newstart.Activity_Main;
import newstart.R;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "NEWSTART_Notifications";
    public static final String EXTRA_TYPE = "notification_type";

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        if (type == null) return;

        showNotification(context, type);
    }

    private void showNotification(Context context, String type) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NEWSTART Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        String title = "NEWSTART Reminder";
        String text = "Time to check your health goals!";
        int icon = R.drawable.ic_launcher_foreground;
        int fragmentId = 0;

        switch (type) {
            case "air":
                title = "Fresh Air";
                text = "Take a moment to breathe some fresh air today!";
                fragmentId = 4;
                break;
            case "nutrition":
                title = "Healthy Eating";
                text = "Remember to choose whole, plant-based foods for your next meal.";
                fragmentId = 1;
                break;
            case "sun":
                title = "Sunlight";
                text = "It's a great time to get some natural Vitamin D!";
                fragmentId = 6;
                break;
            case "water":
                title = "Hydration";
                text = "Time for another glass of water. Stay hydrated!";
                fragmentId = 5;
                break;
            case "workout":
                title = "Exercise";
                text = "Ready for a quick workout or stretch?";
                fragmentId = 0;
                break;
        }

        Intent intent = new Intent(context, Activity_Main.class);
        intent.putExtra("fragmentID", fragmentId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 
                type.hashCode(), 
                intent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(type.hashCode(), builder.build());
    }
}
