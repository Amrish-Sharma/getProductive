package com.cb.getproductive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.List;

public class ArchivedTaskAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Task> archivedTasks;

    public ArchivedTaskAdapter(Context context, List<Task> archivedTasks) {
        this.context = context;
        this.archivedTasks = archivedTasks;
    }

    @Override
    public int getGroupCount() {
        return 1; // Only one group for archived tasks
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return archivedTasks.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return "Archived Tasks";
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return archivedTasks.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText("Archived Tasks");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);
        Task task = (Task) getChild(groupPosition, childPosition);
        textView.setText(task.getText());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}