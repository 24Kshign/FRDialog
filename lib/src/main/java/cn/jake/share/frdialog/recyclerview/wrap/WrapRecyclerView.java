package cn.jake.share.frdialog.recyclerview.wrap;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jack
 * Date：2018/9/11 下午2:03
 * Desc：
 */
public class WrapRecyclerView extends RecyclerView {

    //包裹了一层头部和底部的Adapter
    private WrapRecyclerAdapter mWrapRecyclerAdapter;

    //列表数据的Adapter
    private RecyclerView.Adapter mAdapter;

    private View mEmptyView;

    public WrapRecyclerView(Context context) {
        this(context, null);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        // 为了防止多次设置Adapter
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mAdapter = null;
        }

        this.mAdapter = adapter;

        mWrapRecyclerAdapter = (WrapRecyclerAdapter) adapter;

        super.setAdapter(mWrapRecyclerAdapter);

        // 注册一个观察者 不然更新没有效果
        mAdapter.registerAdapterDataObserver(mDataObserver);

        // 解决GridLayout添加头部和底部也要占据一行
        mWrapRecyclerAdapter.adjustSpanSize(this);
    }

    // 添加头部
    public void addHeaderView(View view) {
        // 如果没有Adapter那么就不添加，也可以选择抛异常提示
        // 让他必须先设置Adapter然后才能添加，这里是仿照ListView的处理方式
        if (null != mWrapRecyclerAdapter) {
            mWrapRecyclerAdapter.addHeaderView(view);
        }
    }

    // 添加底部
    public void addFooterView(View view) {
        if (null != mWrapRecyclerAdapter) {
            mWrapRecyclerAdapter.addFooterView(view);
        }
    }

    // 移除头部
    public void removeHeaderView(View view) {
        if (null != mWrapRecyclerAdapter) {
            mWrapRecyclerAdapter.removeHeaderView(view);
        }
    }

    // 移除底部
    public void removeFooterView(View view) {
        if (null != mWrapRecyclerAdapter) {
            mWrapRecyclerAdapter.removeFooterView(view);
        }
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (null == mAdapter) {
                return;
            }
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyDataSetChanged();
            }
            dataChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (null == mAdapter) {
                return;
            }
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyItemRemoved(positionStart);
            }
            dataChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (null == mAdapter) {
                return;
            }
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyItemMoved(fromPosition, toPosition);
            }
            dataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (null == mAdapter) {
                return;
            }
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyItemChanged(positionStart);
            }
            dataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (null == mAdapter) {
                return;
            }
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyItemChanged(positionStart, payload);
            }
            dataChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (null == mAdapter) {
                return;
            }
            if (mWrapRecyclerAdapter != mAdapter) {
                mWrapRecyclerAdapter.notifyItemInserted(positionStart);
            }
            dataChanged();
        }
    };

    private void dataChanged() {
        if (mAdapter.getItemCount() == 0) {
            //没有数据
            if (null != mEmptyView) {
                this.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.VISIBLE);
            }
        } else {
            if (null != mEmptyView) {
                mEmptyView.setVisibility(View.GONE);
            }
            this.setVisibility(View.VISIBLE);
        }
    }
}