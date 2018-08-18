package com.developer.abhishek.bakingtime.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.developer.abhishek.bakingtime.DetailActivity;
import com.developer.abhishek.bakingtime.R;
import com.developer.abhishek.bakingtime.model.BakingListModel;
import com.google.gson.Gson;

public class IngredientWidget extends AppWidgetProvider {

    private static BakingListModel bakingListModels;
    private static int sizeOfBakingList = 0;
    private static int currentIndexOfIngredient = 0;
    private static RemoteViews views;
    private static int height;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Bundle bundle = appWidgetManager.getAppWidgetOptions(appWidgetId);
        height = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        views = new RemoteViews(context.getPackageName(), R.layout.ingedient_widget);

        loadWidget(context);

        //  Clicking start cook will open app
        Intent startIntent = new Intent(context, DetailActivity.class);
        startIntent.putExtra(DetailActivity.INTENT_KEY_FROM_HOME_PAGE,bakingListModels);
        PendingIntent startPendingIntent = PendingIntent.getActivity(context, 0, startIntent, 0);
        views.setOnClickPendingIntent(R.id.startCookBtnAtWid, startPendingIntent);

        //  Clicking next button will load next ingredient
        Intent nextIntent = new Intent(context,WidgetIntentService.class);
        nextIntent.setAction(WidgetIntentService.ACTION_CHANGE_INCREMENT);
        nextIntent.putExtra(WidgetIntentService.INTENT_EXTRA_SIZE,sizeOfBakingList);
        nextIntent.putExtra(WidgetIntentService.INTENT_EXTRA_CURRENT_INDEX, currentIndexOfIngredient);
        PendingIntent nextPendingIntent = PendingIntent.getService(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.nextBtnAtWid, nextPendingIntent);

        //  Clicking previous button will load previous ingredient
        Intent prevIntent = new Intent(context,WidgetIntentService.class);
        prevIntent.setAction(WidgetIntentService.ACTION_CHANGE_DECREMENT);
        prevIntent.putExtra(WidgetIntentService.INTENT_EXTRA_SIZE,sizeOfBakingList);
        prevIntent.putExtra(WidgetIntentService.INTENT_EXTRA_CURRENT_INDEX, currentIndexOfIngredient);
        PendingIntent prevPendingIntent = PendingIntent.getService(context,0,prevIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.prevBtnAtWid,prevPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        updateAppWidget(context,appWidgetManager,appWidgetId);
    }

    public static void loadWidget(Context context){
        try{
            SharedPreferences prefs = context.getSharedPreferences(WidgetIntentService.PREF_BAKING_LIST, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            sizeOfBakingList = prefs.getInt(WidgetIntentService.PREF_BAKING_LIST_SIZE,0);
            currentIndexOfIngredient = prefs.getInt(WidgetIntentService.PREF_BAKING_CURRENT_INDEX,0);

            if(sizeOfBakingList > 0 && currentIndexOfIngredient < sizeOfBakingList && currentIndexOfIngredient > -1){
                String jsonBakingList = prefs.getString(String.valueOf(currentIndexOfIngredient), "");
                bakingListModels = gson.fromJson(jsonBakingList,BakingListModel.class);

                if(bakingListModels != null){
                    views = new RemoteViews(context.getPackageName(), R.layout.ingedient_widget);
                    views.setTextViewText(R.id.ingredientNameATWID,bakingListModels.getName());
                }
            }

            String ingredientList = "";
            if(height <= 200){
                ingredientList = "";
            }else{
                for(int i=0;i<bakingListModels.getIngredients().size();i++){
                    if(height <= 450){
                        if(i != 0){
                            ingredientList += ", ";
                        }
                        ingredientList += bakingListModels.getIngredients().get(i).getIngredient();
                    }else{
                        ingredientList += bakingListModels.getIngredients().get(i).getIngredient()+"\n";
                    }
                }
            }
            views.setTextViewText(R.id.ingredientListATWID,ingredientList);

            if(currentIndexOfIngredient == 0){
                views.setViewVisibility(R.id.prevBtnAtWid, View.INVISIBLE);
            }else{
                views.setViewVisibility(R.id.prevBtnAtWid, View.VISIBLE);
            }

            if(currentIndexOfIngredient == sizeOfBakingList-1){
                views.setViewVisibility(R.id.nextBtnAtWid, View.INVISIBLE);
            }else{
                views.setViewVisibility(R.id.nextBtnAtWid, View.VISIBLE);
            }
        }catch (Exception e){}
    }
}

