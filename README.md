# Synchronize-at-Gateway

This is a Zuul API Gateway application which can be used to make a an http request (which has some asynchrnous 
process) synchronous. (Asynchrnous process involving transfer of messages using kafka topics.) 

It consists of a post-filter which blocks the request, and only let it proceed when it has received a message from a 
the pre-defined kafka topic. The message in this kafka topic should be added only when the whole asynchrnous process is 
complete. The messages read from this topic are treated as responses to the request. The response received from the service is 
ignored. 


-------------------------

It relies on use of a correlationId, which can be used to track a request, response, and message on kafka topic. If 
the request does not have any such unique if, the correlationId filter can be enabled which will add such a unique id
 to each request. All the request, response, and messages on the topic are assumed to be JSON. [JsonPath](https://github.com/json-path/JsonPath) library is 
 for defining pointers which are used to get/add correlationId to the JSON body.

--------------------------

The messages from the kafka topic are stored as response corresponding to a correlationId (which is recorded in a 
pre-filter). This storage is polled from the post-filter. The wait time between 2 subsequent requests to the storage
 is configurable.  


-----------------------

### Enhancements:

Currently all the correlationIds and responses are stored in an in-memmory HashMap and HashSet. CorrelationIdResponseRepository.java can 
be modified to support storing this data in cache, database, etc. 

