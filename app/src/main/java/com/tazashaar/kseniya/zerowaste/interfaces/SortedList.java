package com.tazashaar.kseniya.zerowaste.interfaces;

import com.tazashaar.kseniya.zerowaste.data.ReceptionPoint;

import java.util.ArrayList;

public interface SortedList {
    ArrayList<ReceptionPoint> list = new ArrayList<>();

    void onClickItem(int position);
    void setNoResultVisible(boolean isEmpty);
}
