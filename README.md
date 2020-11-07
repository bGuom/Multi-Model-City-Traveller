# Multi-Model-City-Traveller
Multi Model City Traveller is a mobile phone application that guides you when moving from one city to another city about travelling ways, travelling cost, time and distance. This app calculates the quickest and cheapest multi model travel method using buses, train and taxis. This app uses the real time traffic data from google directions API.

## Setup
You need to create a Google cloud console account and enable billing. Activate following APIs and generate an API key. Place the API key in project AndroidMenifest file and in MainActivity.java file

Google Maps SDK for Android
Google Places API
Google Directions API

## Data Sources

Train time tables, Train ticket fairs, Bus routes , Bus times, Bus fares,  Taxi staions, Taxi fairs were collected and fed into a local database of the application.
Google Firebase Realtime databasae was used as online data storage.

## Maps 
Google Maps SDK for Android was used for map data

## Places Search
Google Places API was used for place searching

## Directions
Google directions API was used to get 
  Traffic congestions
  Distance of the trip
  Estimated trip time based on travel mode
  Ploting data for directions

## Graph - Used for shortest distance calculation 

Dynamic graph was generated for both travel time and travel cost between user's start and end locations. Travel time and cost was used as weights for edges in the graph. Nodes were selected as

S = start
E = end
Tr = nearest train station
Tx = neareset taxi location
B = nearest bus halt

dijkstra's shorest path algorithm was applied to the generated graph to find the fastest and cheapest routes using multi model transporation.

<img src="https://raw.githubusercontent.com/bGuom/Multi-Model-City-Traveller/main/data/graph.png" width="720">
