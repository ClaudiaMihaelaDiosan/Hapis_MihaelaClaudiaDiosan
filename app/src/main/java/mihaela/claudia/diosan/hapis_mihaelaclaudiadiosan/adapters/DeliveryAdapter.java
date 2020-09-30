package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.logic.Delivery;



public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.DeliveryHolder> implements Filterable {

    private DeliveryAdapter.OnItemClickListener mListener;
    private List<Delivery> deliveryData;
    private List<Delivery> deliveryList;

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Delivery> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filteredList.addAll(deliveryList);
            }else{
                for (Delivery delivery: deliveryList){
                    if (delivery.getDonorUsername().toLowerCase().contains(constraint.toString().trim()) ||
                        delivery.getDonatesTo().toLowerCase().contains(constraint.toString().trim()) ||
                        delivery.getDonationLocation().toLowerCase().contains(constraint.toString().trim()) ||
                        delivery.getDonationType().toLowerCase().contains(constraint.toString().trim())) {
                        filteredList.add(delivery);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            deliveryData.clear();
            deliveryData.addAll((Collection<? extends Delivery>) results.values);
            notifyDataSetChanged();
        }
    };

        public interface OnItemClickListener{
        void onContactedClick(int position);
        void onDeliveredClick(int position);
        void onCancelDeliverClick(int position);
    }


    public void setOnItemClickListener(DeliveryAdapter.OnItemClickListener listener){
        mListener = listener;
    }


    public DeliveryAdapter(List<Delivery> deliveryData) {
       this.deliveryData = deliveryData;
       this.deliveryList = new ArrayList<>(deliveryData);
    }


    @NonNull
    @Override
    public DeliveryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_custom_view, parent, false);
        return new DeliveryHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryHolder deliveryHolder, int position) {
        FirebaseFirestore mFirestore;
        mFirestore = FirebaseFirestore.getInstance();

        Delivery delivery = deliveryData.get(position);
        deliveryHolder.donorUsername.setText(delivery.getDonorUsername());
        deliveryHolder.donationType.setText(delivery.getDonationType());
        deliveryHolder.homelessUsername.setText(delivery.getDonatesTo());
        deliveryHolder.location.setText(delivery.getDonationLocation());
        deliveryHolder.date.setText(delivery.getDonationDate());
        deliveryHolder.time.setText(delivery.getDonationHour());
        deliveryHolder.email.setText(delivery.getDonorEmail());
        deliveryHolder.phone.setText(delivery.getDonorPhone());

        DocumentReference documentReference = mFirestore.collection("throughVolunteerDonations").document(delivery.getDonorEmail() + "->" + delivery.getDonatesTo() + ":" + delivery.getDonationType());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Boolean contacted = documentSnapshot.getBoolean("contacted");

                    if (contacted != null && contacted){
                        deliveryHolder.contactedText.setVisibility(View.VISIBLE);
                        deliveryHolder.contactedBtn.setEnabled(false);
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return deliveryData.size();
    }

    class DeliveryHolder extends RecyclerView.ViewHolder{
        MaterialButton contactedBtn;
        MaterialButton deliveredBtn;
        MaterialButton cancelDeliverBtn;
        TextView donorUsername;
        TextView donationType;
        TextView homelessUsername;
        TextView location;
        TextView date;
        TextView time;
        TextView email;
        TextView phone;
        TextView contactedText;



        public DeliveryHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            donorUsername = itemView.findViewById(R.id.delivery_donor_name);
            donationType = itemView.findViewById(R.id.delivery_donation_type);
            homelessUsername = itemView.findViewById(R.id.delivery_donates_to);
            location = itemView.findViewById(R.id.delivery_location);
            date = itemView.findViewById(R.id.delivery_date);
            time = itemView.findViewById(R.id.delivery_time);
            email = itemView.findViewById(R.id.delivery_donor_email);
            phone = itemView.findViewById(R.id.delivery_donor_phone);
            contactedBtn = itemView.findViewById(R.id.contactedBtn);
            deliveredBtn = itemView.findViewById(R.id.deliveredBtn);
            contactedText = itemView.findViewById(R.id.contacted_text);
            cancelDeliverBtn = itemView.findViewById(R.id.cancelDeliverBtn);


          contactedBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (listener != null){
                      int position = getAdapterPosition();
                      if (position != RecyclerView.NO_POSITION) {
                          listener.onContactedClick(position);
                      }
                  }
              }
          });

          deliveredBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (listener != null){
                      int position = getAdapterPosition();
                      if (position != RecyclerView.NO_POSITION) {
                          listener.onDeliveredClick(position);
                      }
                  }
              }
          });

          cancelDeliverBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (listener != null){
                      int position = getAdapterPosition();
                      if (position != RecyclerView.NO_POSITION){
                          listener.onCancelDeliverClick(position);
                      }
                  }
              }
          });
        }
    }
}
