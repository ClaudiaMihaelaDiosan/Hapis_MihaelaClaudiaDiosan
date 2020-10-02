package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.R;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.logic.Cities;
import mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.volunteer.EditHomelessFragment;

import static android.content.Context.MODE_PRIVATE;

public class CitiesCardsAdapter extends RecyclerView.Adapter<CitiesCardsAdapter.CardsAdapterHolder> implements Filterable {
    private List<Cities> citiesData;
    private List<Cities> citiesList;
    private OnItemClickListener mListener;
    Context context;


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Cities> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filteredList.addAll(citiesList);
            }else{
                for (Cities city : citiesList){
                    if (city.getCity().toLowerCase().contains(constraint.toString().trim()) ||
                            city.getCountry().toLowerCase().contains(constraint.toString().trim())){
                        filteredList.add(city);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            citiesData.clear();
            citiesData.addAll((Collection<? extends Cities>) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

   public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
   }


    public CitiesCardsAdapter(List<Cities> citiesData, Context context) {
        this.citiesData = citiesData;
        this.citiesList = new ArrayList<>(citiesData);
        this.context = context;
    }

    @NonNull
    @Override
    public CardsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cities_card_design, parent, false);
        return new CardsAdapterHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsAdapterHolder holder, int position) {
        Cities cities = citiesData.get(position);
        holder.city.setText(cities.getCity());
        holder.country.setText(cities.getCountry());

        holder.itemView.setLongClickable(true);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences preferences = context.getSharedPreferences("cityInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("city", cities.getCity()).apply();
                return false;
            }
        });

        Glide
                .with(holder.itemView.getContext())
                .load(cities.getImage())
                .placeholder(R.drawable.no_profile_image)
                .into(holder.city_image);


          }

    @Override
    public int getItemCount() {
        return citiesData.size();
    }

    static class  CardsAdapterHolder extends RecyclerView.ViewHolder{
        TextView city;
        TextView country;
        ImageView city_image;



        public CardsAdapterHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            city = itemView.findViewById(R.id.city_name);
            country = itemView.findViewById(R.id.country_name);
            city_image = itemView.findViewById(R.id.city_image);



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



}
