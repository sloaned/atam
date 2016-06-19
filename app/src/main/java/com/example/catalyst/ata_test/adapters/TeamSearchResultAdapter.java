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
 * Created by dsloane on 6/1/2016.
 */
public class TeamSearchResultAdapter extends BaseAdapter {

    //Setting up a tag for logging purposes.
    private final String TAG = TeamSearchResultAdapter.class.getSimpleName();

    //Context declared for getting access to the layout infalter service.
    private Context mContext;

    //The list holds the data the adapter is using.
    private List<Team> teams;

    //Constructor.
    public TeamSearchResultAdapter(Context context, List<Team> teamsList) {
        mContext = context;
        teams = teamsList;
    }

    //This method returns the sise of the list.
    public void clear() {
        teams.clear();
    }

    //This method returns the size of the list.
    @Override
    public int getCount() {
        return teams.size();
    }

    //Given a position, this method returns the team at that position.
    @Override
    public Team getItem(int location) {
        return teams.get(location);
    }

    //Since the Team id is a string, this method is useless.
    //It is just being Implemented to make the compiler happy.
    @Override
    public long getItemId(int position) {
        return -1;
    };

    //Basic android View Setup.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Declared here per ViewHolder Pattern.
        ViewHolder holder;

        //If that view hasn't been instantiated yet.
        if (convertView == null) {

            //Store the team object for reference.
            Team team = teams.get(position);

            //Create a new View Holder per the ViewHolder pattern.
            holder = new ViewHolder();

            //Declare the layout inflater to inflate the view.
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Create the view.
            convertView = inflater.inflate(R.layout.list_team_results, null);

            //Find the TextView, and assign it to the holder.
            holder.teamNameTV = (TextView) convertView.findViewById(R.id.team_name);

            //Saves the team nameTV to the view holder.
            holder.teamName = team.getName();


            //Save the view holder.
            convertView.setTag(holder);
        } else {
            ///if view already exisits, load it from view tag.
            holder = (ViewHolder) convertView.getTag();
        }

        //Display the saved team to the user.
        holder.teamNameTV.setText(holder.teamName);

        //return the view.
        return convertView;
    }

    //Android ViewHolder pattern.
    private static class ViewHolder {

        //Holds the value of the team nameTV.
        public String teamName;

        //Holds a reference to the TextView where the team nameTV will be displayed.
        public TextView teamNameTV;
    }
}
