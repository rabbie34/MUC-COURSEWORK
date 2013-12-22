package com.example.googlemapsproject;

import java.util.LinkedList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private GoogleMap mMap;
	private LinkedList<Venue> venues;
	private MainActivity main;
	LocationManager locMgr;
	MyLocationListener locLstnr;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        main = this;
        
        // venues is a list of all of the venues
        venues = new LinkedList<Venue>();
        
        // this is all the external data regarding the venues
        Venue athletes = new Venue("Athletes' Village", new LatLng(55.846506,-4.207767), "Springfield Road", "G40 3ET", "",BitmapDescriptorFactory.fromResource(R.drawable.athletes));
        Venue barry = new Venue("Barry Buddon Shooting Centre", new LatLng(56.492785,-2.746206), "Carnoustie", "DD7 7RY", "Shooting",BitmapDescriptorFactory.fromResource(R.drawable.barry));
        Venue cathkin = new Venue("Cathkin Braes Mountain Bike Park", new LatLng(55.795817 , -4.215488), "Cathkin Braes Country Park", "G45", "Mountain Bike",BitmapDescriptorFactory.fromResource(R.drawable.cathkin));
        Venue celtic = new Venue("Celtic Park", new LatLng(55.849722 , -4.205556), "18 Kerrydale Street", "G40 3R3", "",BitmapDescriptorFactory.fromResource(R.drawable.celtic));
        Venue velodrome = new Venue("Commonwealth Arena and Velodrome", new LatLng(55.847222 , -4.208042), "1000 London Rd", "G40 3HY", "Badminton, Cycling",BitmapDescriptorFactory.fromResource(R.drawable.velodrome));
        Venue hockey = new Venue("Glasgow National Hockey Centre", new LatLng(55.8491 , -4.23325), "Glasgow Green", "G1 5DB", "Hockey",BitmapDescriptorFactory.fromResource(R.drawable.hockey));
        Venue hampden = new Venue("Hampden Park", new LatLng(55.825829 , -4.251902), "Hampden Park", "G42 9BA", "Athletics",BitmapDescriptorFactory.fromResource(R.drawable.hampden));
        Venue ibrox = new Venue("Ibrox Stadium", new LatLng(55.853203 , -4.309173), "150 Edmiston Drive", "G51 2XD", "Rugby Sevens",BitmapDescriptorFactory.fromResource(R.drawable.ibrox));
        Venue kelvingrove = new Venue("Kelvingrove Lawn Bowls Centre", new LatLng(55.867449 , -4.289432), "Kelvingrove Park", "G3 6BY", "Lawn Bowls",BitmapDescriptorFactory.fromResource(R.drawable.kelvingrove));
        Venue pool = new Venue("Royal Commonwealth Pool", new LatLng(55.938536 , -3.173631), "21 Dalkeith Rd", "EH16 5BB", "Diving",BitmapDescriptorFactory.fromResource(R.drawable.pool));
        Venue secc = new Venue("SECC Precinct", new LatLng(55.860767 , -4.287071), "Exhibition Way", "G3 8YW", "Boxing, Gymnastics, Judo, Netball, Wrestling, Weightlifting",BitmapDescriptorFactory.fromResource(R.drawable.secc));
        Venue scotstoun = new Venue("Scotstoun Sports Campus", new LatLng(55.879139 , -4.339621), "72 Danes Drive", "G14 9HD", "Squash, Table Tennis",BitmapDescriptorFactory.fromResource(R.drawable.scotstoun));
        Venue strathclyde = new Venue("Strathclyde Country Park", new LatLng(55.797421 , -4.034128), "366 Hamilton Rd", "ML1 3ED", "Triathlon",BitmapDescriptorFactory.fromResource(R.drawable.strathclyde));
        Venue tollcross = new Venue("Tollcross Swimming Centre", new LatLng(55.8449 , -4.17596), "350 Wellshot Rd", "G32 7QR", "Swimming",BitmapDescriptorFactory.fromResource(R.drawable.tollcross));
        
        // adding each of the above venues to the list of venues
        venues.add(athletes);
        venues.add(barry);
        venues.add(cathkin);
        venues.add(celtic);
        venues.add(velodrome);
        venues.add(hockey);
        venues.add(hampden);
        venues.add(ibrox);
        venues.add(kelvingrove);
        venues.add(pool);
        venues.add(secc);
        venues.add(scotstoun);
        venues.add(strathclyde);
        venues.add(tollcross);
        
        // getting the map in our layout
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        // moves the camera to glasgow when the app is loaded
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.8580, -4.2590) , 12.0f));
        
        // places a marker over glasgow
        mMap.addMarker(new MarkerOptions().position(new LatLng(55.8580, -4.2590)).title("Glasgow"));
        
        // goes through every venue from our list above and places a marker for each of them.
        // each venue has their own icon, position and title
        for (int i = 0; i < venues.size(); i++)
        {
        	Venue curVenue = venues.get(i);
        	mMap.addMarker(new MarkerOptions().position(curVenue.eventPos).title(curVenue.eventName).icon(curVenue.eventImage));
        }
        
        // listener for when markers are clicked
        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                
            	// when a marker is clicked, get the name of the marker
            	String eventName = marker.getTitle();
            	Venue selectedVenue = null;
            	Boolean search = false;
            	
            	// loop through every venue in our list, if any of the venues match the name
            	// of the marker, then select that venue
            	for (int i = 0; i < venues.size(); i++)
            	{
            		if(venues.get(i).eventName.equals(eventName))
            		{
            			selectedVenue = venues.get(i);
            			
            			// sets the search status to success
            			search = true;
            		}
            	}
            	
            	// if we succeeded in finding the venue
            	if (search)
            	{
            		AlertDialog.Builder builder = new AlertDialog.Builder(main);
            		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
            		{
            			public void onClick(DialogInterface dialog, int id) 
            			{
            				dialog.cancel();
            			}
            		});
            		
            		// if the venue actually has a sport, then display it. otherwise ignore it.
            		if(selectedVenue.eventSport.equals(""))
            		{
            			builder.setMessage("Address: "+selectedVenue.eventAddress + "\nPostcode: " + selectedVenue.eventPostCode);
            		}
            		else
            		{
            			builder.setMessage("Sport: "+selectedVenue.eventSport + "\nAddress: "+selectedVenue.eventAddress + "\nPostcode: " + selectedVenue.eventPostCode);
            		}
            		builder.setTitle(selectedVenue.eventName);
            		AlertDialog alert = builder.create();	
            		alert.show();
            	}
            	return false;
            }
        });
        
        

    }
    
    
    // instantiate the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
    
    
    // when an action bar item is selected
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_hybrid:
			// hybrid map
			// set the map type to hybrid
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			return true;
		case R.id.action_normal:
			// normal map
			// set the map type to normal
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			return true;
		case R.id.action_location:
			// input location
			// opens a dialog box asking the user if he wants to enter location manually
			// or via GPS
			AlertDialog.Builder builderlocation = new AlertDialog.Builder(main);
			builderlocation.setTitle("Location");
			builderlocation.setPositiveButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			final CharSequence[] itemslocation = {"GPS", "Manual"};
			builderlocation.setItems(itemslocation, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) 
				{
					switch (which){
					case 0:
						// gps controls here
						locMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
				        locLstnr = new MyLocationListener();
				        locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locLstnr);
						break;
					case 1:
						// manual controls here
						mMap.addMarker(new MarkerOptions().position(mMap.getCameraPosition().target).title("Me").draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.man)));
						
						break;
					}
				}});
			AlertDialog alertlocation = builderlocation.create();
			alertlocation.show();
			return true;
		case R.id.action_previous:
			// show previous cities
			// open a dialog box asking the user which previous city they would like to go to
			AlertDialog.Builder builder = new AlertDialog.Builder(main);
			builder.setTitle("Previous Commonwealth Holders");
			builder.setPositiveButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			final CharSequence[] items = {"Glasgow", "Delhi", "Melbourne", "Manchester", "Kuala Lumper", "Victoria", "Auckland", "Edinburgh", "Brisbane", "Edmonton", "Christchurch"};
			builder.setItems(items,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							// if glasgow is selected, app is essentially reset and put back to its initial state.
							case 0:
					        	mMap.clear();
					        	mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.8580, -4.2590) , 12.0f));
					        	mMap.addMarker(new MarkerOptions().position(new LatLng(55.8580, -4.2590)).title("Glasgow"));
					            
					            for (int i = 0; i < venues.size(); i++)
					            {
					            	Venue curVenue = venues.get(i);
					            	mMap.addMarker(new MarkerOptions().position(curVenue.eventPos).title(curVenue.eventName).icon(curVenue.eventImage));
					            }
					            break;
					        // for each item in the list, go to its position displaying a marker there
					        // the marker shows the name of the city, what year it happened, how many medals scotland won, what country it was in 
					        // the icon for the marker is the country's flag.
							case 1:
					        	mMap.clear();
					        	mMap.addMarker(new MarkerOptions().position(new LatLng(28.61, 77.23)).title("Delhi Commonwealth 2010").snippet("Scotland won 9 gold medals, 10 silver medals, and 7 bronze medals in India").icon(BitmapDescriptorFactory.fromResource(R.drawable.indiaflag)));
					        	mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.61, 77.23) , 6.0f));
					        	break;
							case 2:
								mMap.clear();
								mMap.addMarker(new MarkerOptions().position(new LatLng(-37.813611, 144.963056)).title("Melbourne Commonwealth 2006").snippet("Scotland won 11 gold medals, 7 silver medals, and 11 bronze medals in Australia").icon(BitmapDescriptorFactory.fromResource(R.drawable.australiaflag)));
								mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-37.813611, 144.963056) , 6.0f));
								break;
							case 3:
								mMap.clear();
								mMap.addMarker(new MarkerOptions().position(new LatLng(53.466667, -2.233333)).title("Manchester Commonwealth 2002").snippet("Scotland won 6 gold medals, 8 silver medals, and 16 bronze medals in England").icon(BitmapDescriptorFactory.fromResource(R.drawable.englandflag)));
								mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.466667, -2.233333) , 6.0f));
								break;
							case 4:
								mMap.clear();
								mMap.addMarker(new MarkerOptions().position(new LatLng(3.1475, 101.693333)).title("Kuala Lumpur Commonwealth 1998").snippet("Scotland won 3 gold medals, 2 silver medals, and 7 bronze medals in Malaysia").icon(BitmapDescriptorFactory.fromResource(R.drawable.malaysiaflag)));
								mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(3.1475, 101.693333) , 6.0f));
								break;
							case 5:
								mMap.clear();
								mMap.addMarker(new MarkerOptions().position(new LatLng(48.428611, -123.365556)).title("Victoria Commonwealth 1994").snippet("Scotland won 6 gold medals, 3 silver medals, and 11 bronze medals in Canada").icon(BitmapDescriptorFactory.fromResource(R.drawable.canadaflag)));
								mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.428611, -123.365556) , 6.0f));
								break;
							case 6:
								mMap.clear();
								mMap.addMarker(new MarkerOptions().position(new LatLng(-36.840417, 174.739869)).title("Auckland Commonwealth 1990").snippet("Scotland won 5 gold medals, 7 silver medals, and 10 bronze medals in New Zealand").icon(BitmapDescriptorFactory.fromResource(R.drawable.newzealandflag)));
								mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-36.840417, 174.739869) , 6.0f));
								break;
							case 7:
								mMap.clear();
								mMap.addMarker(new MarkerOptions().position(new LatLng(55.939, -3.172)).title("Edinburgh Commonwealth 1986").snippet("Scotland won 3 gold medals, 12 silver medals, and 18 bronze medals in Scotland").icon(BitmapDescriptorFactory.fromResource(R.drawable.scotlandflag)));
								mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(55.939, -3.172) , 6.0f));
								break;
							case 8:
								mMap.clear();
								mMap.addMarker(new MarkerOptions().position(new LatLng(-27.467917, 153.027778)).title("Brisbane Commonwealth 1982").snippet("Scotland won 8 gold medals, 6 silver medals, and 12 bronze medals in Australia").icon(BitmapDescriptorFactory.fromResource(R.drawable.australiaflag)));
								mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-27.467917, 153.027778) , 6.0f));
								break;
							case 9:
								mMap.clear();
								mMap.addMarker(new MarkerOptions().position(new LatLng(53.533333, -113.5)).title("Edmonton Commonwealth 1978").snippet("Scotland won 3 gold medals, 6 silver medals, and 5 bronze medals in Canada").icon(BitmapDescriptorFactory.fromResource(R.drawable.canadaflag)));
								mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.533333, -113.5) , 6.0f));
								break;
							case 10:
								mMap.clear();
								mMap.addMarker(new MarkerOptions().position(new LatLng(-43.53, 172.620278)).title("Christchurch Commonwealth 1974").snippet("Scotland won 3 gold medals, 5 silver medals, and 11 bronze medals in New Zealand").icon(BitmapDescriptorFactory.fromResource(R.drawable.newzealandflag)));
								mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-43.53, 172.620278) , 6.0f));
								break;
							}
						}
					});
			
			AlertDialog alert = builder.create();
			alert.show();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}


    // custom class to take GPS input.
	// listens for GPS input from the phone. when it is received, a toast message is displayed showing the location
	// a marker is also placed at the location.
	public class MyLocationListener implements LocationListener
	{
	@Override
	public void onLocationChanged(Location loc)
	{
	loc.getLatitude();
	loc.getLongitude();
	String Text = "My current location is: " +
	"Latitud = " + loc.getLatitude() +
	"Longitud = " + loc.getLongitude();
	Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
	mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title("Me").icon(BitmapDescriptorFactory.fromResource(R.drawable.man)));
	 
	}
	 
	@Override
	public void onProviderDisabled(String provider)
	{
	Toast.makeText( getApplicationContext(),
	"Gps Disabled",
	Toast.LENGTH_SHORT ).show();
	}
	 
	@Override
	public void onProviderEnabled(String provider)
	{
	Toast.makeText( getApplicationContext(),
	"Gps Enabled",
	Toast.LENGTH_SHORT).show();
	}
	 
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
	 
	}
	 
	}
   
}

