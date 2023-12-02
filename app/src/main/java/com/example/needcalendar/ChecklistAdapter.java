package com.example.needcalendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {

    private List<ListCheck> checklistItems;
    private Context mContext;

    public ChecklistAdapter(List<ListCheck> items, Context context) {

        this.checklistItems = items;
        this.mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListCheck currentItem = checklistItems.get(position);
        holder.titleTextView.setText(currentItem.getTitle());
        holder.placeTextView.setText(currentItem.getPlace());
        holder.memoTextView.setText(currentItem.getMemo());

    }

    @Override
    public int getItemCount() {

        return checklistItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        public CheckBox checkbox1;
        public TextView titleTextView;
        public TextView placeTextView;
        public TextView memoTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            checkbox1 = itemView.findViewById(R.id.checkbox1);
            titleTextView = itemView.findViewById(R.id.item_board_title);
            placeTextView = itemView.findViewById(R.id.item_board_place);
            memoTextView = itemView.findViewById(R.id.item_board_memo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int curPos = getAdapterPosition();// 현재 리스트 아이템 위치
                    if (curPos != RecyclerView.NO_POSITION) {
                        ListCheck todoItem = checklistItems.get(curPos);
                        Context context = view.getContext();

                        String[] strChoiceItems = {"수정하기", "삭제하기"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("작업을 선택하세요");
                        builder.setItems(strChoiceItems, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {
                                if(position == 0){


                                }
                                else if(position == 1 ){
                                    //delete UI
                                    deleteData(todoItem, curPos);

                                }
                            }
                            private void deleteData(ListCheck todoItem, int curPos) {
                                if (mContext != null) {
                                    DBcheck dbHelper = new DBcheck(mContext);
                                    long deletedItemId = dbHelper.deleteData(todoItem.getId());
                                    dbHelper.close();

                                    if (deletedItemId != -1) {
                                        checklistItems.remove(curPos);
                                        notifyItemRemoved(curPos);
                                        notifyItemRangeChanged(curPos, checklistItems.size());
                                        Toast.makeText(mContext, "목록이 제거되었습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mContext, "목록 제거에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        builder.create().show();

                    }
                }

            });
        }





    }

}
