package demo.app.venus;

import androidx.cardview.widget.CardView;

import demo.app.venus.Models.Items;


public interface ItemsClickListener {
    void onClick(Items notes);
    void onLongClick(Items notes, CardView cardView);
}
