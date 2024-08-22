package com.example.mainscreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private final List<Restaurant> restaurantList;

    public CollectionAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collections_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.restaurantName.setText(restaurant.getName());

        // Load the first image from the list using Glide
        List<String> imageResIds = restaurant.getImageResIds();
        if (imageResIds != null && !imageResIds.isEmpty()) {
            // Assuming imageResIds contains image URLs
            String imageUrl = imageResIds.get(0); // Get the first URL
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl) // Load image URL
                    .into(holder.restaurantImage); // Set image into ImageView
        } else {
            // Set a blank image if no URL is available
            holder.restaurantImage.setImageDrawable(null); // Or set a blank drawable
        }
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView restaurantImage;
        public TextView restaurantName;

        public ViewHolder(View itemView) {
            super(itemView);
            restaurantImage = itemView.findViewById(R.id.restaurant_image);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
        }
    }
}
