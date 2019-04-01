package com.kaiwen.base.modles.gis;

public class Gps
{

	/**
	 * latitude 纬度 -90-90
	 */
	private double wgLat;
	/**
	 * longitude 经度 -180~180
	 */
	private double wgLon;

	public Gps(double wgLat, double wgLon)
	{
		setWgLat(wgLat);
		setWgLon(wgLon);
	}

	public double getWgLat()
	{
		return wgLat;
	}

	public void setWgLat(double wgLat)
	{
		this.wgLat = wgLat;
	}

	public double getWgLon()
	{
		return wgLon;
	}

	public void setWgLon(double wgLon)
	{
		this.wgLon = wgLon;
	}

	@Override
	public String toString()
	{
		return wgLat + "," + wgLon;
	}

}
