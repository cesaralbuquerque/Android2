package com.synergy.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.synergy.dao.VideoDao;
import com.synergy.model.Subscriber;
import com.synergy.model.Video;

public class VideoDaoImpl extends AbstractBasicHibernateDao<Video> implements VideoDao {

	public Video getVideoByVideoId(String id) {
		final Query query = createQuery("from Video where videoId = ?");
		query.setParameter(1, id);
		return (Video) query.getSingleResult();
	}
	
	public List<Video> getVideos(Subscriber subscriber) {
		return (List<Video>) createNamedQuery("Video.getVideos", subscriber).getResultList();
	}

}
