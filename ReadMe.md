# Hotel Merger Project
Multiple sources data fetching, merging and cleaning using a few basic rules

## How does it work?
* Suppliers are loaded from mapped JSON objects in the supplier.json file so that we can easily add or modify a supplier without touching the code
* ...

## How to run?
* Compile: mvn clean package
* Run: java -jar target/hotel-merger-1.0.jar <hotel_ids> <destination_id>
    - hotel_ids: none or list of hotel id, each id is seperated using coma, ex: adsf13,afdds2
    - destination_ids: none or list of destination id (integer), each id is seperated using coma, ex: 1234,5678

