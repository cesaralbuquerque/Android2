package com.synergy.dao;

import java.util.List;

import com.synergy.model.Subscriber;
import com.synergy.model.Video;

public interface VideoDao extends BaseDao<Video> {

	public Video getVideoByVideoId(String id);
	
	public List<Video> getVideos(Subscriber subscriber);

}
