WebMIaS version 1.5

To configure WebMIaS for searching indexes created by MIaS, indexes. properties file needs to be edited. It is located in the WebMIaS.war file in WEB-INF\classes\cz\muni\fi\webmias directory.
This file contains a comma-separated list of indexes that we want WebMIaS to search in. I also contains a comma-separated list of root directories where the files that are indexed in the corresponding index are located.

Example:
INDEXES=/home/MIaS/index
STORAGES=/home/MIaS/

This states that the index is located in the /home/MIaS/index directory and the files have the relative path beggining at /home/MIaS/. They were indexed using /home/MIaS/ root directory.


Multiple indexed can be loaded using comma-separated list of paths.

Example:
INDEXES=/home/MIaS/index1,/home/MIaS/index2
STORAGES=/home/MIaS/,/home/data/

This denotes that the index located in
/home/MIaS/index1 uses /home/MIaS/ root directory for documents

and the index located in
/home/MIaS/index2 uses /home/data root directory for documents.

INDEXES[i] -> STORAGES[i]