package reformad.api.model;

import java.util.Date;

public class Issue {
	private long id;
	private float latitude;
	private float longitude;
	private Date creation_date;
	private String user;
	private String district;
	private String neighbourhood;
	private String address;
	
	public Issue() {
		
	}
	
	public Issue(long id, float latitude, float longitude, Date creation_date,
			String user, String district, String neighbourhood, String address) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.creation_date = creation_date;
		this.user = user;
		this.district = district;
		this.neighbourhood = neighbourhood;
		this.address = address;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
