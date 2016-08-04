package com.rahul.movie.db.service;

import android.support.annotation.NonNull;

import com.rahul.movie.db.model.MovieModel;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * \tCreated by Rahul on 8/2/2016.
 */

public class MovieApi {


    private static MovieService instance;

    public static synchronized MovieService getInstance() {
        if (instance == null) {
            instance = getRetrofit().create(MovieService.class);

        }
        return instance;
    }

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit
                .Builder()
                .baseUrl(Constant.BASE_URL)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public interface MovieService {

        @GET("discover/movie")
        Observable<MovieModel> getPopularMovies(@Query("sort_by") String sortBy);

        @GET("discover/movie")
        Observable<MovieModel> getHighestRatedMovie(@Query("certification_country") String country,
                                                    @Query("certification") String certification, @Query("sort_by") String sortBy);

    }

    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient
                .Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(new ApiKeyInterceptor())
                .addInterceptor(logging)
                .build();
    }

    private static class ApiKeyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", Constant.API_KEY).build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        }
    }
}
