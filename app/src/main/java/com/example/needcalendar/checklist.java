package com.example.needcalendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class checklist extends RecyclerView.Adapter<checklist.BoardViewHolder> {


    //데이터 리스트
    private List<list> dataList;

    public checklist(List<list> dataList) {

        this.dataList = dataList;


    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new BoardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {

        list data = dataList.get(position);
        holder.title.setText(data.getTitle());
        holder.place.setText(data.getPlace());
        holder.memo.setText(data.getMemo());


    }

    @Override
    public int getItemCount() {

        // 전체 데이터의 개수 조회
        return dataList.size();
    }


    public class BoardViewHolder extends RecyclerView.ViewHolder {
        //ViewHolder 에 필요한 데이터들
        private TextView title;
        private TextView place;
        private TextView memo;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_board_title);
            place = itemView.findViewById(R.id.item_board_place);
            memo = itemView.findViewById(R.id.item_board_memo);
        }


    }

}