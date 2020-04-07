package upv.dadm.cubatometro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import upv.dadm.cubatometro.Lib.Ranking;
import upv.dadm.cubatometro.R;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener{
    private ArrayList<Ranking> data;

    public RankingAdapter(ArrayList<Ranking> data){
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.ViewHolder holder, int position) {
        holder.position.setText(data.get(position).getPosition());
        holder.name.setText(data.get(position).getName());
        holder.points.setText(data.get(position).getPoints() + " puntos");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView position;
        public TextView name;
        public TextView points;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.position_textview_rankinglist);
            name = itemView.findViewById(R.id.membername_textview_rankinglist);
            points = itemView.findViewById(R.id.memberpoints_textview_rankinglist);
        }
    }


}
