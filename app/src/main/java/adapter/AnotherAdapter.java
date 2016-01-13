package adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.timelinemaker.R;

import java.util.ArrayList;

import view.TimeLineView;

/**
 * Created by 洒笑天涯 on 2016/1/10.
 */
public class AnotherAdapter extends RecyclerView.Adapter<AnotherAdapter.ViewHolder> {

    private ArrayList<String> timeLineData;

    private Context mContext;
    //之前这里没有赋值,才导致默认绘制的是圆圈
    private int type= TimeLineView.CENTERTYPE.OTHER;
    //这里之前也是默认为0
    private int mPositiontype=TimeLineView.POSITIONTYPE.CENTER;
    /***
     * @return 距离顶部有多远
     */
    private int marginTop;

    public AnotherAdapter(ArrayList<String> dataList, Context context) {
        this.timeLineData = dataList;
        this.mContext = context;
    }

    /***
     * @return 设置中间圆圈的显示样式
     */
    public void setType(int type) {
        this.type = type;
    }

    public void setPositiontype(int mPositiontype) {
        this.mPositiontype = mPositiontype;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("mine","在createViewHolder这里");
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items, parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setColors(holder, position);
        isLastorFrist(holder, position);
        holder.timeLineView.setPositiontype(mPositiontype);

    }

    /***
     * @return 设置颜色
     */
    private void setColors(ViewHolder holder, int position) {
        holder.tv_name.setText(timeLineData.get(position));
    }

    /***
     * @return 是最后一个还是第一个
     */
    private void isLastorFrist(ViewHolder holder, int position) {
        if (position == 0) {
            holder.timeLineView.isFrist(true).setCenterBmp(R.mipmap.icon_arrow_top).setCenterType(TimeLineView.CENTERTYPE.OTHER);
        }
        if (position == timeLineData.size() - 1) {
            holder.timeLineView.setIsLast(true).setCenterBmp(R.mipmap.icon_arrow_bottom).setCenterType(TimeLineView.CENTERTYPE.OTHER);
        }
        if (position != 0 && position != timeLineData.size() - 1) {
            holder.timeLineView.setIsLast(false).isFrist(false).setCenterType(type);
            if (type == TimeLineView.CENTERTYPE.OTHER) {
                holder.timeLineView.setCenterBmp(R.mipmap.icon_center);
            } else {
                holder.timeLineView.setRadius(8);
            }
        }
    }


    @Override
    public int getItemCount() {
        return timeLineData == null ? 0 : timeLineData.size();
    }

    /***
     * @return 显示土司
     */
    private void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TimeLineView timeLineView;
        private TextView tv_name;
        private ImageView iv_image;

        public ViewHolder(View itemView) {
            super(itemView);
            timeLineView = (TimeLineView) itemView.findViewById(R.id.tlv_view);
            tv_name = (TextView) itemView.findViewById(R.id.tv_names);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);

            timeLineView.setCenterType(TimeLineView.CENTERTYPE.OTHER).setCenterBmp(R.mipmap.icon_center).setLineColor(Color.BLACK).setLineWidth(5);
            timeLineView.setMarginTop(25);
            tv_name.setTextColor(Color.BLACK);
            iv_image.setImageResource(R.mipmap.poison);

        }
    }
}
