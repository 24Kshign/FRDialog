package cn.jake.share.frdialog.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.jake.share.frdialog.recyclerview.interfaces.MultiTypeAdapter;
import cn.jake.share.frdialog.recyclerview.interfaces.RecyclerOnItemClickListener;
import cn.jake.share.frdialog.recyclerview.interfaces.RecyclerOnItemLongClickListener;
import cn.jake.share.frdialog.recyclerview.wrap.WrapRecyclerAdapter;

/**
 * Created by Jack
 * Date：2018/9/11 下午1:49
 * Desc：
 */
public abstract class FRBaseDialogAdapter<DATA> extends RecyclerView.Adapter<FRBaseDialogViewHolder>{
    private List<DATA> mDataList;
    protected Context mContext;
    private MultiTypeAdapter<DATA> mMultiTypeAdapter;
    private RecyclerOnItemClickListener onItemClickListener;
    private RecyclerOnItemLongClickListener onItemLongClickListener;

    public FRBaseDialogAdapter(Context context) {
        mContext = context;
    }

    public FRBaseDialogAdapter(Context context, MultiTypeAdapter<DATA> multiTypeAdapter) {
        this(context);
        mMultiTypeAdapter = multiTypeAdapter;
    }

    @NonNull
    @Override
    public FRBaseDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int mLayoutId;
        if (null != mMultiTypeAdapter) {
            //需要多布局
            mLayoutId = viewType;
        } else {
            mLayoutId = getLayoutRes();
        }
        if (mLayoutId == 0) {
            throw new IllegalArgumentException("The layoutRes of adapter should not be null");
        }
        View itemView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        return new FRBaseDialogViewHolder(itemView);
    }

    protected int getLayoutRes() {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull FRBaseDialogViewHolder holder, int position) {
        convert(holder, mDataList.get(position), position, null);
        setOnClickListener(holder);
    }


    @Override
    public void onBindViewHolder(@NonNull FRBaseDialogViewHolder holder, int position, @NonNull List<Object> payloads) {
        convert(holder, mDataList.get(position), position, payloads);
        setOnClickListener(holder);
    }

    private void setOnClickListener(final FRBaseDialogViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener) {
                    if (getItemViewType(holder.getLayoutPosition()) >= WrapRecyclerAdapter.BASE_ITEM_TYPE_HEADER - 1) {
                        return;
                    }
                    onItemClickListener.click(holder.getLayoutPosition(), view);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (null != onItemLongClickListener) {
                    return onItemLongClickListener.click(holder.getLayoutPosition(), view);
                }
                return false;
            }
        });
    }

    /**
     * 让子类去实现数据绑定
     */
    protected abstract void convert(FRBaseDialogViewHolder holder, DATA data, int position, List<Object> payloads);

    @Override
    public int getItemViewType(int position) {
        if (null != mMultiTypeAdapter) {
            return mMultiTypeAdapter.getLayoutRes(mDataList.get(position));
        }
        return super.getItemViewType(position);
    }

    /**
     * 设置数据源
     *
     * @param dataList 数据
     */
    public void setDataList(List<DATA> dataList) {
        if (null == mDataList) {
            mDataList = new ArrayList<>();
        }
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    /**
     * 移除列表所有的数据
     */
    public void removeAllDataList() {
        if (mDataList != null) {
            mDataList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 在列表某个位置插入数据
     *
     * @param position 需要在哪个位置上插入
     * @param data     插入的数据
     */
    public void insertItem(int position, DATA data) {
        mDataList.add(position, data);
        notifyItemInserted(position);  //插入更新操作
    }

    /**
     * 移除某个位置的数据
     *
     * @param position
     */
    public void removeItem(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);  //删除操作更新
    }

    /**
     * 获取整个列表数据
     *
     * @return
     */
    public List<DATA> getDataList() {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        return mDataList;
    }

    /**
     * 更新局部数据
     *
     * @param position item的位置
     * @param data     item的数据
     */
    public void updateItem(int position, DATA data) {
        mDataList.set(position, data);
        notifyItemChanged(position, "payload");
    }

    /**
     * 获取一条数据
     *
     * @param position item的位置
     * @return item对应的数据
     */
    public DATA getItem(int position) {
        return mDataList.get(position);
    }

    /**
     * 追加一条数据
     *
     * @param data 追加的数据
     */
    public void appendItem(DATA data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 追加一个集合数据
     *
     * @param list 要追加的数据集合
     */
    public void appendList(List<DATA> list) {
        mDataList.addAll(list);
        notifyItemRangeInserted(mDataList.size() - list.size() + 1, list.size());
    }

    /**
     * 在最顶部前置数据
     *
     * @param data 要前置的数据
     */
    public void frontItem(DATA data) {
        mDataList.add(0, data);
        notifyDataSetChanged();
    }

    /**
     * 在顶部前置数据集合
     *
     * @param list 要前置的数据集合
     */
    public void frontList(List<DATA> list) {
        mDataList.addAll(0, list);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(RecyclerOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(RecyclerOnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public int getItemCount() {
        return null != mDataList ? mDataList.size() : 0;
    }
}