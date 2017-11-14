package com.zk.walkwayapp.view.fragment;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zk.library.common.mvp.BaseFragment;
import com.zk.library.common.mvp.ContentView;
import com.zk.library.common.mvp.IMvpPresenter;
import com.zk.library.common.utils.DateUtils;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.model.bean.PushModel;
import com.zk.walkwayapp.model.dao.PushMsgDbUtils;

import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_msg)
public class MsgFragment extends BaseFragment {


    @Bind(R.id.tv_nomsg)
    TextView nomsg;
    @Bind(R.id.listview)
    ListView listview;

    @Override
    protected void load() {
        List<PushModel> model = PushMsgDbUtils.queryMsgs();
        if (model == null ||model.size() == 0){
            nomsg.setVisibility(View.VISIBLE);
        }else {
            nomsg.setVisibility(View.GONE);
            listview.setAdapter(new Myadapter(model));
        }
    }

    private class Myadapter extends BaseAdapter{

        private List<PushModel> models;
        private Myadapter(List<PushModel> models){
            this.models = models;
        }
        @Override
        public int getCount() {
            return models.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.msg_item,null);

            }
            TextView tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            tvContent.setText(models.get(position).getMsg());
            TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            tvDate.setText(DateUtils.formatDate(models.get(position).getDate()));
            return convertView;
        }
    }
    @Override
    protected IMvpPresenter getPresenter() {
        return null;
    }

}
