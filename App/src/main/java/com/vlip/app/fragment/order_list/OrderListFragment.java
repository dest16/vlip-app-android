package com.vlip.app.fragment.order_list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vlip.app.Constants;
import com.vlip.app.R;
import com.vlip.app.bean.Event;
import com.vlip.app.bean.Order2;
import com.vlip.app.fragment.order_detail.OrderDetailFragment;
import com.vlip.app.kit.AppUtils;
import com.vlip.kit.DPUtils;
import com.vlip.ui.activity.ToolbarFragmentActivity;
import com.vlip.ui.adapter.recyclerview.BaseRecyclerAdapter;
import com.vlip.ui.adapter.recyclerview.LinearDividerItemDecoration;
import com.vlip.ui.adapter.recyclerview.ViewHolder;
import com.vlip.ui.dialog.CommonDialog;
import com.vlip.ui.fragment.LazyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

public class OrderListFragment extends LazyFragment<OrderListPresenter> implements OrderListView {

    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    Integer mPage = 1;
    BaseRecyclerAdapter<Order2> mAdapter;
    CommonDialog dialog;
    int status;

    public static OrderListFragment newInstance(int status) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.INTENT_KEY1, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewId() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        int space = DPUtils.dp2px(getResources(), 10);
        mRecyclerView.addItemDecoration(new LinearDividerItemDecoration(LinearDividerItemDecoration.VERTICAL, space, AppUtils.getColor(R.color.colorBg)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dialog = new CommonDialog(getContext());
        dialog.setTitle("删除订单");
        dialog.setContent("确定要删除该订单吗？");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initData() {
        assert getArguments() != null;
        status = getArguments().getInt(Constants.INTENT_KEY1);

        mAdapter = new BaseRecyclerAdapter<Order2>(R.layout.item_order_process) {
            @Override
            protected void convert(ViewHolder viewHolder, Order2 item, int position) {
                TextView sn = viewHolder.findViewById(R.id.sn);
                TextView type = viewHolder.findViewById(R.id.type);
                TextView time = viewHolder.findViewById(R.id.time);
                TextView from = viewHolder.findViewById(R.id.from);
                TextView to = viewHolder.findViewById(R.id.to);
                sn.setText(String.format("订单编号：%s", item.id));
//                sn.setText(String.format("订单编号：%s", item.orderNumber));
                type.setText(item.type);
//                time.setText(item.acceptTime.toLocaleString());
                from.setText(item.fromSite);
                to.setText(item.toSite);
                String timeStr = "";
                switch (status) {
                    case 0://待接单
                        if (item.startTime != null)
                            timeStr = String.format("发布时间：%s", item.startTime.toLocaleString());
                        break;
                    case 1://进行中
                        if (item.acceptTime != null)
                            timeStr = String.format("接单时间：%s", item.acceptTime.toLocaleString());
                        break;
                    case 3:
                        if (item.finishTime != null)
                            timeStr = String.format("完成时间：%s", item.finishTime.toLocaleString());
                        break; //已完成
                    case 4: //已取消
                        if (item.cancelTime != null)
                            timeStr = String.format("取消时间：%s", item.cancelTime.toLocaleString());
                        break;
                }
                time.setText(timeStr);
            }

            @Override
            protected void onItemClick(Order2 item, int position) {
                Bundle b = new Bundle();
                b.putSerializable(Constants.INTENT_KEY1, item);
                ToolbarFragmentActivity.createFragment(requireContext(), OrderDetailFragment.class, b);
            }

            @Override
            protected void onItemLongClick(Order2 item, int position) {
                dialog.setOnClickSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getPresenter().cancelOrder(item.id);
                    }
                });
                dialog.show();
            }
        };
        mRecyclerView.setAdapter(mAdapter);
//        switch (status) {
//            case 0: //待接单
//                mAdapter = new BaseRecyclerAdapter<Order2>(R.layout.item_order_pending) {
//                    @Override
//                    protected void convert(ViewHolder viewHolder, Order2 item, int position) {
//                        TextView sn = viewHolder.findViewById(R.id.sn);
//                        TextView type = viewHolder.findViewById(R.id.type);
//                        TextView time = viewHolder.findViewById(R.id.time);
//                        TextView from = viewHolder.findViewById(R.id.from);
//                        TextView to = viewHolder.findViewById(R.id.to);
//                        TextView option = viewHolder.findViewById(R.id.option);
//                        sn.setText(String.format("订单编号：%s", item.id));
//                        type.setText(item.type);
//                        time.setText(item.acceptTime.toLocaleString());
//                        from.setText(item.fromSite);
//                        to.setText(item.toSite);
//
//                    }
//                };
//                mRecyclerView.setAdapter(mAdapter);
//                break;
//            case 1: //进行中
//                mAdapter = new BaseRecyclerAdapter<Order2>(R.layout.item_order_process) {
//                    @Override
//                    protected void convert(ViewHolder viewHolder, Order2 item, int position) {
//                        TextView sn = viewHolder.findViewById(R.id.sn);
//                        TextView type = viewHolder.findViewById(R.id.type);
//                        TextView time = viewHolder.findViewById(R.id.time);
//                        TextView from = viewHolder.findViewById(R.id.from);
//                        TextView to = viewHolder.findViewById(R.id.to);
//                        sn.setText(String.format("订单编号：%s", item.id));
//                        type.setText(item.type);
//                        time.setText(item.acceptTime.toLocaleString());
//                        from.setText(item.fromSite);
//                        to.setText(item.toSite);
//
//                    }
//                };
//                mRecyclerView.setAdapter(mAdapter);
//                break;
//            case 3: //已完成
//                mAdapter = new BaseRecyclerAdapter<Order2>(R.layout.item_order_finished) {
//                    @Override
//                    protected void convert(ViewHolder viewHolder, Order2 item, int position) {
//                        TextView sn = viewHolder.findViewById(R.id.sn);
//                        TextView name = viewHolder.findViewById(R.id.name);
//                        ImageView image1 = viewHolder.findViewById(R.id.image1);
//                        ImageView image2 = viewHolder.findViewById(R.id.image2);
//                        ImageView image3 = viewHolder.findViewById(R.id.image3);
//                        TextView spec = viewHolder.findViewById(R.id.spec);
//                        TextView option1 = viewHolder.findViewById(R.id.option1);
//                        TextView option2 = viewHolder.findViewById(R.id.option2);
//                        TextView option3 = viewHolder.findViewById(R.id.option3);
//                        sn.setText(String.format("订单编号：%s", item.id));
//
//                    }
//                };
//                mRecyclerView.setAdapter(mAdapter);
//                break;
//            case 4: //已取消
//                mAdapter = new BaseRecyclerAdapter<Order2>(R.layout.item_order_canceled) {
//                    @Override
//                    protected void convert(ViewHolder viewHolder, Order2 item, int position) {
//                        TextView sn = viewHolder.findViewById(R.id.sn);
////                        TextView cancel = viewHolder.findViewById(R.id.cancel);
//                        TextView name = viewHolder.findViewById(R.id.name);
//                        ImageView image1 = viewHolder.findViewById(R.id.image1);
//                        ImageView image2 = viewHolder.findViewById(R.id.image2);
//                        ImageView image3 = viewHolder.findViewById(R.id.image3);
//                        TextView spec = viewHolder.findViewById(R.id.spec);
//                        TextView option1 = viewHolder.findViewById(R.id.option1);
//                        TextView option2 = viewHolder.findViewById(R.id.option2);
//
//                    }
//                };
//                mRecyclerView.setAdapter(mAdapter);
//                break;
//        }
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getPresenter().queryOrderList(status, mPage, Constants.LIMIT);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mPage++;
                getPresenter().queryOrderList(status, mPage, Constants.LIMIT);
            }
        });
        mRefreshLayout.autoRefresh();

    }

    @Override
    public OrderListPresenter createPresenter() {
        return new OrderListPresenter(new OrderListModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribeEvent(Event.CancelOrdersEvent event) {
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void setOrderList(long currPage, long totalPage, List<Order2> orderList) {
        if (currPage >= totalPage) {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
            mAdapter.replaceAll(orderList);
        } else {
            if (currPage == 1) {
                mRefreshLayout.finishRefresh();
                mAdapter.replaceAll(orderList);
            } else {
                mRefreshLayout.finishLoadMore();
                mAdapter.addAll(orderList);
            }
        }
    }

    @Override
    public void refresh() {
        mRefreshLayout.autoRefresh();
    }

}
