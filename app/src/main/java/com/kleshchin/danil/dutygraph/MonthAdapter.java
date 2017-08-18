package com.kleshchin.danil.dutygraph;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Danil Kleshchin on 16.08.2017.
 */
class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {

    private int daysInMonthCount_;

    void setDayInMonthCount(int dayInMonthCount) {
        daysInMonthCount_ = dayInMonthCount;
    }

    MonthAdapter(int daysInMonthCount) {
        daysInMonthCount_ = daysInMonthCount;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item_month_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.dutyValue_.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return daysInMonthCount_;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dutyValue_;
        ViewHolder(View itemView) {
            super(itemView);
            dutyValue_ = (TextView) itemView.findViewById(R.id.month_fragment_duty_value);
        }
    }
}
