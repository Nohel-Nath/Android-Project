package model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.login_signup_screendesign_demo.R;
import com.login_signup_screendesign_demo.housedetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class rentadapter extends RecyclerView.Adapter<rentadapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<Rent> rents;
    ArrayList<Rent> rents_all;


    public interface ItemClicked {
        void onItemClicked(int index);
    }
    public rentadapter(Context c , ArrayList<Rent> r)
    {
        context =c;
        rents = r;
        rents_all = new ArrayList<Rent>(rents);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.image,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        FirebaseStorage firebaseStorage =FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference("uploads");
        storageReference.child(rents.get(position).getImageuri()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(holder.image);
            }
        });




        holder.fee.setText(rents.get(position).getFee());
        holder.location.setText(rents.get(position).getLocation());
        holder.addresss.setText(rents.get(position).getAddress());
        //Picasso.get().load(rents.get(position).getImageuri()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, housedetails.class);
                intent.putExtra("address",rents.get(position).getAddress());
                v.getContext().startActivity(intent);
            }
        });

        Log.e("image0","image");
    }

    @Override
    public int getItemCount() {
        return rents.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {


        public ImageView image;
        public TextView fee;
        public TextView location;
        public TextView addresss;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.ivHouse);
            fee = itemView.findViewById(R.id.tvFee);
            location = itemView.findViewById(R.id.tvLocationCardView);
            addresss = itemView.findViewById(R.id.tvAddress);
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Rent> filteredlist = new ArrayList<>();

            if(constraint!=null){
                if(constraint.length()==0){
                    filteredlist.addAll(rents_all);
                }else{
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for(Rent rent: rents_all){
                        if(rent.getLocation().toLowerCase().contains(filterPattern)){
                            filteredlist.add(rent);
                        }
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.count = filteredlist.size();
            results.values=filteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            rents.clear();
            rents.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}












