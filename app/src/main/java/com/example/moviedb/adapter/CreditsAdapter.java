package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Credits;
import com.example.moviedb.model.NowPlaying;

import java.util.List;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.CardViewViewHolder> {
    private Context context;
    private List<Credits.Cast> listCast;
    private List<Credits.Cast> getListCast (){return listCast;}
    public void setListCast (List<Credits.Cast> listCast){
        this.listCast = listCast;
    }
    public CreditsAdapter (Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public CreditsAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_credits, parent, false);
        return new CreditsAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditsAdapter.CardViewViewHolder holder, int position) {
        final Credits.Cast cast = getListCast().get(position);
        holder.lbl_ori_name.setText(cast.getOriginal_name());
        holder.lbl_char_name.setText(cast.getCharacter());
        if (cast.getProfile_path() == null) {
            holder.img_credits.setImageDrawable(context.getResources().getDrawable(R.drawable.tmdb_icon));
        }else{
            Glide.with(context).load(Const.IMG_URL + cast.getProfile_path()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.img_credits);
        }
    }

    @Override
    public int getItemCount() {
        return listCast.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView lbl_ori_name, lbl_char_name;
        ImageView img_credits;
        CardView cv_credits;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            lbl_ori_name = itemView.findViewById(R.id.lbl_ori_name_card_credits);
            lbl_char_name = itemView.findViewById(R.id.lbl_char_name_card_credits);
            img_credits = itemView.findViewById(R.id.img_credits_card_credits);
            cv_credits = itemView.findViewById(R.id.cv_card_credits);
        }
    }
}
