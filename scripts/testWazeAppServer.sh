#!/bin/bash

APP1="localhost:9090"

clear

curl -v "http://localhost:9090/waze/routesWithDirections?end=156+5th+Avenue%2C+New+York%2C+NY+10010&start=6+East+57th+Street%2C+New+York%2C+NY+10022" -H "Accept: application/json"

echo "app1: $APP1  - Routes Directions"
response=$(curl --write-out %{http_code} --output /dev/null --silent --get --include "http://$APP1/waze/routesWithDirections?end=156+5th+Avenue%2C+New+York%2C+NY+10010&start=6+East+57th+Street%2C+New+York%2C+NY+10022" -H "Accept: application/json")
echo $response

echo "app1: $APP1  - Address List"
response=$(curl --write-out %{http_code} --output /dev/null --silent --get --include "http://$APP1/waze/addressList?address=6+E+57th+St+New+York" -H "Accept: application/json")
echo $response

echo "app1: $APP1  - Routes Calculation"
response=$(curl --write-out %{http_code} --output /dev/null --silent --get --include "http://$APP1/waze/routes?end=156+5th+Avenue%2C+New+York%2C+NY+10010&start=6+East+57th+Street%2C+New+York%2C+NY+10022" -H "Accept: application/json")
echo $response

echo "app1: $APP1  - Routes Calculation Coordinates"
response=$(curl --write-out %{http_code} --output /dev/null --silent --get --include "http://$APP1/waze/routesXY?endLat=40.762966999999996&endLon=-73.973968&startLat=40.76267793274429&startLon=-73.97328385997065" -H "Accept: application/json")
echo $response

echo "app1: $APP1  - Routes Directions Coordinates"
response=$(curl --write-out %{http_code} --output /dev/null --silent --get --include "http://$APP1/waze/routesXYwithDirections?endLat=40.762966999999996&endLon=-73.973968&startLat=40.76267793274429&startLon=-73.97328385997065" -H "Accept: application/json")
echo $response

echo "app1: $APP1  - Traffic Notifications"
response=$(curl --write-out %{http_code} --output /dev/null --silent --get --include "http://$APP1/waze/traffic-notifications?latBottom=40.72892879374242&latTop=40.733897887617324&lonLeft=-74.0093457698822&lonRight=-73.98462653160095" -H "Accept: application/json")
echo $response

echo "Finished"


