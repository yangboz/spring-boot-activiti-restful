package info.smartkit.eip.thirdParty;

import java.lang.reflect.Array;

public class BaiduLoc {
	
	private Object location;
	public Object getLocation() {
		return location;
	}
	public void setLocation(Object location) {
		this.location = location;
	}
	private String formatted_address;
	public String getFormatted_address() {
		return formatted_address;
	}
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	private String business;
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	private Object addressComponent;
	public Object getAddressComponent() {
		return addressComponent;
	}
	public void setAddressComponent(Object addressComponent) {
		this.addressComponent = addressComponent;
	}
	private Object poiRegions;
	public Object getPoiRegions() {
		return poiRegions;
	}
	public void setPoiRegions(Object poiRegions) {
		this.poiRegions = poiRegions;
	}
	private long cityCode;
	public long getCityCode() {
		return cityCode;
	}
	public void setCityCode(long cityCode) {
		this.cityCode = cityCode;
	}
}
