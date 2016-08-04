package com.rahul.movie.db;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.rahul.movie.db.model.MovieModel;
import com.rahul.movie.db.presenter.MoviePresenter;
import com.rahul.movie.db.presenter.MoviePresenterIMPL;
import com.rahul.movie.db.service.Constant;
import com.rahul.movie.db.service.MovieApi;
import com.rahul.movie.db.view.GridDividerDecoration;
import com.rahul.movie.db.view.IMovieView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class MainActivity extends AppCompatActivity implements IMovieView {

    private static final String TAG = "MainActivity";
    @BindView(android.R.id.list)
    RecyclerView list;
    private MovieAdapter adapter;
    ProgressDialog mProgressDialog;
    private MoviePresenterIMPL moviePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        list.setHasFixedSize(true);
        adapter = new MovieAdapter(this);
        list.addItemDecoration(new GridDividerDecoration(this));
        list.setAdapter(adapter);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();
        moviePresenter = new MoviePresenterIMPL(this);
        moviePresenter.onCreate();

    }

    @Override
    protected void onResume() {
        super.onResume();
        moviePresenter.onResume();
        if (adapter.getItemCount() > 0) {
            adapter.notifyDataSetChanged();
        } else {
            if (isPopularMovieSelected)
                moviePresenter.retrievePopularMovieList();
            else moviePresenter.retrieveHighestRatingMovieList();
        }
    }

    @Override
    protected void onDestroy() {
        moviePresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onCompleted() {
        cancelDialog();

    }

    boolean isPopularMovieSelected = true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_popular_movies) {
            declare(item, true);
            moviePresenter.retrievePopularMovieList();

            return true;
        }
        if (item.getItemId() == R.id.action_top_rated) {
            declare(item, false);
            moviePresenter.retrieveHighestRatingMovieList();


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void declare(MenuItem item, boolean which) {
        list.scrollToPosition(0);
        mProgressDialog.show();
        isPopularMovieSelected = which;
        item.setChecked(which);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        menu.findItem(R.id.action_popular_movies).setChecked(true);
        return super.onCreateOptionsMenu(menu);
    }


    private void cancelDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void onError(String message) {
        Log.d(TAG, message);
        cancelDialog();
    }

    @Override
    public void onNext(MovieModel movieModel) {
        adapter.addAll(movieModel.getResults());
    }

    @Override
    public Observable<MovieModel> getMovieModel() {
        return MovieApi.getInstance().getPopularMovies("popularity.desc");
    }

    @Override
    public Observable<MovieModel> getTopRatedMovieModel() {
        return MovieApi.getInstance().getHighestRatedMovie("US", "R", "vote_average.desc");
    }


    private static class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> implements OnItemClickListener {

        List<MovieModel.Result> results = new ArrayList<>();
        private Context mContext;

        MovieAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);

            return new MovieViewHolder(view, this);
        }

        void addAll(List<MovieModel.Result> resultList) {
            results.clear();
            results.addAll(resultList);
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder, int position) {
            MovieModel.Result result = results.get(position);
            String uriPath = result.getPosterPath();
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.placeholder);
            RequestOptions requestOptions = RequestOptions
                    .diskCacheStrategyOf(DiskCacheStrategy.DATA)
                    .centerCrop(mContext)
                    .placeholder(drawable)
                    .error(drawable);
            Glide.with(mContext)
                    .asBitmap()
                    .apply(requestOptions)
                    .load(Constant.IMAGE_BASE_URL + uriPath)
                    .transition(BitmapTransitionOptions.withCrossFade(400))
                    .into(holder.squareImageView);
            double voteAverage = result.getVoteAverage();
            float rating = (float) (voteAverage / 10f * 5f);
            String dateString = Constant.getDateString(result.getReleaseDate());
            holder.txtTitle.setText(mContext.getString(R.string.single_string, result.getTitle()));
            holder.txtDate.setText(mContext.getString(R.string.single_string,
                    TextUtils.isEmpty(dateString) ?
                            result.getReleaseDate() : dateString));
            holder.movieRating.setRating(rating);


        }


        @Override
        public int getItemCount() {
            return results.size();
        }


        @Override
        public void onItemClick(View view, int position) {
            MovieDetailActivity.start(mContext, view, results.get(position));

        }
    }


    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.squareImageView)
        ImageView squareImageView;
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.movieRating)
        RatingBar movieRating;

        OnItemClickListener itemClickListener;

        MovieViewHolder(View view, OnItemClickListener itemClickListener) {
            super(view);
            ButterKnife.bind(this, view);
            this.itemClickListener = itemClickListener;
        }

        @OnClick(R.id.list_item)
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}
