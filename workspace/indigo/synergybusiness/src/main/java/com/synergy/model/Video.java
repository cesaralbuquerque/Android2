package com.synergy.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries( { @NamedQuery(name = "Video.getVideos", query = "from Video u where u.owner=? and deleted is false order by dateCreated desc") })
public class Video extends AbstractPersistent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String videoId;
	private Date dateCreated;
	private Long size;
	private Long duration;
	private String name;

	private Integer viewCount = 0;

	private Boolean deleted = Boolean.FALSE;

	@Lob
	private byte[] videoFrame;

	@ManyToOne
	@JoinColumn(name = "Subscriber_id")
	private Subscriber owner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getDuration() {
		return duration;
	}

	public void setOwner(Subscriber owner) {
		this.owner = owner;
	}

	public Subscriber getSubscriber() {
		return owner;
	}

	public void setVideoFrame(byte[] videoFrame) {
		this.videoFrame = videoFrame;
	}

	public byte[] getVideoFrame() {
		return videoFrame;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getViewCount() {
		return viewCount;
	}

}
