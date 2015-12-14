WebMIaS
=======

## Configuration

To configure WebMIaS for searching indexes created by MIaS, `indexes.properties`
file needs to be edited. It is located in the `WebMIaS.war` file in 
`WEB-INF/classes/cz/muni/fi/webmias` directory. This file contains a 
comma-separated list of indexes that we want WebMIaS to search in. I also 
contains a comma-separated list of root directories where the files that are 
indexed in the corresponding index are located.

Example:
```ini
INDEXES=/home/MIaS/index
STORAGES=/home/MIaS/
```

This states that the index is located in the `/home/MIaS/index` directory and
the files have the relative path beginning at `/home/MIaS/`. They were indexed
using `/home/MIaS/` as the root directory.

## Multiple indexes

Multiple indexes can be loaded using comma-separated list of paths.

Example:
```ini
INDEXES=/home/MIaS/index1,/home/MIaS/index2
STORAGES=/home/MIaS/,/home/data/
```

This denotes that the index located in `/home/MIaS/index1` uses `/home/MIaS/` as
the root directory for documents and the index located in `/home/MIaS/index2`
uses `/home/data` as the root directory for documents, e.g. 
`INDEXES[i] → STORAGES[i]`.

## JVM configuration

It can be useful to increase memory limits for Tomcat JVM running WebMIaS 
instance. See sample file `setenv.sh.sample`. Modify the configuration to fit
your needs and copy the result as an executable script `setenv.sh` in 
`$CATALINA_BASE/bin` of your Tomcat server to keep your customizations separate 
of the Tomcat distributional scripts.
