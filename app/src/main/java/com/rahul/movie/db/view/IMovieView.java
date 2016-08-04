package com.rahul.movie.db.view;

import com.rahul.movie.db.model.MovieModel;

import rx.Observable;

/**
 * \tCreated by Rahul on 8/3/2016.
 */

public interface IMovieView {
    void onCompleted();

    void onError(String message);

    void onNext(MovieModel movieModel);

    Observable<MovieModel> getMovieModel();

    Observable<MovieModel> getTopRatedMovieModel();
}
