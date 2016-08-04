package com.rahul.movie.db.model;

/**
 * \tCreated by Rahul on 8/3/2016.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class MovieModel implements Parcelable {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList<Result>();
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;



    /**
     * @return The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    /**
     * @return The totalResults
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     * @param totalResults The total_results
     */
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * @return The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages The total_pages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }


    public static class Result implements Parcelable {

        @SerializedName("poster_path")
        @Expose
        private String posterPath;
        @SerializedName("adult")
        @Expose
        private Boolean adult;
        @SerializedName("overview")
        @Expose
        private String overview;
        @SerializedName("release_date")
        @Expose
        private String releaseDate;
        @SerializedName("genre_ids")
        @Expose
        private List<Integer> genreIds = new ArrayList<Integer>();
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("original_title")
        @Expose
        private String originalTitle;
        @SerializedName("original_language")
        @Expose
        private String originalLanguage;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("backdrop_path")
        @Expose
        private String backdropPath;
        @SerializedName("popularity")
        @Expose
        private Double popularity;
        @SerializedName("vote_count")
        @Expose
        private Integer voteCount;
        @SerializedName("video")
        @Expose
        private Boolean video;
        @SerializedName("vote_average")
        @Expose
        private Double voteAverage;

        /**
         * @return The posterPath
         */
        public String getPosterPath() {
            return posterPath;
        }

        /**
         * @param posterPath The poster_path
         */
        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        /**
         * @return The adult
         */
        public Boolean getAdult() {
            return adult;
        }

        /**
         * @param adult The adult
         */
        public void setAdult(Boolean adult) {
            this.adult = adult;
        }

        /**
         * @return The overview
         */
        public String getOverview() {
            return overview;
        }

        /**
         * @param overview The overview
         */
        public void setOverview(String overview) {
            this.overview = overview;
        }

        /**
         * @return The releaseDate
         */
        public String getReleaseDate() {
            return releaseDate;
        }

        /**
         * @param releaseDate The release_date
         */
        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        /**
         * @return The genreIds
         */
        public List<Integer> getGenreIds() {
            return genreIds;
        }

        /**
         * @param genreIds The genre_ids
         */
        public void setGenreIds(List<Integer> genreIds) {
            this.genreIds = genreIds;
        }

        /**
         * @return The id
         */
        public Integer getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * @return The originalTitle
         */
        public String getOriginalTitle() {
            return originalTitle;
        }

        /**
         * @param originalTitle The original_title
         */
        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        /**
         * @return The originalLanguage
         */
        public String getOriginalLanguage() {
            return originalLanguage;
        }

        /**
         * @param originalLanguage The original_language
         */
        public void setOriginalLanguage(String originalLanguage) {
            this.originalLanguage = originalLanguage;
        }

        /**
         * @return The title
         */
        public String getTitle() {
            return title;
        }

        /**
         * @param title The title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return The backdropPath
         */
        public String getBackdropPath() {
            return backdropPath;
        }

        /**
         * @param backdropPath The backdrop_path
         */
        public void setBackdropPath(String backdropPath) {
            this.backdropPath = backdropPath;
        }

        /**
         * @return The popularity
         */
        public Double getPopularity() {
            return popularity;
        }

        /**
         * @param popularity The popularity
         */
        public void setPopularity(Double popularity) {
            this.popularity = popularity;
        }

        /**
         * @return The voteCount
         */
        public Integer getVoteCount() {
            return voteCount;
        }

        /**
         * @param voteCount The vote_count
         */
        public void setVoteCount(Integer voteCount) {
            this.voteCount = voteCount;
        }

        /**
         * @return The video
         */
        public Boolean getVideo() {
            return video;
        }

        /**
         * @param video The video
         */
        public void setVideo(Boolean video) {
            this.video = video;
        }

        /**
         * @return The voteAverage
         */
        public Double getVoteAverage() {
            return voteAverage;
        }

        /**
         * @param voteAverage The vote_average
         */
        public void setVoteAverage(Double voteAverage) {
            this.voteAverage = voteAverage;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.posterPath);
            dest.writeValue(this.adult);
            dest.writeString(this.overview);
            dest.writeString(this.releaseDate);
            dest.writeList(this.genreIds);
            dest.writeValue(this.id);
            dest.writeString(this.originalTitle);
            dest.writeString(this.originalLanguage);
            dest.writeString(this.title);
            dest.writeString(this.backdropPath);
            dest.writeValue(this.popularity);
            dest.writeValue(this.voteCount);
            dest.writeValue(this.video);
            dest.writeValue(this.voteAverage);
        }

        public Result() {
        }

        Result(Parcel in) {
            this.posterPath = in.readString();
            this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.overview = in.readString();
            this.releaseDate = in.readString();
            this.genreIds = new ArrayList<Integer>();
            in.readList(this.genreIds, Integer.class.getClassLoader());
            this.id = (Integer) in.readValue(Integer.class.getClassLoader());
            this.originalTitle = in.readString();
            this.originalLanguage = in.readString();
            this.title = in.readString();
            this.backdropPath = in.readString();
            this.popularity = (Double) in.readValue(Double.class.getClassLoader());
            this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
            this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        }

        public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
            @Override
            public Result createFromParcel(Parcel source) {
                return new Result(source);
            }

            @Override
            public Result[] newArray(int size) {
                return new Result[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.page);
        dest.writeTypedList(this.results);
        dest.writeValue(this.totalResults);
        dest.writeValue(this.totalPages);
    }

    public MovieModel() {
    }

    protected MovieModel(Parcel in) {
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(Result.CREATOR);
        this.totalResults = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}