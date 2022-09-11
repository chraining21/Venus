package demo.app.venus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter extends ArrayAdapter {
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }
    public Adapter(@NonNull Context context, @NonNull List<String> mData) {
        super(context, 0, mData);
    }
    private View createView(int position, View convertView
            , ViewGroup parent){
        convertView = LayoutInflater.from(getContext()).inflate(//綁定介面
                R.layout.spinner, parent, false);
        TextView tvName = convertView.findViewById(R.id.textViewTitle);//控制介面元件
        String item = (String) getItem(position);//取得每一筆的資料內容
        if (item != null) {
            tvName.setText(item);
        }
        return convertView;
    }//複寫介面
}
