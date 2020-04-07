package upv.dadm.cubatometro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import upv.dadm.cubatometro.Lib.User;
import upv.dadm.cubatometro.R;

public class CreateGroupAdapter extends RecyclerView.Adapter<CreateGroupAdapter.ViewHolder> {
    private ArrayList<User> data;


    public CreateGroupAdapter(ArrayList<User> data){
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.memberIcon.setImageResource(data.get(position).getImage());
        holder.memberName.setText(data.get(position).getName());
        holder.addMember.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView memberIcon;
        public TextView memberName;
        public Switch addMember;

        public ViewHolder(View v){
            super(v);
            memberIcon = v.findViewById(R.id.membericon_imageview_memberlist);
            memberName = v.findViewById(R.id.membername_textview_memberlist);
            addMember = v.findViewById(R.id.addmember_switch_memberlist);
        }
    }
}
