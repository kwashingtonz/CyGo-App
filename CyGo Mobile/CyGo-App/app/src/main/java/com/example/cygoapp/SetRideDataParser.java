package com.example.cygoapp;

import android.util.Log;

import com.example.cygoapp.constant.Constant;
import com.example.cygoapp.models.Route;
import com.example.cygoapp.util.AppMath;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetRideDataParser {

    public List<Route> parse(JSONObject jObject) {

        List<Route> routes = new ArrayList<>();
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;
        JSONObject jDistance;
        JSONObject jDuration;
        String distance;
        String duration;

        try {

            // Getting routes
            jRoutes = jObject.getJSONArray("routes");
            /** Traversing all routes */
            for (int i = 0; i < jRoutes.length(); i++) {

                int totalDistance = 0;
                int totalSeconds = 0;
                int myIndex = 0;

                // Getting bounds
                JSONObject jBounds = ((JSONObject)jRoutes.get(i)).getJSONObject("bounds");
                HashMap<String, Double> bounds = new HashMap<>();
                // Bounds are the furthest latitude (east,west) and longitude (north,south) points plus added 5 km (amount may be changed..)
                double maxLat = ((JSONObject)jBounds.get("northeast")).getDouble("lat") + AppMath.getDeltaLatitude(Constant.BoundsInKilometers);
                double minLat = ((JSONObject)jBounds.get("southwest")).getDouble("lat") - AppMath.getDeltaLatitude(Constant.BoundsInKilometers);
                double maxLng = ((JSONObject)jBounds.get("northeast")).getDouble("lng") + AppMath.getDeltaLongitude(Constant.BoundsInKilometers, minLat);
                double minLng = ((JSONObject)jBounds.get("southwest")).getDouble("lng") - AppMath.getDeltaLongitude(Constant.BoundsInKilometers, maxLat);

                bounds.put("north", maxLat);
                bounds.put("east", maxLng);
                bounds.put("south", minLat);
                bounds.put("west", minLng);

                Log.d("LATLNG: ", "Lats: " + minLat + " " + maxLat + " Longs: " + minLng + " " + maxLng);

                // Getting legs
                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                Log.d("mytag", "parse: " + jLegs);
                List<HashMap<String, Double>> path = new ArrayList<>();  // Holds all coordinate points of this route
                List<HashMap<String, Double>> myList = new ArrayList<>();   // Holds every 100th coordinate point of this route
                /** Traversing all legs */
                for (int j = 0; j < jLegs.length(); j++) {
                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");
                    Log.d("mytag", "jsteps: " + jSteps);


                    jDistance = ((JSONObject) jLegs.get(j)).getJSONObject("distance");
                    totalDistance = totalDistance + Integer.parseInt(jDistance.getString("value"));


                    jDuration = ((JSONObject) jLegs.get(j)).getJSONObject("duration");
                    totalSeconds = totalSeconds + Integer.parseInt(jDuration.getString("value"));

                    /** Traversing all steps */
                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline = "";
                        polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        /** Traversing all points */
                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, Double> hm = new HashMap<>();
                            hm.put("lat", (list.get(l)).latitude);
                            hm.put("lng", (list.get(l)).longitude);
                            path.add(hm);
                            if (myIndex % 100 == 0) {
                                myList.add(hm);
                            }
                            myIndex++;
                        }
                    }


                    int dist = totalDistance / 1000;
                    //SetRideConstant.DISTANCE = String.valueOf(dist);


                    int hours = (totalSeconds / 3600);
                    int minutes = ((totalSeconds - hours * 3600) / 60);
                    //SetRideConstant.DURATION = String.valueOf(hours + "h " + minutes + "min");


                    duration =  String.valueOf(hours) + " h " + minutes + " min";
                    distance = String.valueOf(dist);

                    // Building route object from necessary data and adding to list
                    Route route = new Route(path, myList, bounds, distance, duration);
                    routes.add(route);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }

        Log.d("TESTI", routes.toString());
        return routes;
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
