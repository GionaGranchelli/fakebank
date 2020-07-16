

## Build
    ./build
 This will build the backend project and, building two images for backend and frontend
## Run
    docker-compose up
    
will start to container
the project frontend will be available at 
        
        localhost:80
        
the backend will be available at

        localhost:8080
        
## ATTENTION
During the CSV conversion, a little problem can raise. 
To extract value from start/endBalance and mutation I use 
    
    Utils.roundDouble(Double.parseDouble(value)
    
In Case of a value having a fomat like "8E+1"
Is gonna ingest these value as Hex value,
for example "8E+1" gets converted in the value 80.00 
