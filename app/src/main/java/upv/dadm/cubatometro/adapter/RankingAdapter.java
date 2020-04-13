package upv.dadm.cubatometro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import upv.dadm.cubatometro.entidades.Ranking;
import upv.dadm.cubatometro.R;

public class RankingAdapter extends BaseAdapter {
    private ArrayList<Ranking> data;
    private Context context;
    private LayoutInflater inflater;

    public RankingAdapter(ArrayList<Ranking> data, Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position){
        return data.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View scoreView, ViewGroup parent){
        ViewHolder holder;

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(scoreView == null){
            scoreView = inflater.inflate(R.layout.ranking_list_item, parent, false);
            holder = new ViewHolder();
            holder.name = scoreView.findViewById(R.id.membername_textview_rankinglist);
            holder.points = scoreView.findViewById(R.id.memberpoints_textview_rankinglist);

            scoreView.setTag(holder);
        } else {
            holder = (ViewHolder) scoreView.getTag();
        }

        final Ranking m = data.get(position);
        holder.name.setText(m.getName());
        holder.points.setText(m.getPoints() + "");

        return scoreView;
    }

    public static class ViewHolder{
        public TextView name;
        public TextView points;

    }

}
