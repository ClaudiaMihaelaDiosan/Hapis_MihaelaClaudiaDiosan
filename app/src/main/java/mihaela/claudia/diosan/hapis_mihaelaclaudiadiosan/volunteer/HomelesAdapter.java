package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.homeless.Homeless;

public class HomelesAdapter extends FirestoreRecyclerAdapter<Homeless, HomelesAdapter.HomelessHolder> {



    public HomelesAdapter(@NonNull FirestoreRecyclerOptions<Homeless> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomelessHolder homelessHolder, int i, @NonNull Homeless homeless) {

        homelessHolder.username.setText(homeless.getHomelessUsername());
        homelessHolder.phone.setText(homeless.getHomelessPhoneNumber());
        homelessHolder.birthday.setText(homeless.getHomelessBirthday());
        homelessHolder.lifeHistory.setText(homeless.getHomelessLifeHistory());
        homelessHolder.locationAddress.setText(homeless.getHomelessAddress());
        homelessHolder.schedule.setText(homeless.getHomelessSchedule());
        homelessHolder.need.setText(homeless.getHomelessNeed());


       // Uri.parse(homeless.getImage())
        Glide
                .with(homelessHolder.itemView.getContext())
                .load(homeless.getImage())
                .placeholder(R.drawable.no_profile_image)
                .into(homelessHolder.profileImageView);
    }

    @NonNull
    @Override
    public HomelessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeless_custom_view, parent, false);
        return new HomelessHolder(view);
    }

    class HomelessHolder extends RecyclerView.ViewHolder{

        ImageView profileImageView;
        TextView username;
        TextView phone;
        TextView birthday;
        TextView lifeHistory;
        TextView locationAddress;
        TextView schedule;
        TextView need;

        public HomelessHolder(@NonNull View itemView) {
            super(itemView);

            profileImageView = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.homeless_username_tv);
            phone = itemView.findViewById(R.id.homeless_phone_tv);
            birthday = itemView.findViewById(R.id.homeless_birthday_tv);
            lifeHistory = itemView.findViewById(R.id.homeless_lifeHistory_tv);
            locationAddress = itemView.findViewById(R.id.homeless_locationAddress_tv);
            schedule = itemView.findViewById(R.id.homeless_schedule_tv);
            need = itemView.findViewById(R.id.homeless_need_tv);
        }
    }
}
