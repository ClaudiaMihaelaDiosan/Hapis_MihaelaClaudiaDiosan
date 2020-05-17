package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.homeless.Homeless;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.AdapterHomeViewHolder>  {
    private ArrayList<Homeless> mHomelessList;
    private OnItemClickListener mListener;

     public interface OnItemClickListener{
            void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class AdapterHomeViewHolder extends RecyclerView.ViewHolder{
        public ImageView profileImageView;
        public TextView username;
        public TextView need;
        public TextView locationAddress;




        public AdapterHomeViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profile_image_donor_card);
            username = itemView.findViewById(R.id.homeless_username_tv_donor_card);
            need = itemView.findViewById(R.id.homeless_need_tv_donor_card);
            locationAddress = itemView.findViewById(R.id.homeless_locationAddress_tv_donor_card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                         }
                    }
                }
            });
        }

    }

    public AdapterHome(ArrayList<Homeless> homelessList){
        this.mHomelessList = homelessList;

    }

    @NonNull
    @Override
    public AdapterHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeless_card_custom_view, parent, false);
        return new AdapterHomeViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHomeViewHolder holder, int position) {
        Homeless currentItem = mHomelessList.get(position);

       // holder.profileImageView.setImageResource(currentItem.getImage());
        holder.username.setText(currentItem.getHomelessUsername());
        holder.need.setText(currentItem.getHomelessNeed());
        holder.locationAddress.setText(currentItem.getHomelessAddress());



    }

    @Override
    public int getItemCount() {
        return mHomelessList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }



}
