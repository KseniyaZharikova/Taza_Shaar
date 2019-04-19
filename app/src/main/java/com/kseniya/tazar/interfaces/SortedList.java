package com.kseniya.tazar.interfaces;

import com.kseniya.tazar.data.ReceptionPoint;

import java.util.ArrayList;

public interface SortedList {
    ArrayList<ReceptionPoint> list = new ArrayList<>();

    void onClickItem(int position);
    void setNoResultVisible(boolean isEmpty);
}
