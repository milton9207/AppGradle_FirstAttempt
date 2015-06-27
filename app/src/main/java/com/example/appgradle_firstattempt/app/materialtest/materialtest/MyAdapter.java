package com.example.appgradle_firstattempt.app.materialtest.materialtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.appgradle_firstattempt.app.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Milton on 5/5/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<Information> data = Collections.emptyList();
    private Context context;
    private ClickListener clickListener;

    public MyAdapter(Context context, List<Information> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_row, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int i) {

        Information current = data.get(i);
        viewHolder.textView.setText(current.title);
        viewHolder.imageView.setImageResource(current.iconId);

        //One way to add a listener
//        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,"Item click" + i,Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size()  ;
    }

    public void delete(int position) {
        data.remove(position);

        notifyItemRemoved(position);
    }



    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        ImageView imageView;



        public MyViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.listText);
            imageView = (ImageView) itemView.findViewById(R.id.listIcon);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            //delete(getPosition());
//            context.startActivity(new Intent(context,Activity2.class));

//            if(clickListener!=null)
//            {
//                clickListener.ItemClicked(v,getPosition());
//            }

        }
    }

    public interface ClickListener{

        public void ItemClicked(View view, int position);

    }


}
