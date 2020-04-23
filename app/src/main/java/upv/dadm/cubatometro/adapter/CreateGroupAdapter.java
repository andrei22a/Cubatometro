package upv.dadm.cubatometro.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageView;
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
    private ArrayList<User> selectedMembers;


    public CreateGroupAdapter(ArrayList<User> data){
        this.data = data;
        selectedMembers = new ArrayList<>(data.size());
        setHasStableIds(true);
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
                Picasso.get().load(imageURI).noFade().into(holder.memberIcon);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
        else {holder.memberIcon.setImageDrawable(data.get(position).getProfilePic().getDrawable());}
        holder.memberName.setText(data.get(position).getUsername());

        holder.addMember.setTag(position);

        holder.addMember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = (int) holder.addMember.getTag();
                if (isChecked){
                    data.get(pos).setSelected(true);
                    selectedMembers.add(data.get(pos));
                } else {
                    data.get(pos).setSelected(false);
                    selectedMembers.remove(data.get(pos));
                }
                Log.d("SELECTED MEMBERS", selectedMembers.toString());
            }
        });
    }

    public void updateList(ArrayList<User> list){
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public User getItem(int position){
        return data.get(position);
    }

    public ArrayList<User> getSelectedIds(){
        return selectedMembers;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter(){
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                Log.d("SEARCHVIEW CLEARED", "true");
                results.count = data.size();
                results.values = data;
            } else {
                ArrayList<User> filteredList = new ArrayList<>();
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (User user : data) {
                    if (user.getUsername().toLowerCase().contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            data.addAll((ArrayList<User>) results.values);
            notifyDataSetChanged();
        }
    };


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView memberIcon;
        TextView memberName;
        CheckBox addMember;

        ViewHolder(View v){
            super(v);
            memberIcon = v.findViewById(R.id.membericon_imageview_memberlist);
            memberName = v.findViewById(R.id.membername_textview_memberlist);
            addMember = v.findViewById(R.id.addmember_switch_memberlist);
        }
    }


}
