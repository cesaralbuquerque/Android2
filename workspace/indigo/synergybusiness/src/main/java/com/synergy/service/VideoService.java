package com.synergy.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.synergy.model.Subscriber;
import com.synergy.model.Video;

public interface VideoService extends BaseService<Video> {

	public Video getVideoByVideoId(String id);
	
	public List<Video> getVideos(Subscriber subscriber);

	@Transactional
	Video createVideo(Subscriber owner, Long duration, Long size, String videoId, byte[] videoFrame);

}
