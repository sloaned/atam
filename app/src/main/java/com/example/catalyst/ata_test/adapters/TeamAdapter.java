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
 * Created by dsloane on 5/27/2016.
 */
public class TeamAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater inflater;
    private List<Team> teams = new ArrayList<Team>();

    private final String TAG = TeamAdapter.class.getSimpleName();

    public TeamAdapter(Context context, List<Team> teamsList) {
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
            convertView = inflater.inflate(R.layout.list_teams, null);
            holder.teamName = (TextView) convertView.findViewById(R.id.team_name);
            holder.teamDescription = (TextView) convertView.findViewById(R.id.team_description);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Team team = teams.get(position);
        holder.teamName.setText(team.getName());
        holder.teamDescription.setText(team.getDescription());

        return convertView;
    }

    private static class ViewHolder {
        TextView teamName;
        TextView teamDescription;
    }
    /*

    private Context mContext;

    private LayoutInflater inflater;
    private List<Kudo> kudos = new ArrayList<Kudo>();

    private final String TAG = KudosAdapter.class.getSimpleName();

    public KudosAdapter(Context context, List<Kudo> kudosList) {
        mContext = context;
        kudos = kudosList;
    }

    public void clear() {
        kudos.clear();
    }

    public int getCount() {
        return kudos.size();
    }

    public Kudo getItem(int location) {
        return kudos.get(location);
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
            convertView = inflater.inflate(R.layout.list_kudos, null);
            holder.reviewerName = (TextView) convertView.findViewById(R.id.reviewer_name);
            holder.kudoDate = (TextView) convertView.findViewById(R.id.kudo_date);
            holder.kudoContent = (TextView) convertView.findViewById(R.id.kudo_content);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Kudo kudo = kudos.get(position);
        holder.reviewerName.setText(kudo.getReviewer().getFirstName() + " " + kudo.getReviewer().getLastName());
        holder.kudoDate.setText(kudo.getSubmittedDate());
        holder.kudoContent.setText(kudo.getKudo());

        return convertView;
    }

    private static class ViewHolder {
        TextView reviewerName;
        TextView kudoDate;
        TextView kudoContent;
    }
     */
}
