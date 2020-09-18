package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.logic.Homeless;

public class HomelessAdapter extends RecyclerView.Adapter<HomelessAdapter.HomelessAdapterHolder> implements Filterable {

    private List<Homeless> homelessData;
    private List<Homeless> homelessList;
    private OnItemClickListener mListener;

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Homeless> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filteredList.addAll(homelessList);
            }else{
                for (Homeless homeless : homelessList){
                    if (homeless.getHomelessUsername().toLowerCase().contains(constraint.toString().trim()) ||
                            homeless.getHomelessAddress().toLowerCase().contains(constraint.toString().trim()) ||
                            homeless.getHomelessNeed().toLowerCase().contains(constraint.toString().trim())){
                        filteredList.add(homeless);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            homelessData.clear();
            homelessData.addAll((Collection<? extends Homeless>) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClicklistener(OnItemClickListener listener){
        mListener = listener;
    }

    public HomelessAdapter(List<Homeless> homelessData){
        this.homelessData =homelessData;
        this.homelessList = new ArrayList<>(homelessData);
    }

    @NonNull
    @Override
    public HomelessAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeless_custom_view, parent, false);
        return new HomelessAdapterHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomelessAdapterHolder holder, int position) {
        Homeless homeless = homelessData.get(position);
        holder.username.setText(homeless.getHomelessUsername());
        holder.phone.setText(homeless.getHomelessPhoneNumber());
        holder.birthday.setText(homeless.getHomelessBirthday());
        holder.lifeHistory.setText(homeless.getHomelessLifeHistory());
        holder.locationAddress.setText(homeless.getHomelessAddress());
        holder.schedule.setText(homeless.getHomelessSchedule());
        holder.need.setText(homeless.getHomelessNeed());


        Glide
                .with(holder.itemView.getContext())
                .load(homeless.getImage())
                .placeholder(R.drawable.no_profile_image)
                .into(holder.profileImageView);
    }

    @Override
    public int getItemCount() {
        return homelessData.size();
    }

    static class HomelessAdapterHolder extends RecyclerView.ViewHolder{
        ImageView profileImageView;
        TextView username;
        TextView phone;
        TextView birthday;
        TextView lifeHistory;
        TextView locationAddress;
        TextView schedule;
        TextView need;
        LinearLayout phoneLayout;


        public HomelessAdapterHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            FirebaseFirestore mFirestore;
            FirebaseUser user;
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            mFirestore = FirebaseFirestore.getInstance();
            user = mAuth.getCurrentUser();
            String email = user.getEmail();

            profileImageView = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.homeless_username_tv);
            phone = itemView.findViewById(R.id.homeless_phone_tv);
            birthday = itemView.findViewById(R.id.homeless_birthday_tv);
            lifeHistory = itemView.findViewById(R.id.homeless_lifeHistory_tv);
            locationAddress = itemView.findViewById(R.id.homeless_locationAddress_tv);
            schedule = itemView.findViewById(R.id.homeless_schedule_tv);
            need = itemView.findViewById(R.id.homeless_need_tv);
            phoneLayout = itemView.findViewById(R.id.phone_layout);

            DocumentReference donorsDocument = mFirestore.collection("donors").document(email);
            donorsDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()){
                            phoneLayout.setVisibility(View.GONE);
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
