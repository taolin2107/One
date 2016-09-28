package app.taolin.one.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import app.taolin.one.App;
import app.taolin.one.databinding.MoreContentListItemBinding;
import app.taolin.one.models.MoreListModel;

/**
 * @author taolin
 * @version v1.0
 * @date Sep 22, 2016.
 * @description
 */

public class MoreContentListAdapter extends BaseAdapter {

    private List<MoreListModel> mDataList;

    public MoreContentListAdapter(List<MoreListModel> datalist) {
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
        MoreContentListItemBinding layoutBinding;
        if (convertView == null) {
            layoutBinding = MoreContentListItemBinding.inflate(LayoutInflater.from(App.getInstance()), parent, false);
            convertView = layoutBinding.getRoot();
            convertView.setTag(layoutBinding);
        } else {
            layoutBinding = (MoreContentListItemBinding) convertView.getTag();
        }
        layoutBinding.setListModel(mDataList.get(position));
        return convertView;
    }
}
