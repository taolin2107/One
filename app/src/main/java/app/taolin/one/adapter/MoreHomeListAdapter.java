package app.taolin.one.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class MoreHomeListAdapter extends RecyclerView.Adapter<MoreHomeListAdapter.ViewHolder> {

    private List<MoreListModel> mDataList;
    private ItemClickListener mClickListener;

    public MoreHomeListAdapter(List<MoreListModel> datalist) {
        mDataList = datalist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MoreHomeListItemBinding layoutBinding = MoreHomeListItemBinding.inflate(LayoutInflater
                .from(App.getInstance()), parent, false);
        return new ViewHolder(layoutBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bindData(mDataList.get(position));
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(v, position);
                }
            });
        }
    }

    public void addItem(MoreListModel item) {
        mDataList.add(item);
    }

    public MoreListModel getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        mClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        MoreHomeListItemBinding layoutBinding;

        ViewHolder(MoreHomeListItemBinding itemView) {
            super(itemView.getRoot());
            layoutBinding = itemView;
        }

        void bindData(MoreListModel model) {
            layoutBinding.setListModel(model);
        }
    }

    public interface ItemClickListener {
        void onClick(View v, int position);
    }
}
