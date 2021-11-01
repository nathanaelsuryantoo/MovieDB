package com.example.moviedb.view.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.adapter.NowPlayingAdapter;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.view.activities.NowPlayingActivity;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
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
    private RecyclerView rv_now_playing;
    private MovieViewModel view_model;
    private int page_number = 1;
    private Boolean isLoading = false;
    private List<NowPlaying.Results> resultsList = new ArrayList<>();
    private NowPlayingAdapter adapter ;
    private Boolean check = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        rv_now_playing = view.findViewById(R.id.rv_now_playing_fragment);

        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getNowPlaying(page_number);
        view_model.getResultGetNowPlaying().observe(getActivity(), showNowPlaying);

        adapter = new NowPlayingAdapter(getActivity());
        return view;
    }
    private Observer<NowPlaying> showNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {


            if(page_number == 1){
                rv_now_playing.setLayoutManager(new LinearLayoutManager(getActivity()));

                adapter.setListNowPlaying(nowPlaying.getResults());
                rv_now_playing.setAdapter(adapter);
                resultsList.addAll(nowPlaying.getResults());
            }else{
                resultsList.add(null);
                adapter.setListNowPlaying(resultsList);
                adapter.notifyItemInserted(resultsList.size()-1);
                resultsList.remove(resultsList.size()-1);
                resultsList.addAll(nowPlaying.getResults());
                adapter.setListNowPlaying(resultsList);
                adapter.notifyDataSetChanged();
                check = false;
            }

            rv_now_playing.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    if(!isLoading){

                        if(layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == resultsList.size()-2 && check == false){
                            page_number++;
                            isLoading = true;
                            ProgressDialog progressdialog = ProgressDialog.show(getActivity(), "", "", true);
                            progressdialog.setContentView(R.layout.item_loading);
                            progressdialog.getWindow().setGravity(Gravity.BOTTOM);
                            WindowManager.LayoutParams params = progressdialog.getWindow().getAttributes();
                            params.y = 200;
                            progressdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            view_model.getNowPlaying(page_number);
                            check = true;
                            isLoading = false;
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressdialog.dismiss();
                                    view_model.getResultGetNowPlaying().observe(getActivity(), showNowPlaying);
                                }
                            }, 500);


                        }

                    }
                }
            });

            ItemClickSupport.addTo(rv_now_playing).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                    return false;
                }
            });
            ItemClickSupport.addTo(rv_now_playing).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("movieId", ""+nowPlaying.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate(R.id.action_menu_now_playing_to_menu_movie_details, bundle);
                }
            });
        }
    };




}