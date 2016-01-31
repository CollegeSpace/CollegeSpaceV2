package com.ap.collegespacev2.Adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ap.collegespacev2.Helper.NavItem;
import com.ap.collegespacev2.Helper.UpdatesItem;
import com.ap.collegespacev2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by amaneureka on 31-Jan-16.
 */
public class PostsListAdapter extends BaseAdapter
{
    Context mContext;
    ArrayList<UpdatesItem> mUpdatesItem;

    public PostsListAdapter(Context context, ArrayList<UpdatesItem> updatesItem)
    {
        this.mContext = context;
        this.mUpdatesItem = updatesItem;
    }

    @Override
    public int getCount()
    {
        return mUpdatesItem.size();
    }

    @Override
    public UpdatesItem getItem(int position)
    {
        return mUpdatesItem.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowview = convertView;
        ViewHolder holder;

        if (rowview == null)
        {
            holder = new ViewHolder();
            final LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = inflater.inflate(R.layout.row_updates_list, parent, false);
            holder.headlineView = (TextView)rowview.findViewById(R.id.updates_post_title);
            holder.dateView = (TextView)rowview.findViewById(R.id.updates_post_date);
            rowview.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        //Get Current feed Item
        UpdatesItem post = getItem(position);
        String date = post.getDate();

        //Calculate time ago "2015-12-07T15:57:31"
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        try
        {
            Date ddate = formatter.parse(date);
            long millis = ddate.getTime();
            date = DateUtils.getRelativeTimeSpanString(millis, System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS).toString();
        }
        catch(ParseException e) { }

        holder.headlineView.setText(post.getTitle());
        holder.dateView.setText(date);
        return rowview;
    }

    static class ViewHolder
    {
        TextView headlineView;
        TextView dateView;
    }
}
