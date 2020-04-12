package com.vup.arousingtimer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnotherFragment extends Fragment {
    final private String TAG = "AnotherFragment";
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_another, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadAdapterList();

        return v;
    }

    private void loadAdapterList() {
        ArrayList<AnotherListDO> arrayList = new ArrayList<>();
        Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.sadang_hd_icon, null);
        String appName = getString(R.string.text_app_name_01);
        String comment = getString(R.string.text_comment_01);

        arrayList.add(new AnotherListDO(d,appName, comment, "https://play.google.com/store/apps/details?id=com.vup.examineetimer"));

        CustomAdapter adapter = new CustomAdapter(arrayList);
        mRecyclerView.setAdapter(adapter);
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        private ArrayList<AnotherListDO> mList;

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            protected ImageView ivIcon;
            protected TextView tvAppName;
            protected TextView tvComment;

            public CustomViewHolder(View view) {
                super(view);
                this.ivIcon = (ImageView) view.findViewById(R.id.list_item_icon);
                this.tvAppName = (TextView) view.findViewById(R.id.list_item_text);
                this.tvComment = (TextView) view.findViewById(R.id.list_item_secondary_text);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition() ;
                        if (pos != RecyclerView.NO_POSITION) {
                            // 데이터 리스트로부터 아이템 데이터 참조.
                            Log.i(TAG, "CLICK POSITION : " + pos);

                            // TODO : use item.
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(
                                    mList.get(pos).getUrl()));
                            intent.setPackage("com.android.vending");
                            startActivity(intent);
                        }
                    }
                });
            }
        }

        public CustomAdapter(ArrayList<AnotherListDO> list) {
            this.mList = list;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item_another, viewGroup, false);

            CustomViewHolder viewHolder = new CustomViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
            viewholder.ivIcon.setImageDrawable(mList.get(position).getIcon());
            viewholder.tvAppName.setText(mList.get(position).getAppName());
            viewholder.tvComment.setText(mList.get(position).getComment());
        }

        @Override
        public int getItemCount() {
            return (null != mList ? mList.size() : 0);
        }

    }
}
