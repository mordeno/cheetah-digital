
###### Overview
A simple simulation of image uploader application that demonstrates queueing, filtering, and saving data using Redis.  
At startup it will initialize datasets. It will then trigger an uploadImage method that will push data to a queue,   
consume the data, and check if data already exists in storage.

###### How To Run
 * Start a Redis server on default port (6379).  
 * Run main method

