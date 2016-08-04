package com.rahul.movie.db;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rahul.movie.db.model.MovieModel;
import com.rahul.movie.db.service.Constant;
import com.rahul.movie.db.view.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_RESULT = "MOVIE_RESULT";
    @BindView(R.id.txtOriginalTitle)
    TextView txtOriginalTitle;
    @BindView(R.id.movieRating)
    RatingBar movieRating;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.squareImageView)
    SquareImageView squareImageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        MovieModel.Result result = getIntent().getParcelableExtra(MOVIE_RESULT);

        String uriPath = TextUtils.isEmpty(result.getBackdropPath()) ? result.getPosterPath() : result.getBackdropPath();
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.placeholder);
        displayPic(uriPath, drawable);
        double voteAverage = result.getVoteAverage();
        float rating = (float) (voteAverage / 10f * 5f);

        String dateString = Constant.getDateString(result.getReleaseDate());
        txtOriginalTitle.setText(getString(R.string.single_string, result.getOriginalTitle()));
        txtDate.setText(getString(R.string.single_string,
                TextUtils.isEmpty(dateString) ?
                        result.getReleaseDate() : dateString));
        movieRating.setRating(rating);
        txtDescription.setText(getString(R.string.single_string, result.getOverview()));

    }

    private void displayPic(String uriPath, Drawable drawable) {
        RequestOptions requestOptions = RequestOptions
                .diskCacheStrategyOf(DiskCacheStrategy.DATA)
                .centerCrop(this)
                .placeholder(drawable)
                .error(drawable);
        Glide.with(this)
                .asBitmap()
                .apply(requestOptions)
                .load(Constant.IMAGE_BASE_URL + uriPath)
                .transition(BitmapTransitionOptions.withCrossFade(1000))
                .into(new BitmapImageViewTarget(squareImageView) {
                    @Override
                    public void onResourceReady(Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        colorize(resource);
                        super.onResourceReady(resource, transition);

                    }
                });
    }

    private void colorize(Bitmap resource) {
        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                applyPalette(palette);
            }
        });
    }

    private void applyPalette(final Palette mPalette) {
        final int darkMutedColor = mPalette.getDarkMutedColor(getColor());

        getWindow().setBackgroundDrawable(new ColorDrawable(darkMutedColor));


        //  getWindow().setBackgroundDrawable(new ColorDrawable(darkMutedColor));
        txtOriginalTitle.setTextColor(mPalette.getVibrantColor(txtOriginalTitle.getCurrentTextColor()));
        txtDescription.setTextColor(mPalette.getLightVibrantColor(txtDescription.getCurrentTextColor()));
        txtDate.setTextColor(mPalette.getVibrantColor(txtDate.getCurrentTextColor()));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public int getColor() {
        TypedValue a = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.windowBackground, a, true);
        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT) {
            // windowBackground is a color
            color = a.data;
        }
        return color;
    }

    public static void start(Context context, View view, MovieModel.Result result) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context
                , view.findViewById(R.id.squareImageView), context.getString(R.string.banner_transition));
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MOVIE_RESULT, result);
        ActivityCompat.startActivity((Activity) context, intent, optionsCompat.toBundle());
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }
}

