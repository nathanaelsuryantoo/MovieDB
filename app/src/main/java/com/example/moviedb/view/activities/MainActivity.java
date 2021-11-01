package com.example.moviedb.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private MovieViewModel viewModel;
    private Button btn_hit;
    private TextView txt_show;
    private TextInputLayout til_movieid;
    private ImageView img_movieposter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_movieposter = findViewById(R.id.img_movieposter_main);
        txt_show = findViewById(R.id.txt_show_main);
        til_movieid = findViewById(R.id.til_movieid_main);
        viewModel = new ViewModelProvider(MainActivity.this).get(MovieViewModel.class);
        btn_hit = findViewById(R.id.button_hit_main);
        btn_hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieid = til_movieid.getEditText().getText().toString().trim();
                if(movieid.isEmpty()){
                    til_movieid.setError("Please Fill the Movie ID");
                }else {
                    til_movieid.setError(null);
                    viewModel.getMovieById(movieid);
                    viewModel.getResultGetMovieById().observe(MainActivity.this, showResultMovie);
                }
            }
        });

    }
    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            if(movies == null){
                txt_show.setText("Movie ID is not available on the MovieDB");
            }else {
                String title = movies.getTitle();

                String img_path = Const.IMG_URL + movies.getPoster_path().toString();
                Glide.with(MainActivity.this).load(img_path).into(img_movieposter);
                txt_show.setText(title);
            }
        }
    };
}