package demo.app.venus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import demo.app.venus.ProductsClickListener;
import demo.app.venus.R;
import demo.app.venus.models.Products;


public class ProductListAdapter extends RecyclerView.Adapter<ProductsViewHolder> {
    Context context;
    List<Products> list;
    ProductsClickListener listener;

    public ProductListAdapter(Context context, List<Products> list, ProductsClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsViewHolder(LayoutInflater.from(context).inflate(R.layout.product_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.textView_productName.setText(list.get(position).getProductName());
        holder.textView_productName.setSelected(true);

        holder.textView_date.setText(list.get(position).getExpDate());
        holder.textView_date.setSelected(true);

        holder.products_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.products_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.products_container);
                return true;

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class ProductsViewHolder extends RecyclerView.ViewHolder{


    CardView products_container;
    TextView textView_productName, textView_date;
    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        products_container = itemView.findViewById(R.id.products_container);
        textView_productName = itemView.findViewById(R.id.textView_productName);
        textView_date = itemView.findViewById(R.id.textView_date);

    }
}
