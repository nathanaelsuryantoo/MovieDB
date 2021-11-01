package com.example.moviedb.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.model.Credits;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.Popular;
import com.example.moviedb.model.UpComing;
import com.example.moviedb.repositories.MovieRepository;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository repository;

    public MovieViewModel(@NonNull Application application){
        super(application);
        repository = MovieRepository.getInstance();
    }


    //==Begin of viewmodel get movie by id

    private MutableLiveData<Movies> resultGetMovieById = new MutableLiveData<>();
    public void getMovieById(String movieId){
        resultGetMovieById = repository.getMovieData(movieId);
    }
    public LiveData<Movies> getResultGetMovieById(){
        return resultGetMovieById;
    }

    //== End of viewmodel get movie by id

    //==Begin of viewmodel get movie by id

    private MutableLiveData<NowPlaying> resultGetNowPlaying = new MutableLiveData<>();
    public void getNowPlaying(int pages){
        resultGetNowPlaying = repository.getNowPlayingData(pages);
    }
    public LiveData<NowPlaying> getResultGetNowPlaying(){
        return resultGetNowPlaying;
    }

    //== End of viewmodel get movie by id
    private MutableLiveData<UpComing> resultGetUpComing = new MutableLiveData<>();
    public void getUpComing(int pages){
        resultGetUpComing = repository.getUpComingData(pages);
    }
    public LiveData<UpComing> getResultGetUpComing(){
        return resultGetUpComing;
    }

    private MutableLiveData<Credits> resultGetCredits = new MutableLiveData<>();
    public void getCredits(String movie_id){
        resultGetCredits = repository.getCreditsData(movie_id);
    }
    public LiveData<Credits> getResultGetCredits(){
        return resultGetCredits;
    }

    private MutableLiveData<Popular> resultGetPopular = new MutableLiveData<>();
    public void getPopular(int pages){
        resultGetPopular = repository.getPopularData(pages);
    }
    public LiveData<Popular> getResultGetPopular(){
        return resultGetPopular;
    }
}
