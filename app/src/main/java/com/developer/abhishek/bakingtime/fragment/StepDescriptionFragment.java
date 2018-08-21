package com.developer.abhishek.bakingtime.fragment;

import android.content.res.Configuration;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.abhishek.bakingtime.R;
import com.developer.abhishek.bakingtime.model.Ingredients;
import com.developer.abhishek.bakingtime.model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDescriptionFragment extends Fragment {

    private final String STEP_SAVED_KEY = "step_key";
    private final String INGREDIENTS_SAVED_KEY = "ingredients_key";
    private final String PLAYBACK_POSITION_SAVED_KEY = "playback_position_key";
    private final String FLAG_SAVED_KEY = "flag_key";
    private final String PLAY_WHEN_READY_SAVED_KEY = "play_when_ready_key";

    @BindView(R.id.descriptionHeadAtDA)
    TextView head;
    @BindView(R.id.descriptionTextAtDA)
    TextView descText;
    @BindView(R.id.exoPlayerPortAtDA)
    SimpleExoPlayerView simpleExoPlayerViewPort;
    @BindView(R.id.exoPlayerLandAtDA)
    SimpleExoPlayerView simpleExoPlayerViewLand;

    private SimpleExoPlayer exoPlayer;
    private long playbackPosition = 0;
    private boolean playWhenReady = true;

    private Steps steps;
    private ArrayList<Ingredients> ingredients = new ArrayList<>();
    private boolean flag;
    private String videoUrl;
    private boolean isPortrait = true;

    public void setSteps(Steps steps) {
        this.steps = steps;
    }

    public void setIngredients(List<Ingredients> ingredient) {
       if(ingredient != null){
           ingredients.addAll(ingredient);
       }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public StepDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(STEP_SAVED_KEY)){
                steps = savedInstanceState.getParcelable(STEP_SAVED_KEY);
            }
            if(savedInstanceState.containsKey(INGREDIENTS_SAVED_KEY)){
                ingredients = savedInstanceState.getParcelableArrayList(INGREDIENTS_SAVED_KEY);
            }
            if(savedInstanceState.containsKey(PLAYBACK_POSITION_SAVED_KEY)){
                playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION_SAVED_KEY);
            }
            if(savedInstanceState.containsKey(FLAG_SAVED_KEY)){
                flag = savedInstanceState.getBoolean(FLAG_SAVED_KEY);
            }
            if(savedInstanceState.containsKey(PLAY_WHEN_READY_SAVED_KEY)) {
                playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_SAVED_KEY);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_description, container, false);
        ButterKnife.bind(this,view);
        showBar();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkOrientation();

        if(flag){
            //      INGREDIENTS
            simpleExoPlayerViewPort.setVisibility(View.GONE);
            simpleExoPlayerViewLand.setVisibility(View.GONE);
            head.setText(getActivity().getResources().getString(R.string.ingredientsStr));
            String str = "";
            for(int i=0;i<ingredients.size();i++){
                str += ingredients.get(i).getIngredient()+"   "+ingredients.get(i).getQuantity()+"  "+ingredients.get(i).getMeasure()+"\n";
            }
            descText.setText(str);
        }else{
            //      STEPS
            head.setText(steps.getShortDescription());
            descText.setText(steps.getDescription());

            videoUrl = steps.getVideoURL();

            if(videoUrl == null || videoUrl.isEmpty()){
                simpleExoPlayerViewPort.setVisibility(View.GONE);
                simpleExoPlayerViewLand.setVisibility(View.GONE);
            }else{
                if(isPortrait){
                    simpleExoPlayerViewPort.setVisibility(View.VISIBLE);
                    simpleExoPlayerViewLand.setVisibility(View.GONE);
                }else{
                    simpleExoPlayerViewLand.setVisibility(View.VISIBLE);
                    simpleExoPlayerViewPort.setVisibility(View.GONE);
                }
                initializePlayer();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(INGREDIENTS_SAVED_KEY, ingredients);
        outState.putParcelable(STEP_SAVED_KEY, steps);
        outState.putBoolean(FLAG_SAVED_KEY, flag);
        if(exoPlayer != null){
            outState.putLong(PLAYBACK_POSITION_SAVED_KEY, exoPlayer.getCurrentPosition());
            outState.putBoolean(PLAY_WHEN_READY_SAVED_KEY, exoPlayer.getPlayWhenReady());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer(){
        exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()), new DefaultTrackSelector(), new DefaultLoadControl());

        if(isPortrait){
            simpleExoPlayerViewPort.setPlayer(exoPlayer);
            simpleExoPlayerViewPort.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        }else{
            simpleExoPlayerViewLand.setPlayer(exoPlayer);
            simpleExoPlayerViewLand.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        }

        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl),new DefaultHttpDataSourceFactory("exoPlayerVideo"), new DefaultExtractorsFactory(), null, null);
        exoPlayer.prepare(mediaSource, true, false);

        exoPlayer.seekTo(playbackPosition);
        exoPlayer.setPlayWhenReady(playWhenReady);

        hideBar();
    }

    private void checkOrientation(){
        int configuration = this.getResources().getConfiguration().orientation;
        if(configuration == Configuration.ORIENTATION_PORTRAIT){
            isPortrait = true;
        }else{
            isPortrait = false;
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    private void showBar(){
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    private void hideBar(){
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

}
