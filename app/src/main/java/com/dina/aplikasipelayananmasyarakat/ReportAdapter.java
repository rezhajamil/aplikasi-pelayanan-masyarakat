package com.dina.aplikasipelayananmasyarakat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    ArrayList<Report> report;
    ReportActivity reportActivity;

    public ReportAdapter(ReportActivity reportActivity, ArrayList<Report> report) {
        this.reportActivity=reportActivity;
        this.report=report;
    }

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(reportActivity).inflate(R.layout.report_item, parent, false);

        return new ReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, int position) {
        holder.tvEmail.setText(report.get(position).getEmail());
        holder.tvMessage.setText(report.get(position).getMessage());

        Log.v("gagal_report",String.valueOf(report.size()));
    }

    @Override
    public int getItemCount() {
        return (report != null) ? report.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmail,tvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail=itemView.findViewById(R.id.tv_email_report);
            tvMessage=itemView.findViewById(R.id.tv_report_msg);
        }
    }
}
