package com.example.catalyst.ata_test.adapters;

import android.content.Context;
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
 * Created by dsloane on 6/1/2016.
 */
public class TeamSearchResultAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater inflater;
    private List<Team> teams = new ArrayList<Team>();

    private final String TAG = TeamSearchResultAdapter.class.getSimpleName();

    public TeamSearchResultAdapter(Context context, List<Team> teamsList) {
        mContext = context;
        teams = teamsList;
    }

    public void clear() {
        teams.clear();
    }

    public int getCount() {
        return teams.size();
    }

    public Team getItem(int location) {
        return teams.get(location);
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
            convertView = inflater.inflate(R.layout.list_team_results, null);
            holder.teamName = (TextView) convertView.findViewById(R.id.team_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Team team = teams.get(position);
        holder.teamName.setText(team.getName());

        return convertView;
    }


    private static class ViewHolder {
        TextView teamName;
    }
}
