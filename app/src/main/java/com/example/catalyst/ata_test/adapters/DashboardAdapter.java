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
 * Created by dsloane on 4/27/2016.
 */
public class DashboardAdapter extends BaseAdapter {

    //Setting up a tag for logging purposes.
    private static final String TAG = DashboardAdapter.class.getSimpleName();

    //Context declared for getting access to the layout infalter service.
    private Context mContext;

    //The list holds the data the adapter is using.
    private List<Team> teamList;

    //Constructor. Assigns a value to mContext and the datalist.
    public DashboardAdapter(Context context, List<Team> teamList) {
        mContext = context;
        this.teamList = teamList;
    }

    //This method clears the data the adapter is using.
    public void clear() {
        teamList.clear();
    }

    //This method returns the size of the list.
    @Override
    public int getCount() {
        return teamList.size();
    }

    //Given a position, this method returns the object at that position.
    @Override
    public Team getItem(int location) {
        return teamList.get(location);
    }

    //Since the objects id is a string, this method is useless.
    //Implementing it make Compiler happy.
    @Override
    public long getItemId(int position) {
        return -1;
    }

    //Basic Android View setup.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Declared here per ViewHolder Pattern.
        ViewHolder viewHolder;

        //If the view hasn't been instantiated yet.
        if (convertView == null) {

            //Create a new View Holder per the ViewHolder Pattern.
            viewHolder = new ViewHolder();

            //Declare the layout inflater to inflate the view.
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Create the view.
            convertView = inflater.inflate(R.layout.list_dashboard, null);

            //Find the Textview, and assign it to the view holder.
            viewHolder.teamNameTV = (TextView) convertView.findViewById(R.id.team_name);

            //Assigns the team nameTV to the ViewHolder to be accessed later.
            viewHolder.teamName = teamList.get(position).getName();

            //Assigns the ID to the ViewHolder so it can be accessed later.
            viewHolder.teamId = teamList.get(position).getId();

            //Save the viewHolder
            convertView.setTag(viewHolder);
        } else {
            //If the view already exists, load it from the tag.
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Assigns the value of the team nameTV to the textview associated with the View.
        viewHolder.teamNameTV.setText(viewHolder.teamName);

        //return the view.
        return convertView;
        //TODO: Remove the invisible text view from the XML inside the Dashboard Adapter.
    }


    //Android ViewHolder pattern.
    private static class ViewHolder {
        public String teamId; //the team id can be stored and used later to get team details
        public String teamName; //Stores the team nameTV in the ViewHolder.
        public TextView teamNameTV; //TextView that displays team nameTV.
    }

}
