package com.ap.collegespacev2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ap.collegespacev2.Models.NpediaPosts;
import com.ap.collegespacev2.R;
import com.bumptech.glide.Glide;


import java.util.ArrayList;

/**
 * Created by anirudh on 26/07/17.
 */

public class NsitPediaAdapter extends RecyclerView.Adapter<NsitPediaAdapter.PostsViewHolder> {

    ArrayList<NpediaPosts> arrayListPosts ;
    Context mContext ;
    OnItemCLickedListener onItemCLickedListener;

    public NsitPediaAdapter(ArrayList<NpediaPosts> arrayListPosts, Context mContext , OnItemCLickedListener oicl) {
        this.arrayListPosts = arrayListPosts;
        this.mContext = mContext;
        this.onItemCLickedListener = oicl ;

    }
    public void updatePosts(ArrayList<NpediaPosts> arrayListPosts){
        this.arrayListPosts = arrayListPosts ;
        notifyDataSetChanged();
    }
    public interface OnItemCLickedListener {
        void onItemCLicked(View view , NpediaPosts thisPost);
    }
    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.list_nsitpedia_posts , parent , false) ;
        return new PostsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        final NpediaPosts thisPost = arrayListPosts.get(position) ;
        holder.tvTitle.setText(thisPost.getTitle());
        holder.tvDescription.setText(Html.fromHtml(thisPost.getExcerpt()));
        holder.tvAuthor.setText(thisPost.getAuthor().getUsername());
        if(thisPost.getAuthor().getAvatar().equals("avatar")) {
            Glide.with(mContext)
                    .load(R.drawable.ic_person)
                    .into(holder.ivAvatar);
        }else{
            Glide.with(mContext)
                    .load(thisPost.getAuthor().getAvatar())
                    .into(holder.ivAvatar);
        }

        if(!thisPost.getFeatured_image().getGuid().equals("guid")){
            Glide.with(mContext)
                    .load(thisPost.getFeatured_image().getGuid())
                    .into(holder.ivFeaturedImage) ;

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemCLickedListener.onItemCLicked(v , thisPost);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListPosts.size();
    }

    class PostsViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle , tvAuthor , tvDescription ;
        ImageView ivAvatar  , ivFeaturedImage;
        View itemView ;
        public PostsViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle) ;
            tvAuthor = (TextView) itemView.findViewById(R.id.tvPostersName) ;
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription) ;
            ivAvatar = (ImageView)itemView.findViewById(R.id.ivProfilePic) ;
            ivFeaturedImage = (ImageView)itemView.findViewById(R.id.ivFeaturedImage) ;
            this.itemView = itemView ;
        }
    }
}
