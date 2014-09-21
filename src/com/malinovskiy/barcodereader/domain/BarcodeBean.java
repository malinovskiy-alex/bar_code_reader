package com.malinovskiy.barcodereader.domain;

/**
 * Created by malinovsky on 9/17/2014.
 */
public class BarcodeBean {
	private String type;
	private String locationCode;
	private String barcode;

	public BarcodeBean() {
		super();
	}

	public BarcodeBean(String locationCode, String type, String barcode) {
		this.locationCode = locationCode;
		this.type = type;
		this.barcode = barcode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String toString() {
		return "{\"locationCode\" : \"" + locationCode + "\", \"type\" : \""
				+ type + "\", \"barcode\":\"" + barcode + "\"}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		BarcodeBean bean = (BarcodeBean) o;

		if (barcode != null ? !barcode.equals(bean.barcode)
				: bean.barcode != null)
			return false;
		if (locationCode != null ? !locationCode.equals(bean.locationCode)
				: bean.locationCode != null)
			return false;
		if (type != bean.type)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = type != null ? type.hashCode() : 0;
		result = 31 * result
				+ (locationCode != null ? locationCode.hashCode() : 0);
		result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
		return result;
	}
}
