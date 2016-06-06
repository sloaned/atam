package com.example.catalyst.ata_test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.models.Kudo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsloane on 5/10/2016.
 */
public class KudosAdapter extends BaseAdapter {

    //Setting up a tag for logging purposes.
    private final String TAG = KudosAdapter.class.getSimpleName();

    //Context declared for getting the layout infalter service.
    private Context mContext;

    //This list holds the data the adapter is using.
    private List<Kudo> kudos = new ArrayList<Kudo>();

    //Constructor.
    public KudosAdapter(Context context, List<Kudo> kudosList) {
        mContext = context;
        kudos = kudosList;
    }

    //This method clears the data the adapter is using.
    public void clear() {
        kudos.clear();
    }

    //This method returns the size of the array.
    public int getCount() {
        return kudos.size();
    }

    //returns a kudo at specified position.
    public Kudo getItem(int location) {
        return kudos.get(location);
    }

    //Since the objects id is a string, this method is useless.
    //Implementing it make Compiler happy.
    @Override
    public long getItemId(int position) {
        return -1;
    }

    //Basic Android Adapter view setup.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //viewHolder declared here per the ViewHolder pattern.
        ViewHolder viewHolder;

        if (convertView == null) {

            //Load the kudo in from the adapter.
            Kudo kudo = kudos.get(position);

            //Create a new View Holder per the ViewHolder pattern.
            viewHolder = new ViewHolder();

            //Get the layout service to inflate the view.
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Inflate the view.
            convertView = inflater.inflate(R.layout.list_kudos, null);

            //Assigns the Textview for the reviewers nameTV to the view holder.
            viewHolder.reviewerNameTV = (TextView) convertView.findViewById(R.id.reviewer_name);

            //Assigns the Textview for the date the kudo was posted to the view holder.
            viewHolder.kudoDateTV = (TextView) convertView.findViewById(R.id.kudo_date);

            //Assigns the Textview that displays the kudo text to the view holder.
            viewHolder.kudoContentTV = (TextView) convertView.findViewById(R.id.kudo_content);

            //Stores the full nameTV of the reviewer in the ViewHolder.
            viewHolder.reviewerName = (kudo.getReviewer().getFirstName() + " " + kudo.getReviewer().getLastName());

            //Stores the value of the submitted date in the ViewHolder.
            viewHolder.submittedDate = kudo.getSubmittedDate();

            //Stores the content of the Kudo in the ViewHolder object.
            viewHolder.kudoContent = kudo.getKudo();

            //Set the holder class as tag on the view, so that it can be loaded.
            convertView.setTag(viewHolder);
        } else {
            //If the view was already created at one point, assign it to the view holder.
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Display the saved Reviewers nameTV in the TextView.
        viewHolder.reviewerNameTV.setText(viewHolder.reviewerName);

        //Display the saved Kudos submitted date in the TextView.
        viewHolder.kudoDateTV.setText(viewHolder.submittedDate);

        //Display the saved content of the kudo in the TextView.
        viewHolder.kudoContentTV.setText(viewHolder.kudoContent);

        //Return the converted view.
        return convertView;
    }

    //Android View Holder pattern
    private static class ViewHolder {

        //Holds the value of the ReviewerName.
        public String reviewerName;

        //Holds the value of the Kudos submitted date.
        public String submittedDate;

        //Holds the content of the Kudo.
        public String kudoContent;

        //TextView that displays the reviewers nameTV.
        TextView reviewerNameTV;

        //TextView that displays the date the kudo was posted.
        TextView kudoDateTV;

        //TextView that displays the text of the text of the kudo.
        TextView kudoContentTV;
    }
}
