package com.xujie.crm.workbench.vo;

import com.xujie.crm.workbench.domain.Activity;

import java.util.List;

public class UpdateVo<T> {
    private Activity activity;
    private List<T> dataList ;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
