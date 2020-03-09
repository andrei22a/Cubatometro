package upv.dadm.cubatometro.Lib;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import upv.dadm.cubatometro.R;

import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerView extends RecyclerView.Adapter<MyRecyclerView.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ProgressBar vote_progress;

        public ViewHolder(View view, final OnItemClickListener oil, final OnItemLongClickListener oill ){
            super(view);
            name = view.findViewById(R.id.candidate_name_vote);
            vote_progress = view.findViewById(R.id.candidate_progress_vote);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    oil.onItemClickListener(getAdapterPosition());
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    oill.onItemLongClickListener(getAdapterPosition());
                    return true;
                }
            });
        }
    }

}
