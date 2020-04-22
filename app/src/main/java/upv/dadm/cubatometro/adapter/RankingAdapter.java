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


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.Listeners.ImageListener;
import upv.dadm.cubatometro.entidades.Ranking;
import upv.dadm.cubatometro.R;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    private ArrayList<Ranking> data;
    private Context context;

    public RankingAdapter(ArrayList<Ranking> data, Context context){
        this.data = data;
        this.context = context;
        setHasStableIds(false);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Ranking m = data.get(position);
        holder.name.setText(m.getName());
        holder.points.setText(m.getPoints() + "");
        if(m.getUserIcon() == null) new DAO().getUserProfilePics(m.getUserID(), new ImageListener() {
            @Override
            public void onImageReceived(String imageURI) {
                if (imageURI == null){
                    Resources resources = context.getResources();
                    imageURI = new Uri.Builder()
                            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                            .authority(resources.getResourcePackageName(R.mipmap.ic_default_picture))
                            .appendPath(resources.getResourceTypeName(R.mipmap.ic_default_picture))
                            .appendPath(resources.getResourceEntryName(R.mipmap.ic_default_picture))
                            .build().toString();
                }
                Picasso.get().load(imageURI).noFade().into(holder.userIcon);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
        else {holder.userIcon.setImageDrawable(data.get(position).getUserIcon().getDrawable());}
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView points;
        public ImageView userIcon;

        public ViewHolder(View v){
            super(v);
            name = v.findViewById(R.id.membername_textview_rankinglist);
            points = v.findViewById(R.id.memberpoints_textview_rankinglist);
            userIcon = v.findViewById(R.id.membericon_imageview_rankinglist);
        }
    }

}
