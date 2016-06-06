package com.example.catalyst.ata_test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.models.User;

import java.util.List;

/**
 * Created by dsloane on 4/27/2016.
 */
public class UserSearchResultAdapter extends BaseAdapter {

    //Setting up a tag for logging purposes.
    private static final String TAG = DashboardAdapter.class.getSimpleName();

    //Context declared for getting access to the layout infalter service.
    private Context mContext;

    //The list holds the data the adapter is using.
    private List<User> resultList;

    //Constructor
    public UserSearchResultAdapter(Context context, List<User> resultList) {
        mContext = context;
        this.resultList = resultList;
    }

    //This method clears the data the adapter is using.
    public void clear() {
        resultList.clear();
    }

    //This method returns the size of the list.
    @Override
    public int getCount() {
        return resultList.size();
    }

    //Given a position, this method returns the User object at that position.
    @Override
    public User getItem(int location) {
        return resultList.get(location);
    }

    //Since the objects id is a string, this method is useless.
    //Implementing it make the Compiler happy.
    @Override
    public long getItemId(int position) {
        return -1;
    }

    //Basic Android View setup.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Declared here per the ViewHolder pattern.
        ViewHolder viewHolder;

        //If the view hasn't been instantiated yet.
        if (convertView == null) {

            //Instantiate a user object at the given position.
            User u = resultList.get(position);

            //Declare the layout inflater to inflate the view.
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Create a new ViewHolder per the ViewHolder Pattern.
            viewHolder = new ViewHolder();

            //Create the view.
            convertView = inflater.inflate(R.layout.list_search_results, null);

            //Find the TextView and assign it to the view holder.
            viewHolder.nameTV = (TextView) convertView.findViewById(R.id.user_name);

            //Find the TextView and assign it to the view holder.
            viewHolder.titleTV = (TextView) convertView.findViewById(R.id.user_title);

            //Save the value of the user name in the View holder.
            viewHolder.userName = u.getFirstName() + " " + u.getLastName();

            //Save the value of the user's title in the View holder
            viewHolder.userTitle = u.getTitle();

            //Save the view holder
            convertView.setTag(viewHolder);
        } else {
            //If the view already exist, load it from the tag.
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Display the saved value of the user's name to the app user.
        viewHolder.nameTV.setText(viewHolder.userName);

        //Display the saved value of the user's title to the app user.
        viewHolder.titleTV.setText(viewHolder.userTitle);

        //Return the view.
        return convertView;
    }

    //Android ViewHolder pattern.
    private static class ViewHolder {

        //Holds the value of the user name.
        public String userName;

        //Holds the value of the users title.
        public String userTitle;

        //Holds a reference to the TextView for displaying the user's name.
        public TextView nameTV;

        //Holds a reference to the TextView for displaying the user's title.
        public TextView titleTV;
    }
}
