package com.example.googlemapsproject;

import android.media.Image;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

public class Venue {
	
	public String eventName;
	public LatLng eventPos;
	public String eventAddress;
	public String eventPostCode;
	public String eventSport;
	public BitmapDescriptor eventImage;
	
	public Venue (String name, LatLng position, String address, String post, String sport, BitmapDescriptor image)
	{
		this.eventName = name;
		this.eventPos = position;
		this.eventAddress = address;
		this.eventPostCode = post;
		this.eventSport = sport;
		this.eventImage = image;
	}

}
