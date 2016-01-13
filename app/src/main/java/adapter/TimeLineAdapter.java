package adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {

    private ArrayList<String> timeLineData;

    private Context mContext;

    private int type;

    private int mPositiontype=TimeLineView.POSITIONTYPE.CENTER;
    /***
     * @return 距离顶部有多远
     */
    private int marginTop;

    public TimeLineAdapter(ArrayList<String> dataList, Context context) {
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
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items, parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setColors(holder, position);
        isLastorFrist(holder, position);
        holder.timeLineView.setPositiontype(mPositiontype);
        holder.timeLineView.setMarginTop(25);
    }

    /***
     * @return 设置颜色
     */
    private void setColors(ViewHolder holder, int position) {
        holder.tv_name.setText(timeLineData.get(position));
        if (position % 3 == 0) {
            holder.timeLineView.setMainColor(Color.RED);
            holder.timeLineView.setLineColor(Color.RED);
        } else {
            holder.timeLineView.setMainColor(Color.BLUE);
            holder.timeLineView.setLineColor(Color.BLUE);
        }
    }

    /***
     * @return 是最后一个还是第一个
     */
    private void isLastorFrist(ViewHolder holder, int position) {
        if (position == 0) {
            holder.timeLineView.isFrist(true).setCenterType(TimeLineView.CENTERTYPE.RINGCYCLE);
        }
        if (position == timeLineData.size() - 1) {
            holder.timeLineView.setIsLast(true).setCenterType(TimeLineView.CENTERTYPE.RINGCYCLE);
        }
        if (position != 0 && position != timeLineData.size() - 1) {
            holder.timeLineView.setIsLast(false).isFrist(false).setCenterType(type);
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
        }
    }
}
