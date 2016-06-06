package com.example.catalyst.ata_test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.models.Team;

import java.util.List;

/**
 * Created by dsloane on 5/27/2016.
 */
public class TeamAdapter extends BaseAdapter {

    //Setting up a tag for logging purposes.
    private final String TAG = TeamAdapter.class.getSimpleName();

    //Context declared for getting access to the layout infalter service.
    private Context mContext;

    //The list holds the data the adapter is using.
    private List<Team> teams;

    //Constructor.
    public TeamAdapter(Context context, List<Team> teamsList) {
        mContext = context;
        teams = teamsList;
    }

    //This method clears the data the adapter is using.
    public void clear() {
        teams.clear();
    }

    //This method returns the size of the list.
    public int getCount() {
        return teams.size();
    }

    //this method returns a team object form the provided location in the list.
    @Override
    public Team getItem(int location) {
        return teams.get(location);
    }

    //Since the objects id is a string, this method is useless.
    //Implementing it make Compiler happy.
    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Declared here per ViewHolder Pattern
        ViewHolder holder;

        //If the view hasn't been instantiated yet.
        if (convertView == null) {

            //Create a new ViewHolder per the ViewHolder Pattern.
            holder = new ViewHolder();

            //Declare the Layout Inflater to inflate the view.
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Inflate the view.
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

    //Android ViewHolder Pattern
    private static class ViewHolder {
        TextView teamName;
        TextView teamDescription;
    }
}
