package io.makeorbreak.hackohollics.onfrugal.presentation.findoffer.searchList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.makeorbreak.hackohollics.onfrugal.R;
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer;
import io.makeorbreak.hackohollics.onfrugal.domain.model.User;
import io.makeorbreak.hackohollics.onfrugal.presentation.OfferActivity;
import io.makeorbreak.hackohollics.onfrugal.presentation.UserActivity;

public class SearchExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<ParentRow> parentRowList;
    private ArrayList<ParentRow> originalList;

    public SearchExpandableListAdapter(Context mContext
            , ArrayList<ParentRow> originalList) {
        this.mContext = mContext;
        this.parentRowList = new ArrayList<>();
        this.parentRowList.addAll(originalList);
        this.originalList = new ArrayList<>();
        this.originalList.addAll(originalList);
    }

    @Override
    public int getGroupCount() {
        return parentRowList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return parentRowList.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentRowList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parentRowList.get(groupPosition).getChildList().get(childPosition);
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

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentRow parentRow = (ParentRow) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.parent_row, null);
        }

        TextView heading = convertView.findViewById(R.id.parent_text);

        heading.setText(parentRow.getName().trim());
        return convertView;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildRow childRow = (ChildRow) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.child_row, null);
        }

        ImageView childIcon = convertView.findViewById(R.id.child_icon);
        childIcon.setImageResource(childRow.getIcon());

        final TextView childText = convertView.findViewById(R.id.child_text);
        childText.setText(childRow.getBasicModel().getName().trim());

        final View finalConvertView = convertView;
        childText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childRow.getBasicModel() instanceof Offer) {
                    Intent intent = new Intent(mContext, OfferActivity.class);
                    intent.putExtra("OFFER", childRow.getBasicModel());
                    mContext.startActivity(intent);
                } else if (childRow.getBasicModel() instanceof User) {
                    Intent intent = new Intent(mContext, UserActivity.class);
                    intent.putExtra("USER",  childRow.getBasicModel());
                    mContext.startActivity(intent);

                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}