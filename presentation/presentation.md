# Intro
Overview diagram of the program. 

# Michael - DevOps
Features:

 - Rocketboy deploy
 - Lab log website
 - User guide
 - Service desk

Advantages:

 - Rocketboy deploy: Users and download the program quickly and see the latest release notes. They can test out the program.

 - Lab log website: Easily log time entered and left times with a single click to avoid any overriding of information.

 - User guide: Tells the user how to interact with the program


Benefits: 

 - Rocketboy deploy: The benefit of the user being able to download the program is so they can give feedback and discover bugs before the product is ready for launch. The program will result in a better quality project by resolving the bugs found so the rocket simulation is reliable and the rocket can launch safely.

 - Lab log website: 

 - User guide: Avoids service desk questions that can be easily answered. Resulting in more time to spend developing the program.


Questions that might be asked:

 - Why not make repo public instead of hosting a specific website?


Link log website to rocketboy deploy: 

From developing the lab log website, we were inspired to make more tools to increase the productivity and quality of the program. Rocketboy deploy does precisely this; it allows any user to download the program quickly to use the product and send us feedback. This website has been in released to the public for the past five weeks. 

# Max - UX/UI

# Alex - 3D graph and kmeans

3D graph video
Image of graph from the side

 - Clusters are identified using a kmeans algorithm. The number of clusters is set by the user.
 - These clusters provide a representation of the most likely paths the rocket will take.
 - The closest simulated path to each cluster center is re-run to trace the path through 3D space.
 - These flightpaths are graphed on an interactive 3D graph.
 - These flightpaths show the rocket going up to varying altitudes then the parachute opening and it drifting downwind.  

 - This graph allows the user to see more information about the rocket's flightpath than is presented in the 2D scatter graph.
 - Shows overall stability, effects of wind, flight events like the parachute opening.
 - This detailed feedback allows the user to make more informed decisions about their rocket design and improve the overall design and flight plan.

# Jacqui - Mission Control integration

Video of the terminal window showing input params and loading simulation progress bar

- The Mission Control integration with our Monte Carlo Simulation software allows Mission Control Teams to easily interact and use our software.
- We created an API which uses a command to run our Monte Carlo Simulation software without running the Graphical Interface.
- The command takes in parameters such as the CSV file path to import and value which determines if the Graphical Interface should be present.

- The API will output two CSV files
- The first CSV File outputted called 'points.csv' contains the results of the Monte Carlo simulations, expressed as Longitude and Latitude points.
- The second CSV File outputted called 'SimulationStats.csv' contains additional information about the Monte Carlo Simulations.
    - The values in the SimulationStatscsv aims to provde an indepth report into the Monte Carlo Simulation, covering factors such as Landing Position Longitude,Landing Position Latitude,Landing Position Altitude,Simulation Time,Motor Ignited,Lift Off,Launch Rod Cleared,Tumbling,Launch Rod Angle,Launch Rod Direction,Warning Set,Max Alt Time,Effective Launch Rod Length (Cut down to the most appropriate ones lol)

Advantages

- Allows Mission Control Teams to easily interact and use our software.
- Outputs all of the vital information in a CSV format

Benefits
- Streamlined API which can be used anywhere in any environment
- 

# Justina - 2D graph and map

# Georgia - Monte Carlo implementation

Word cloud of mission control parameters, maybe a picture of the gui input, maybe a picture of the output as a scatter plot

- We use OpenRocket as a backend to perform Monte Carlo sims
- The benefits of this data is to make sims more accurate to real life conditions
- The benefits of running large numbers of sims (Monte Carlo) is getting a good idea of what will happen
- Random distribution to add a layer of variety to parameters

# Conclusion
