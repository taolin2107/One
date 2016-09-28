package app.taolin.one.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import app.taolin.one.App;
import app.taolin.one.databinding.MoreHomeListItemBinding;
import app.taolin.one.models.MoreListModel;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 22, 2016.
 * @description
 */

public class MoreHomeListAdapter extends BaseAdapter {

    private List<MoreListModel> mDataList;

    public MoreHomeListAdapter(List<MoreListModel> datalist) {
        mDataList = datalist;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    public void addItem(MoreListModel item) {
        mDataList.add(item);
    }

    @Override
    public MoreListModel getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MoreHomeListItemBinding layoutBinding;
        if (convertView == null) {
            layoutBinding = MoreHomeListItemBinding.inflate(LayoutInflater.from(App.getInstance()), parent, false);
            convertView = layoutBinding.getRoot();
            convertView.setTag(layoutBinding);
        } else {
            layoutBinding = (MoreHomeListItemBinding) convertView.getTag();
        }
        layoutBinding.setListModel(mDataList.get(position));
        return convertView;
    }
}
