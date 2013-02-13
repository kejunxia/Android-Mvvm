package com.example.mvvmsample.mvvm;

public abstract class BaseViewModel<EVENT extends BaseViewModelEvent<MODEL>, MODEL extends BaseStateModel>
		extends Observable<EVENT> {
	protected MODEL mModel;

	/**
	 * Model is persistable data that keeps the state of the view model so can
	 * be used to initialize the view model.
	 * 
	 * @return the model
	 */
	public MODEL getModel() {
		return mModel;
	}

	/**
	 * Model is persistable data that keeps the state of the view model so can
	 * be used to initialize the view model.
	 * <p>
	 * Binding a data model should update all sub-views to reflect the data. To
	 * To update specific data and a sub-view, create more methods to do so in
	 * derived classes.
	 * </p>
	 * 
	 * @param dataModel
	 */
	public void bindData(MODEL dataModel) {
		mModel = dataModel;
		for (BaseViewModelEvent<MODEL> event : this) {
			event.onDataBound(dataModel);
		}
	}
}
