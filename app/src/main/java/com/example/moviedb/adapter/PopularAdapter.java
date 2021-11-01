package com.example.moviedb.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Popular;
import com.example.moviedb.model.UpComing;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.CardViewViewHolder> {
    private Context context;
    private List<Popular.Results> popularList;
    private List<Popular.Results> getPopularList(){
        return popularList;
    }
    public void setPopularList(List<Popular.Results> popularList){
        this.popularList = popularList;
    }
    public PopularAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public PopularAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_now_playing, parent, false);
        return new PopularAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.CardViewViewHolder holder, int position) {
        final Popular.Results results = getPopularList().get(position);
        holder.lbl_title.setText(results.getTitle());
        holder.lbl_overview.setText(results.getOverview());
        holder.lbl_releasedate.setText(results.getRelease_date());
        Glide.with(context).load(Const.IMG_URL+results.getPoster_path()).into(holder.img_poster);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("movieId", ""+results.getId());
                bundle.putString("fromfragment", "popularfragment");
                Navigation.findNavController(view).navigate(R.id.action_menu_popular_to_menu_movie_details, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView lbl_title, lbl_overview, lbl_releasedate;
        ImageView img_poster;
        CardView cv;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            lbl_title = itemView.findViewById(R.id.lbl_title_card_nowplaying);
            lbl_overview = itemView.findViewById(R.id.lbl_overview_card_nowplaying);
            lbl_releasedate = itemView.findViewById(R.id.lbl_releasedate_card_nowplaying);
            img_poster = itemView.findViewById(R.id.img_poster_card_now_playing);
            cv = itemView.findViewById(R.id.cv_card_now_playing);
        }
    }
}
