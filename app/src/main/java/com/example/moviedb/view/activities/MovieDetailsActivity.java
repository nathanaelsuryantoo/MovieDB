package com.example.moviedb.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView lbl_title, lbl_overview, lbl_duration, lbl_genre, lbl_rating;
    private ImageView img_poster;
    private MovieViewModel viewModel;
    private Toolbar toolbar_movie_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();

        lbl_title = findViewById(R.id.lbl_title_movie_details);
        lbl_overview = findViewById(R.id.lbl_overviewcontent_movie_details);
        img_poster = findViewById(R.id.img_poster_movie_details);
        lbl_duration = findViewById(R.id.lbl_duration_movie_details);
        lbl_genre = findViewById(R.id.lbl_genre_movie_details);
        lbl_rating = findViewById(R.id.lbl_rating_movie_details);
        toolbar_movie_details = findViewById(R.id.toolbar_movie_details);
        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        viewModel.getMovieById(intent.getStringExtra("movie_id"));
        viewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showResultMovie);

        toolbar_movie_details.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String title = movies.getTitle();
            String overview = movies.getOverview();
            String poster = Const.IMG_URL + movies.getPoster_path().toString();
            List<Movies.Genres> listgenre = movies.getGenres();
            String genre = "";


                if(listgenre.size() > 1){
                    genre = listgenre.get(0).getName() + "\n" + listgenre.get(1).getName();
                }else{
                    genre = listgenre.get(0).getName();
                }

            int runtime = movies.getRuntime();
            Double rating = movies.getVote_average();
            lbl_title.setText(title);
            lbl_overview.setText(overview);
            lbl_genre.setText(genre);
            lbl_duration.setText(String.valueOf(runtime)+" Minutes");
            lbl_rating.setText(String.valueOf(rating) + " / 10");
            Glide.with(MovieDetailsActivity.this).load(poster).into(img_poster);
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }


}