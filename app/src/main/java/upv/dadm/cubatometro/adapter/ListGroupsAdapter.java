package upv.dadm.cubatometro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.Listeners.OnItemClickListener;
import upv.dadm.cubatometro.Listeners.OnItemLongClickListener;
import upv.dadm.cubatometro.Listeners.ImageListener;
import upv.dadm.cubatometro.R;
import upv.dadm.cubatometro.entidades.Grupo;

public class ListGroupsAdapter extends RecyclerView.Adapter<ListGroupsAdapter.ViewHolder> {
    private ArrayList<Grupo> data;
    private static OnItemClickListener itemClickListener;
    private static OnItemLongClickListener itemLongClickListener;


    public ListGroupsAdapter(ArrayList<Grupo> data, OnItemClickListener clickListener, OnItemLongClickListener longClickListener){
        this.data = data;
        itemClickListener = clickListener;
        itemLongClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groups_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.groupName.setText(data.get(position).getName());
        if(data.get(position).getImage() == null) new DAO().getGroupPic(data.get(position).getGroupID(), new ImageListener() {
            @Override
            public void onImageReceived(String imageURI) {
                Picasso.get().load(imageURI).noFade().into(holder.groupIcon);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
        else {holder.groupIcon.setImageDrawable(data.get(position).getImage().getDrawable());}
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView groupIcon;
        private TextView groupName;
        private final Context context;

        public ViewHolder(View view){
            super(view);
            context = view.getContext();
            groupIcon = view.findViewById(R.id.groupicon_imageview_groups);
            groupName = view.findViewById(R.id.groupname_textview_groups);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClickListener(getAdapterPosition());
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemLongClickListener.onLongClickListener(getAdapterPosition());
                    return true;
                }
            });
        }
    }


}
