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

import java.util.List;

/**
 * Created by dsloane on 5/3/2016.
 */
// display information for team member list on team pages
public class TeamMemberAdapter extends BaseAdapter {

    //Setting up a tag for logging purposes.
    private static final String TAG = TeamMemberAdapter.class.getSimpleName();

    //Context declared for getting access to the layout infalter service.
    private Context mContext;

    //The list holds the data the adapter is using.
    private List<User> memberList;

    //Constructor
    public TeamMemberAdapter(Context context, List<User> memberList) {
        mContext = context;
        this.memberList = memberList;
    }

    //This method clears the data the adapter is using.
    public void clear() {
        memberList.clear();
    }

    //This method returns the size of the list
    @Override
    public int getCount() {
        return memberList.size();
    }

    //Given a position, this method returns teh object at that position.
    @Override
    public User getItem(int location) {
        return memberList.get(location);
    }

    //Since the objects id is a string, this method is useless.
    //Implementing it make Compiler happy.
    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Declared here per ViewHolder pattern
        ViewHolder holder;

        //If the view hasn't been instantiated yet.
        if (convertView == null) {

            //Get an instance of the user object.
            User user = memberList.get(position);

            //Create a new view holder per the ViewHolder pattern.
            holder = new ViewHolder();

            //Declare the layout inflater to inflate the view.
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Create the view.
            convertView = inflater.inflate(R.layout.list_team_members, null);

            //Find the TextView and assign it to the view holder.
            holder.nameTV = (TextView) convertView.findViewById(R.id.member_name);

            //Save the users full nameTV to the view holder.
            holder.userName = user.getFirstName() + " " + user.getLastName();

            //Save the view holder.
            convertView.setTag(holder);
        } else {
            //Load the view from the view holder.
            holder = (ViewHolder) convertView.getTag();
        }

        //Set the TextView for the user nameTV to the
        //saved value of the held in the view holder object.
        holder.nameTV.setText(holder.userName);

        //TODO: Remove this debug code when no longer needed, or when app gets released.
        Log.d(TAG, "now adding " + holder.userName);

        return convertView;
    }


    private static class ViewHolder {

        //Holds the value of the uesr nameTV.
        public String userName;

        //Holds a reference to the TextView.
        public TextView nameTV;
    }
}
