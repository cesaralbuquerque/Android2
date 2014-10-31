package com.synergy.service.impl;

import java.util.Date;
import java.util.List;

import com.synergy.dao.VideoDao;
import com.synergy.model.Subscriber;
import com.synergy.model.Video;
import com.synergy.service.VideoService;

public class VideoServiceImpl implements VideoService {

	private VideoDao videoDao;

	public Video find(Long id) {
		return videoDao.find(id);
	}

	public void save(Video t) {
		videoDao.save(t);
	}

	public Video getVideoByVideoId(String id) {
		return videoDao.getVideoByVideoId(id);
	}

	public Video createVideo(Subscriber owner, Long duration, Long size, String videoId, byte[] videoFrame) {
		Video video = new Video();
		video.setOwner(owner);
		video.setDuration(duration);
		video.setVideoId(videoId);
		video.setVideoFrame(videoFrame);
		video.setSize(size);
		final Date today = new Date();
		video.setDateCreated(today);
		video.setName("video_" + System.currentTimeMillis());
		videoDao.save(video);
		return video;
	}

	public void delete(Video video) {
		video.setDeleted(true);
		save(video);
		// videoDao.delete(video);
	}

	public List<Video> getVideos(Subscriber subscriber) {
		return videoDao.getVideos(subscriber);
	}

	public void setVideoDao(VideoDao videoDao) {
		this.videoDao = videoDao;
	}

	public VideoDao getVideoDao() {
		return videoDao;
	}

}
