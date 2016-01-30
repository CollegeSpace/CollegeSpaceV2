package com.ap.collegespacev2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ap.collegespacev2.Helper.UpdatesItem;
import com.ap.collegespacev2.R;

import java.util.ArrayList;

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
        View view;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_updates_list, null);
        }
        else
        {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.updates_post_title);
        titleView.setText(getItem(position).getTitle());
        return view;
    }
}
