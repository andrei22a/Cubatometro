package upv.dadm.cubatometro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.entidades.User;
import upv.dadm.cubatometro.R;

public class CreateGroupAdapter extends RecyclerView.Adapter<CreateGroupAdapter.ViewHolder> {
    private ArrayList<User> data;
    private Boolean[] checkArray;


    public CreateGroupAdapter(ArrayList<User> data){
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list, parent, false);
        return new ViewHolder(view, new CustomSwitchListener());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(data.get(position).getProfilePic() == null) new DAO().getUserProfilePics(data.get(position).getUserID(), holder.memberIcon);
        else {holder.memberIcon.setImageDrawable(data.get(position).getProfilePic().getDrawable());}
        holder.memberName.setText(data.get(position).getUsername());
        holder.addMember.setChecked(false);

        holder.addMember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                notifyDataSetChanged();
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



    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView memberIcon;
        public TextView memberName;
        public Switch addMember;
        public CustomSwitchListener listener;

        public ViewHolder(View v, CustomSwitchListener listener){
            super(v);
            memberIcon = v.findViewById(R.id.membericon_imageview_memberlist);
            memberName = v.findViewById(R.id.membername_textview_memberlist);
            addMember = v.findViewById(R.id.addmember_switch_memberlist);

            this.listener = listener;
            addMember.setOnCheckedChangeListener(listener);
        }

        public void updateCheck(int pos, boolean val){
            if(val){
                addMember.setChecked(true);
            } else {
                addMember.setChecked(false);
            }
        }
    }

    public Boolean[] getSelectedIds(){
        return checkArray;
    }

    public interface SwitchListener{
        void updateCheck(int pos, boolean val);
    }

    private class CustomSwitchListener implements CompoundButton.OnCheckedChangeListener{
        private int position;
        SwitchListener listener;

        public void updatePosition(int position, ViewHolder holder){
            this.position = position;
            listener = (SwitchListener) holder;
        }

        public void onCheckedChanged(CompoundButton button, boolean isChecked){
            checkArray[position] = isChecked;
            if(isChecked){
                listener.updateCheck(position, true);
            } else {
                listener.updateCheck(position, false);
            }
        }
    }
}
