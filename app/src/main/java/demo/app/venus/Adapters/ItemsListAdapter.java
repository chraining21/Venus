package demo.app.venus.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import demo.app.venus.ItemsClickListener;
import demo.app.venus.ListActivity;
import demo.app.venus.Models.Items;
import demo.app.venus.R;


public class ItemsListAdapter extends RecyclerView.Adapter<ItemsViewHolder> {

        Context context;
        List<Items> list;
        ItemsClickListener listener;

    public ItemsListAdapter(Context context, List<Items> list, ItemsClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

        @NonNull
        @Override
        public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemsViewHolder(LayoutInflater.from(context).inflate(R.layout.items_list,parent,false));
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
            holder.textView_title.setText(list.get(position).getTitle());
            holder.textView_title.setSelected(true);

            holder.textView_notes.setText(list.get(position).getNotes());

            holder.textView_category.setText(list.get(position).getCategory());
            holder.textView_category.setSelected(true);

            holder.items_container.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.yellow,null));
            holder.items_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(list.get(holder.getAdapterPosition()));
                }
            });

            holder.items_container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongClick(list.get(holder.getAdapterPosition()), holder.items_container);
                    return true;

                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void filterList(List<Items> filteredList){
        list = filteredList;
        notifyDataSetChanged();
        }
    }



   class ItemsViewHolder extends RecyclerView.ViewHolder {

        CardView items_container;
        TextView textView_title,textView_notes,textView_category;
       public ItemsViewHolder(@NonNull View itemView) {
           super(itemView);
           items_container = itemView.findViewById(R.id.items_container);
           textView_title = itemView.findViewById(R.id.textView_title);
           textView_notes = itemView.findViewById(R.id.textView_notes);
           textView_category = itemView.findViewById(R.id.textView_category);

       }

   }

