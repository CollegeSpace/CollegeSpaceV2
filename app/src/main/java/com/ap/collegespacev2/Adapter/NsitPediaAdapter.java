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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by anirudh on 26/07/17.
 */

public class NsitPediaAdapter extends RecyclerView.Adapter<NsitPediaAdapter.PostsViewHolder> {

    ArrayList<NpediaPosts> arrayListPosts ;
    Context mContext ;

    public NsitPediaAdapter(ArrayList<NpediaPosts> arrayListPosts, Context mContext) {
        this.arrayListPosts = arrayListPosts;
        this.mContext = mContext;
    }
    public void updatePosts(ArrayList<NpediaPosts> arrayListPosts){
        this.arrayListPosts = arrayListPosts ;
        notifyDataSetChanged();
    }
    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.list_nsitpedia_posts , parent , false) ;


        return new PostsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        NpediaPosts thisPost = arrayListPosts.get(position) ;
        holder.tvTitle.setText(thisPost.getTitle());
        holder.tvDescription.setText(Html.fromHtml(thisPost.getExcerpt()));
        holder.tvAuthor.setText(thisPost.getAuthor().getUsername());
        Picasso.with(mContext)
                .load(thisPost.getAuthor().getAvatar())
                .into(holder.ivAvatar);

    }

    @Override
    public int getItemCount() {
        return arrayListPosts.size();
    }

    class PostsViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle , tvAuthor , tvDescription ;
        ImageView ivAvatar ;
        public PostsViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle) ;
            tvAuthor = (TextView) itemView.findViewById(R.id.tvPostersName) ;
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription) ;
            ivAvatar = (ImageView)itemView.findViewById(R.id.ivProfilePic) ;
        }
    }
}
