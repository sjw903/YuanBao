package com.yuanbaogo.mine.order.model;

import java.util.List;

public class LikeListModel{
	private List<LikeListModelItem> likeListModel;

	public void setLikeListModel(List<LikeListModelItem> likeListModel){
		this.likeListModel = likeListModel;
	}

	public List<LikeListModelItem> getLikeListModel(){
		return likeListModel;
	}

	@Override
 	public String toString(){
		return 
			"LikeListModel{" + 
			"likeListModel = '" + likeListModel + '\'' + 
			"}";
		}
}