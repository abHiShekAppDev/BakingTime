package com.developer.abhishek.bakingtime.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

public class WidgetIntentService extends IntentService {

    public static final String ACTION_CHANGE_INCREMENT = "com.developer.abhishek.bakingtime.widget.action.change_ingredient_plus";
    public static final String ACTION_CHANGE_DECREMENT = "com.developer.abhishek.bakingtime.widget.action.change_ingredient_minus";

    public static final String INTENT_EXTRA_CURRENT_INDEX = "count_value";
    public static final String INTENT_EXTRA_SIZE = "size_of_baking_list";

    public final static String PREF_BAKING_LIST = "BAKING_LIST";
    public static final String PREF_BAKING_CURRENT_INDEX = "baking_list_current_index";
    public final static String PREF_BAKING_LIST_SIZE = "baking_list_size";

    private int currentIndex = -1;
    private int size = 0;

    public WidgetIntentService() {
        super("CHANGE_INGREDIENTS");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            currentIndex = intent.getIntExtra(INTENT_EXTRA_CURRENT_INDEX,-1);
            size = intent.getIntExtra(INTENT_EXTRA_SIZE,0);
            final String action = intent.getAction();
            if (ACTION_CHANGE_INCREMENT.equals(action)) {
                incrementId();
            }else if(ACTION_CHANGE_DECREMENT.equals(action)){
                decrementId();
            }
        }
    }

    private void incrementId(){
        if(currentIndex < size-1){
            currentIndex++;
            SharedPreferences.Editor editor = getSharedPreferences(PREF_BAKING_LIST, MODE_PRIVATE).edit();
            editor.putInt(PREF_BAKING_CURRENT_INDEX, currentIndex);
            editor.apply();
            updateWidget();
        }
    }

    private void decrementId(){
        if(currentIndex > 0){
            currentIndex--;
            SharedPreferences.Editor editor = getSharedPreferences(PREF_BAKING_LIST, MODE_PRIVATE).edit();
            editor.putInt(PREF_BAKING_CURRENT_INDEX, currentIndex);
            editor.apply();
            updateWidget();
        }
    }

    private void updateWidget(){
        Intent intent = new Intent(this, IngredientWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        sendBroadcast(intent);
    }
}
