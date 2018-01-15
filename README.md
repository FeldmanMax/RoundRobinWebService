# RoundRobinWebService

Provides the RoundRobin ability using RestAPI.

Please look at the examples below.

Examples:
    
    // Returns the connection weight by endpoint 
    GET http://<base url>/api/metadata/connection/<connectionName>
    Response: 
              {
                "success" : "true",
                "data" : {
                  "totalWeight" : 200,
                  "endpointsWeight" : [
                    {
                      "name" : "google_com",
                      "size" : 100
                    },
                    {
                      "name" : "google_th",
                      "size" : 100
                    }
                  ]
                }
              }
    
    // Get the next endpoint for a specific connection
    GET http://<base url>/api/action/connection/<connectionName>
    Response:
            {
              "success" : "true",
              "data" : {
                "parentConnection" : "search_google",
                "connection" : "search_google",
                "endpoint" : "google_th",
                "value" : "http://www.google.co.th"
              }
            }
            
    // Updates the connection
    PUT http://<base url>/api/action/connection/<endpointName>
    Data (JSON):
        {
    	    "weightRate": {
    		"isSuccess": true,
    		"isPercent": false,
    		"quantity": 10
            }
        }
    