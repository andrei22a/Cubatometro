package upv.dadm.cubatometro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.Listeners.ImageListener;
import upv.dadm.cubatometro.entidades.User;
import upv.dadm.cubatometro.R;

public class CreateGroupAdapter extends RecyclerView.Adapter<CreateGroupAdapter.ViewHolder> {
    private ArrayList<User> data;
    private ArrayList<Integer> checkArray;


    public CreateGroupAdapter(ArrayList<User> data){
        this.data = data;
        checkArray = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if(data.get(position).getProfilePic() == null) new DAO().getUserProfilePics(data.get(position).getUserID(), new ImageListener() {
            @Override
            public void onImageReceived(String imageURI) {
                Picasso.get().load(imageURI).into(holder.memberIcon);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
        else {holder.memberIcon.setImageDrawable(data.get(position).getProfilePic().getDrawable());}
        holder.memberName.setText(data.get(position).getUsername());
        holder.addMember.setChecked(false);

        holder.addMember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkArray.add(position);
                } else {
                    checkArray.remove(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public User getItem(int position){
        return data.get(position);
    }

    public ArrayList<Integer> getSelectedIds(){
        return checkArray;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView memberIcon;
        public TextView memberName;
        public CheckBox addMember;

        public ViewHolder(View v){
            super(v);
            memberIcon = v.findViewById(R.id.membericon_imageview_memberlist);
            memberName = v.findViewById(R.id.membername_textview_memberlist);
            addMember = v.findViewById(R.id.addmember_switch_memberlist);
        }
    }


}
