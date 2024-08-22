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

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private List<Reservation> reservationList;

    public ReservationAdapter(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_reservation_item, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        holder.restaurantName.setText(reservation.getRestaurantName());
        holder.reservationPeople.setText("People: " + reservation.getReservationPeople());
        holder.reservationDate.setText("Date: " + reservation.getReservationDate());
        holder.reservationTime.setText("Time: " + reservation.getReservationTime());

        // Glide를 사용해 Firestore의 이미지 URL을 ImageView에 로드
        Glide.with(holder.itemView.getContext())
                .load(reservation.getRestaurantImage())
                .into(holder.restaurantImage);
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        ImageView restaurantImage;
        TextView restaurantName, reservationPeople, reservationDate, reservationTime;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            restaurantImage = itemView.findViewById(R.id.restaurant_image);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            reservationPeople = itemView.findViewById(R.id.reservation_people);
            reservationDate = itemView.findViewById(R.id.reservation_date);
            reservationTime = itemView.findViewById(R.id.reservation_time);
        }
    }
}
