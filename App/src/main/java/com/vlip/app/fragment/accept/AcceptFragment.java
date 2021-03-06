package com.vlip.app.fragment.accept;

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
import com.vlip.ui.fragment.LazyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AcceptFragment extends LazyFragment<AcceptPresenter> implements AcceptView {

    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    View refresh;
    Integer mPage = 1;
    BaseRecyclerAdapter<Order2> mAdapter;

    int status = 0;

    @Override
    public int getViewId() {
        return R.layout.fragment_accept;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        int space = DPUtils.dp2px(getResources(), 10);
        mRecyclerView.addItemDecoration(new LinearDividerItemDecoration(LinearDividerItemDecoration.VERTICAL, space, AppUtils.getColor(R.color.colorBg)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.refresh})
    public void onClick(View view){
        refresh();
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        mAdapter = new BaseRecyclerAdapter<Order2>(R.layout.item_order_process) {
            @Override
            protected void convert(ViewHolder viewHolder, Order2 item, int position) {
                TextView sn = viewHolder.findViewById(R.id.sn);
                TextView type = viewHolder.findViewById(R.id.type);
                TextView time = viewHolder.findViewById(R.id.time);
                TextView from = viewHolder.findViewById(R.id.from);
                TextView to = viewHolder.findViewById(R.id.to);
                sn.setText(String.format("???????????????%s", item.id));
//                sn.setText(String.format("???????????????%s", item.orderNumber));
                type.setText(item.type);
                //????????????????????????????????????
                if (item.acceptTime != null) time.setText(item.acceptTime.toLocaleString());
                from.setText(item.fromSite);
                to.setText(item.toSite);

            }

            @Override
            protected void onItemClick(Order2 item, int position) {
                Bundle b = new Bundle();
                b.putSerializable(Constants.INTENT_KEY1, item);
                b.putBoolean(Constants.INTENT_KEY2, true);
                ToolbarFragmentActivity.createFragment(requireContext(), OrderDetailFragment.class, b);
            }
        };
        mRecyclerView.setAdapter(mAdapter);

//        mAdapter = new BaseRecyclerAdapter<Order2>(R.layout.item_accept) {
//            @Override
//            protected void convert(ViewHolder viewHolder, Order2 item, int position) {
//                TextView sn = viewHolder.findViewById(R.id.sn);
////                TextView cancel = viewHolder.findViewById(R.id.cancel);
//                TextView name = viewHolder.findViewById(R.id.name);
//                ImageView image1 = viewHolder.findViewById(R.id.image1);
//                ImageView image2 = viewHolder.findViewById(R.id.image2);
//                ImageView image3 = viewHolder.findViewById(R.id.image3);
//                TextView spec = viewHolder.findViewById(R.id.spec);
//                TextView price = viewHolder.findViewById(R.id.price);
//                TextView option1 = viewHolder.findViewById(R.id.option1);
//                option1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        getPresenter().acceptOrder(getItem(position).id);
//                    }
//                });
//                sn.setText(String.format("???????????????%s", item.id));
////                        sn.setText(String.format("???????????????%s", item.sn));
////                        price.setText(AppUtils.toRMBFormat(item.amount));
////                        if (item.list.size() >= 3) {
////                            OrderItem item1 = item.list.get(0);
////                            OrderItem item2 = item.list.get(1);
////                            OrderItem item3 = item.list.get(2);
////                            image1.setVisibility(View.VISIBLE);
////                            image2.setVisibility(View.VISIBLE);
////                            image3.setVisibility(View.VISIBLE);
////                            AppUtils.loadImage(item1.image, image1);
////                            AppUtils.loadImage(item2.image, image2);
////                            AppUtils.loadImage(item3.image, image3);
////                            name.setText("");
////                            spec.setText("");
////                        } else if (item.list.size() == 2) {
////                            OrderItem item1 = item.list.get(0);
////                            OrderItem item2 = item.list.get(1);
////                            AppUtils.loadImage(item1.image, image1);
////                            AppUtils.loadImage(item2.image, image2);
////                            image1.setVisibility(View.VISIBLE);
////                            image2.setVisibility(View.VISIBLE);
////                            image3.setVisibility(View.GONE);
////                            name.setText("");
////                            spec.setText("");
////                        } else if (item.list.size() == 1) {
////                            OrderItem item1 = item.list.get(0);
////                            AppUtils.loadImage(item1.image, image1);
////                            image1.setVisibility(View.VISIBLE);
////                            image2.setVisibility(View.GONE);
////                            image3.setVisibility(View.GONE);
////                            name.setText(item1.name);
////                            spec.setText(AppUtils.getSelectSpecValue(item1.specificationValues));
////                        }
//            }
//
//
//        };
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getPresenter().queryAcceptList(status, mPage, Constants.LIMIT);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mPage++;
                getPresenter().queryAcceptList(status, mPage, Constants.LIMIT);
            }
        });
        mRefreshLayout.autoRefresh();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribeEvent(Event.TakeOrdersEvent event) {
        refresh();
    }

    @Override
    public AcceptPresenter createPresenter() {
        return new AcceptPresenter(new AcceptModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
        if (mAdapter.getData().size() == 0) {
            refresh.setVisibility(View.VISIBLE);
        } else {
            refresh.setVisibility(View.GONE);
        }
    }


    @Override
    public void refresh() {
        mRefreshLayout.autoRefresh();
    }

}
