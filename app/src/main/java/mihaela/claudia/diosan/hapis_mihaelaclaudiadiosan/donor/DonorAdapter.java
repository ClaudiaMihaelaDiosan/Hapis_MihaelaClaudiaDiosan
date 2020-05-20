package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.donor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.homeless.Homeless;

public class DonorAdapter extends FirestoreRecyclerAdapter<Homeless, DonorAdapter.DonorAdaperHolder>{

    private DonorAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot  documentSnapshot, int position);
    }

    public void setOnItemClickListener(DonorAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public DonorAdapter(@NonNull FirestoreRecyclerOptions<Homeless> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull DonorAdaperHolder donorHolder, int i, @NonNull Homeless homeless) {

        donorHolder.username.setText(homeless.getHomelessUsername());
        donorHolder.locationAddress.setText(homeless.getHomelessAddress());
        donorHolder.need.setText(homeless.getHomelessNeed());


        // Uri.parse(homeless.getImage())
        Glide
                .with(donorHolder.itemView.getContext())
                .load(homeless.getImage())
                .placeholder(R.drawable.no_profile_image)
                .into(donorHolder.profileImageView);
    }

    @NonNull
    @Override
    public DonorAdaperHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeless_card_custom_view, parent, false);
        return new DonorAdaperHolder(view, mListener);
    }


    class DonorAdaperHolder extends RecyclerView.ViewHolder{

        ImageView profileImageView;
        TextView username;
        TextView locationAddress;
        TextView need;

        public DonorAdaperHolder(@NonNull View itemView, final OnItemClickListener listener) {
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
                            listener.onItemClick(getSnapshots().getSnapshot(position), position);
                        }
                    }
                }
            });
        }
    }

}
