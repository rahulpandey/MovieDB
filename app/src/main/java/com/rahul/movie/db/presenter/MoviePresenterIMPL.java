package com.rahul.movie.db.presenter;

import com.rahul.movie.db.model.MovieModel;
import com.rahul.movie.db.view.IMovieView;

import rx.Observer;

/**
 * \tCreated by Rahul on 8/3/2016.
 */

public class MoviePresenterIMPL extends BasePresenter implements MoviePresenter, Observer<MovieModel> {

    private IMovieView iMovieView;

    public MoviePresenterIMPL(IMovieView iMovieView) {
        this.iMovieView = iMovieView;
    }

    @Override
    public void retrievePopularMovieList() {
        unSubscribeAll();
        subscribe(iMovieView.getMovieModel(), MoviePresenterIMPL.this);

    }


    @Override
    public void retrieveHighestRatingMovieList() {

        unSubscribeAll();
        subscribe(iMovieView.getTopRatedMovieModel(), MoviePresenterIMPL.this);

    }

    @Override
    public void onCompleted() {
        iMovieView.onCompleted();

    }

    @Override
    public void onError(Throwable e) {
        iMovieView.onError(e.getMessage());
    }

    @Override
    public void onNext(MovieModel movieModel) {
        iMovieView.onNext(movieModel);
    }
}
