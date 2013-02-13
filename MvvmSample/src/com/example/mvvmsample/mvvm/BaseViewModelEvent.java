package com.example.mvvmsample.mvvm;

public interface BaseViewModelEvent<MODEL extends BaseStateModel> extends Event {
	void onDataBound(MODEL dataModel);
}
