package upv.dadm.cubatometro.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.Listeners.ImageListener;
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
        final ViewHolder holder;

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(scoreView == null){
            scoreView = inflater.inflate(R.layout.ranking_list_item, parent, false);
            holder = new ViewHolder();
            holder.name = scoreView.findViewById(R.id.membername_textview_rankinglist);
            holder.points = scoreView.findViewById(R.id.memberpoints_textview_rankinglist);
            holder.userIcon = scoreView.findViewById(R.id.membericon_imageview_rankinglist);

            scoreView.setTag(holder);
        } else {
            holder = (ViewHolder) scoreView.getTag();
        }

        final Ranking m = data.get(position);
        holder.name.setText(m.getName());
        holder.points.setText(m.getPoints() + "");
        if(data.get(position).getUserIcon() == null) new DAO().getUserProfilePics(data.get(position).getUserID(), new ImageListener() {
            @Override
            public void onImageReceived(String imageURI) {
                /*if (imageURI == null){
                    Resources resources = context.getResources();
                    imageURI = new Uri.Builder()
                            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                            .authority(resources.getResourcePackageName(R.mipmap.ic_default_picture))
                            .appendPath(resources.getResourceTypeName(R.mipmap.ic_default_picture))
                            .appendPath(resources.getResourceEntryName(R.mipmap.ic_default_picture))
                            .build().toString();
                }*/
                Picasso.get().load(imageURI).noFade().into(holder.userIcon);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
        else {holder.userIcon.setImageDrawable(data.get(position).getUserIcon().getDrawable());}


        return scoreView;
    }

    public static class ViewHolder{
        public TextView name;
        public TextView points;
        public ImageView userIcon;

    }

}
