package com.example.moviedb.view.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.adapter.CreditsAdapter;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Credits;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView lbl_movie_id;
    private TextView lbl_title, lbl_overview, lbl_genre, lbl_rating, lbl_tagline, lbl_votecount, lbl_releasedate, lbl_popularity;
    private ImageView img_poster, img_backdrop, btn_back;
    private MovieViewModel viewModel;
    private Toolbar toolbar_movie_details;
    private LinearLayout llh_prodcompany;
    private RecyclerView rv_card_credits;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ProgressDialog progressdialog = ProgressDialog.show(getActivity(), "", "", true);
        progressdialog.setContentView(R.layout.item_loading);
        progressdialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = progressdialog.getWindow().getAttributes();
        progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressdialog.dismiss();
            }
        }, 500);
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        //lbl_movie_id = view.findViewById(R.id.lbl_movie_id_movie_details_fragment);
        lbl_title = view.findViewById(R.id.lbl_title_movie_details_fragment);
        lbl_overview = view.findViewById(R.id.lbl_overviewcontent_movie_details_fragment);
        img_poster = view.findViewById(R.id.img_poster_movie_details_fragment);
        lbl_genre = view.findViewById(R.id.lbl_genre_movie_details_fragment);
        lbl_rating = view.findViewById(R.id.lbl_rating_movie_details_fragment);
        llh_prodcompany = view.findViewById(R.id.llh_prodcompany_moviedetails_fragment);
        lbl_tagline = view.findViewById(R.id.lbl_tagline_movie_details_fragment);
        lbl_votecount = view.findViewById(R.id.lbl_votecount_movie_details_fragment);
        img_backdrop = view.findViewById(R.id.img_backdrop_movie_details_fragment);
        lbl_releasedate = view.findViewById(R.id.lbl_release_date_movie_details_fragment);
        lbl_popularity = view.findViewById(R.id.lbl_popularity_movie_details_fragment);
        btn_back = view.findViewById(R.id.btn_back_movie_details_fragment);
        rv_card_credits = view.findViewById(R.id.rv_card_credits_movie_details_fragment);
        //toolbar_movie_details = view.findViewById(R.id.toolbar_movie_details_fragment);
        viewModel = new ViewModelProvider(MovieDetailsFragment.this).get(MovieViewModel.class);
        String movieId = getArguments().getString("movieId");
        String fromfragment = getArguments().getString("fromfragment").toString();
        viewModel.getMovieById(movieId);
        viewModel.getCredits(movieId);
        viewModel.getResultGetMovieById().observe(getViewLifecycleOwner(), showResultMovie);
        viewModel.getResultGetCredits().observe(getViewLifecycleOwner(), showResultCredits);

//        toolbar_movie_details.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //finish();
//            }
//        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fromfragment.equals("nowplayingfragment")){
                    Navigation.findNavController(view).navigate(R.id.action_menu_movie_details_to_menu_now_playing);
                }else if(fromfragment.equals("upcomingfragment")){
                    Navigation.findNavController(view).navigate(R.id.action_menu_movie_details_to_menu_up_coming);
                }else if(fromfragment.equals("popularfragment")){
                    Navigation.findNavController(view).navigate(R.id.action_menu_movie_details_to_menu_popular);
                }
            }
        });



        //lbl_movie_id.setText(movieId);
        return view;
    }
    public Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String title = movies.getTitle();
            String overview = movies.getOverview();
            String tagline = movies.getTagline();
            String poster = ""+Const.IMG_URL + movies.getPoster_path().toString();
            String backdrop = ""+Const.IMG_URL + movies.getBackdrop_path().toString();
            String releasedate = movies.getRelease_date();
            String tanggal = releasedate.substring(8);
            String bulandepan = releasedate.substring(5, 6);
            String bulanbelakang = releasedate.substring(6, 7);
            String tahun = releasedate.substring(0, 4);
            String bulan = "";
            if(Integer.parseInt(bulandepan) == 0) {
                if (Integer.parseInt(bulanbelakang) == 1) {
                    bulan = "Jan";
                } else if (Integer.parseInt(bulanbelakang) == 2) {
                    bulan = "Feb";
                } else if (Integer.parseInt(bulanbelakang) == 3) {
                    bulan = "Mar";
                } else if (Integer.parseInt(bulanbelakang) == 4) {
                    bulan = "Apr";
                } else if (Integer.parseInt(bulanbelakang) == 5) {
                    bulan = "May";
                } else if (Integer.parseInt(bulanbelakang) == 6) {
                    bulan = "Jun";
                } else if (Integer.parseInt(bulanbelakang) == 7) {
                    bulan = "Jul";
                } else if (Integer.parseInt(bulanbelakang) == 8) {
                    bulan = "Aug";
                } else if (Integer.parseInt(bulanbelakang) == 9) {
                    bulan = "Sep";
                }
            }else if(Integer.parseInt(bulandepan) == 1){
                if(Integer.parseInt(bulanbelakang) == 0){
                    bulan = "Oct";
                }else if(Integer.parseInt(bulanbelakang) == 1){
                    bulan = "Nov";
                }else if(Integer.parseInt(bulanbelakang) == 2){
                    bulan = "Dec";
                }
            }
            int popularity = (int)(movies.getPopularity());
            List<Movies.Genres> listgenre = movies.getGenres();
            String genre = "";

            for(int i = 0; i<listgenre.size(); i++){
                if(i == (listgenre.size()-1)){
                    genre = genre + listgenre.get(i).getName();
                }else{
                    genre = genre + listgenre.get(i).getName() + ", ";
                }
            }
            int runtime = movies.getRuntime();
            Double rating = movies.getVote_average();
            int votecount = movies.getVote_count();
            lbl_title.setText(title);
            lbl_overview.setText(overview);
            lbl_genre.setText(genre);
            lbl_tagline.setText(tagline);
            lbl_rating.setText(String.valueOf(rating) + " / 10");
            lbl_votecount.setText("(" + String.valueOf(votecount) + ")");
            lbl_releasedate.setText(tanggal+" "+bulan+"\n"+tahun);
            lbl_popularity.setText(String.valueOf(popularity));
            Glide.with(MovieDetailsFragment.this).load(poster).into(img_poster);
            Glide.with(MovieDetailsFragment.this).load(backdrop).into(img_backdrop);

            for (int i = 0; i < movies.getProduction_companies().size(); i++){
                ImageView imageView = new ImageView(llh_prodcompany.getContext());
                String logopath = movies.getProduction_companies().get(i).getLogo_path();
                String logoprod = Const.IMG_URL + logopath;
                String nameprod = movies.getProduction_companies().get(i).getName();
                if(logopath == null){
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.tmdb_icon, getActivity().getTheme()));
                }else{
                    Glide.with(getActivity()).load(logoprod).into(imageView);
                }
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                imageView.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_gradient));
                if(i == 0){
                    lp.setMargins(0, 0,20,0 );
                }else if(i == (movies.getProduction_companies().size()-1)){
                    lp.setMargins(20, 0,0,0 );
                }else{
                    lp.setMargins(20, 0,20,0 );
                }
                imageView.setPadding(20, 20, 20,20);
                imageView.setLayoutParams(lp);
                llh_prodcompany.addView(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), nameprod, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };
    public Observer<Credits> showResultCredits = new Observer<Credits>() {
        @Override
        public void onChanged(Credits credits) {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);
            rv_card_credits.setLayoutManager(layoutManager);
            CreditsAdapter adapter = new CreditsAdapter(getActivity());
            adapter.setListCast(credits.getCast());
            rv_card_credits.setAdapter(adapter);
        }
    };
}