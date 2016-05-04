package com.example.catalyst.ata_test.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.models.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsloane on 4/27/2016.
 */
public class DashboardAdapter extends BaseAdapter {
    private Context mContext;

    private LayoutInflater inflater;
    private List<Team> teamList = new ArrayList<Team>();

    private static final String TAG = DashboardAdapter.class.getSimpleName();

    public DashboardAdapter(Context context, List<Team> teamList) {
        mContext = context;
        this.teamList = teamList;
    }

    public void clear() {
        teamList.clear();
    }

    @Override
    public int getCount() {
        return teamList.size();
    }

    @Override
    public Object getItem(int location) {
        return teamList.get(location);
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
            convertView = inflater.inflate(R.layout.list_dashboard, null);
            holder.name = (TextView) convertView.findViewById(R.id.team_name);
            holder.id = (TextView) convertView.findViewById(R.id.team_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String s = teamList.get(position).getName();
        String id = teamList.get(position).getId();

        holder.name.setText(s);
        holder.id.setText(id);

        return convertView;
    }



    private static class ViewHolder {
        TextView name;
        TextView id;
    }

}
