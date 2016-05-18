package com.example.catalyst.ata_test.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.data.TeamMemberContract;
import com.example.catalyst.ata_test.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsloane on 5/3/2016.
 */
// display information for team member list on team pages
public class TeamMemberAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater inflater;
    private List<User> memberList = new ArrayList<User>();

    private static final String TAG = TeamMemberAdapter.class.getSimpleName();

    public TeamMemberAdapter(Context context, List<User> memberList) {
        mContext = context;
        this.memberList = memberList;
    }

    public void clear() {
        memberList.clear();
    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public Object getItem(int location) {
        return memberList.get(location);
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
            convertView = inflater.inflate(R.layout.list_team_members, null);
            holder.name = (TextView) convertView.findViewById(R.id.member_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User user = memberList.get(position);
        holder.name.setText(user.getFirstName() + " " + user.getLastName());
        Log.d(TAG, "now adding " + user.getFirstName() + " " + user.getLastName());

        return convertView;
    }



    private static class ViewHolder {
        TextView name;
    }
}
