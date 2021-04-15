# Inverted Index
Course Project Option 2

Video walkthrough: 
  https://pitt.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=cb037bc3-cbbf-48af-ba9c-ad0b003bc982

This application does not allow for direct folder uploads to the cloud but does support inverted indexing for the data files that were provided. The data files are unzipped(extracted) so as to display the folder and file names in which they reside. This program assumes that jobs for specific files or all files have been run with the outputs stored in a bucket. The projectID and bucketName final variables may be changed in Window.java. The jsonFilePath variable  must be changed to reflect the appropriate GCP Credentials. 

Implemented: 
First java Application Implementation and Execution on Docker
Docker to Local (or GCP) Cluster Communication
Inverted Indexing mapReduce Implementation and Execution on the Cluster (GCP)
Exact Term Search (but NOT Top-N Search)
JTable for Exact Term Search

Docker commands: 
docker build -t <imagename> .

On xQuartz: 
docker run --rm -e DISPLAY=<HOST_IP>:0 <imagename>

Overall, the project was not too bad. It wasn't very code-heavy but it definitely required a lot of research. Some of the problems I ran into were not very well-documented and only had a couple of answers on StackOverflow. But I learned a lot through this project. 
