package com.example.catalyst.ata_test.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsloane on 4/27/2016.
 */
public class UserSearchResultAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater inflater;
    private List<User> resultList = new ArrayList<User>();

    private static final String TAG = DashboardAdapter.class.getSimpleName();

    public UserSearchResultAdapter(Context context, List<User> resultList) {
        mContext = context;
        this.resultList = resultList;
    }

    public void clear() {
        resultList.clear();
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int location) {
        return resultList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if (inflater == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_search_results, null);
            holder.name = (TextView) convertView.findViewById(R.id.user_name);
            holder.title = (TextView) convertView.findViewById(R.id.user_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User u = resultList.get(position);

        holder.name.setText(u.getFirstName() + " " + u.getLastName());
        holder.title.setText(u.getTitle());

        return convertView;
    }



    private static class ViewHolder {
        TextView name;
        TextView title;
    }
}
