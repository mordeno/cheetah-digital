
### Overview
A simple simulation of image uploader application that demonstrates queueing, filtering, and saving data using Redis.  
At startup it will initialize datasets. It will then trigger an `uploadImages` method that will push data to a queue,   
consume the data, and check if data already exists in storage.

### Program Flow
At startup, data from active.json will be saved to Redis. 
Also, a bloom filter will be populated with the same dataset.
After data initialization, `uploadImages` method will be triggered.  

All sample data in stream.json will be pushed to a queue one by one.
At the same time, a consumer listens to that event and will consume the data.
Each data will be checked first in bloom filter. If it exists, it will check again in Redis storage.
If it does not exists, it will increment the total count for 'not matched' data.  

Data matched will print _"Image ID {id} already exists!"_  
Data not matched will print _"Image ID {id} uploaded successfully!"_  

Expected results:  
Total matched - 2355  
Total not matched - 2645  
 

### How To Run
 * Start a Redis server on default port (6379).  
 * Run main method
 
 ### Technologies
 * Spring Boot
 * Redisson
 * Rqueue (https://github.com/sonus21/rqueue)

