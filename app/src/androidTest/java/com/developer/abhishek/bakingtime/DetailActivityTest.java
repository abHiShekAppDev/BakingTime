package com.developer.abhishek.bakingtime;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.developer.abhishek.bakingtime.model.BakingListModel;
import com.developer.abhishek.bakingtime.model.Ingredients;
import com.developer.abhishek.bakingtime.model.Steps;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    private List<Ingredients> ingredientsList = new ArrayList<>();
    private List<Steps> stepsList = new ArrayList<>();
    private BakingListModel bakingListModel;

    @Rule
    public ActivityTestRule<DetailActivity> activityTestRule = new ActivityTestRule<>(DetailActivity.class,false,false);

    @Before
    public void setFakeData(){
        ingredientsList.add(new Ingredients("2","CUP","Cheese"));
        stepsList.add(new Steps("0","Intro","Recipe Introduction",null,null));
        bakingListModel = new BakingListModel("0","Nutella Pie",ingredientsList,stepsList,8,null);

        Intent intent = new Intent();
        intent.putExtra(DetailActivity.INTENT_KEY_FROM_HOME_PAGE,bakingListModel);
        activityTestRule.launchActivity(intent);
    }

    @Test
    public void selectingRecipe_openCorrespondingDescription(){
        onView(withId(R.id.recyclerViewAtDA)).perform(actionOnItemAtPosition(0, click()));
    }
}
