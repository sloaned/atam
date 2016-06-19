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

            //
            Team team = teams.get(position);

            //Create a new ViewHolder per the ViewHolder Pattern.
            holder = new ViewHolder();

            //Declare the Layout Inflater to inflate the view.
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Inflate the view.
            convertView = inflater.inflate(R.layout.list_teams, null);

            //Find the TextView for team names, and save it to the ViewHolder.
            holder.teamNameTV = (TextView) convertView.findViewById(R.id.team_name);

            //Find the TextView for team Descriptions, and save it to the ViewHolder.
            holder.teamDescriptionTV = (TextView) convertView.findViewById(R.id.team_description);

            //Saves the team nameTV to the ViewHolder
            holder.teamName = team.getName();

            //Saves the team description to ViewHolder
            holder.teamDescription = team.getDescription();

            //Save The ViewHolder as a tag to view.
            convertView.setTag(holder);
        } else {
            //If the view already exists, load its details into the holde object.
            holder = (ViewHolder) convertView.getTag();
        }

        //Loads the teame into the Textview to be displayed to the user.
        holder.teamNameTV.setText(holder.teamName);

        //Loads the team description into the TextView to be displayed to the user.
        holder.teamDescriptionTV.setText(holder.teamDescription);

        //Save the View Holder.
        return convertView;
    }

    //Android ViewHolder Pattern
    private static class ViewHolder {

        //This value is used here to store the team nameTV.
        public String teamName;

        //This value is used here to store the team description.
        public String teamDescription;

        //Holds the TextView for the team nameTV so it can be directly updated.
        public TextView teamNameTV;

        //Holds the TextView for the team description so it can be directly updated.
        public TextView teamDescriptionTV;
    }
}
