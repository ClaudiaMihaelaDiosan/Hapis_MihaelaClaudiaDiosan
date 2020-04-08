package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.homeless.Homeless;

public class HomelessAdapter extends RecyclerView.Adapter<HomelessAdapter.HomelessViewHolder> {
    private ArrayList<Homeless> mHomelessList;
    private final View.OnClickListener editHomelessListener;
    private final View.OnClickListener deleteHomelessListener;



    public static class HomelessViewHolder extends RecyclerView.ViewHolder{
        public ImageView profileImageView;
        public TextView username;
        public TextView phone;
        public TextView birthday;
        public TextView lifeHistory;
        public TextView locationAddress;
        public TextView schedule;
        public TextView need;
        public ImageButton editHomelessBtn;
        public ImageButton deleteHomelessBtn;


        public HomelessViewHolder(@NonNull View itemView, View.OnClickListener editHomelessListener, View.OnClickListener deleteHomelessListener) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.homeless_username_tv);
            phone = itemView.findViewById(R.id.homeless_phone_tv);
            birthday = itemView.findViewById(R.id.homeless_birthday_tv);
            lifeHistory = itemView.findViewById(R.id.homeless_lifeHistory_tv);
            locationAddress = itemView.findViewById(R.id.homeless_locationAddress_tv);
            schedule = itemView.findViewById(R.id.homeless_schedule_tv);
            need = itemView.findViewById(R.id.homeless_need_tv);
            editHomelessBtn = itemView.findViewById(R.id.edit_homeless_button);
            editHomelessBtn.setOnClickListener(editHomelessListener);
            deleteHomelessBtn = itemView.findViewById(R.id.delete_homeless_button);
            deleteHomelessBtn.setOnClickListener(deleteHomelessListener);

        }
    }

    public HomelessAdapter(ArrayList<Homeless> homelessList,View.OnClickListener editHomelessListener, View.OnClickListener deleteHomelessListener){
        this.mHomelessList = homelessList;
        this.editHomelessListener = editHomelessListener;
        this.deleteHomelessListener = deleteHomelessListener;
    }

    @NonNull
    @Override
    public HomelessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeless_custom_view, parent, false);

        return new HomelessViewHolder(view, editHomelessListener, deleteHomelessListener);

    }

    @Override
    public void onBindViewHolder(@NonNull HomelessViewHolder holder, int position) {
        Homeless currentItem = mHomelessList.get(position);

        holder.profileImageView.setImageResource(currentItem.getProfileImageResource());
        holder.username.setText(currentItem.getUsername());
        holder.phone.setText(currentItem.getPhone());
        holder.birthday.setText(currentItem.getBirthday());
        holder.lifeHistory.setText(currentItem.getLifeHistory());
        holder.locationAddress.setText(currentItem.getLocationAddress());
        holder.schedule.setText(currentItem.getSchedule());
        holder.need.setText(currentItem.getNeed());
    }

    @Override
    public int getItemCount() {
        return mHomelessList.size();
    }


}
