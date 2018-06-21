WebMIaS – Web interface for [MIaS][]
====================================
[![CircleCI](https://circleci.com/gh/MIR-MU/WebMIaS/tree/master.svg?style=shield)][ci]

 [ci]: https://circleci.com/gh/MIR-MU/WebMIaS/tree/master (CircleCI)

[WebMIaS][] is a web interface for [MIaS][]. WebMIaS accepts math queries in
TeX or MathML notation combined with text queries. TeX queries are converted
to MathML on the fly. Results contain match snippets.

 [mias]: https://github.com/MIR-MU/MIaS
 [webmias]: https://mir.fi.muni.cz/webmias/#webmias

Usage
=====
Setting up `indexes.properties`
-------------------------------
Locate the file named `indexes.properties` in the
`WEB-INF/classes/cz/muni/fi/webmias` directory inside the `WebMIaS.war` ZIP
archive, and set up the following properties:

- `PATHS` – A comma-separated list of directories, where MIaS indices are
  located. This corresponds to the `INDEXDIR` property in the configuration
  file of MIaS, but multiple paths can be specified.
- `INDEX_NAMES` – A comma-separated list of names of the corresponding indices.
- `STORAGES` – A comma-separated list of directories, where the source files
  that were used to produce the corresponding indices are located.
- `MAXRESULTS` – The maximum number of results that the system retrieves.

The resulting file might have the following content:

Example:
```ini
INDEX_NAMES=first-index,second-index
PATHS=/home/MIaS/index1,/home/MIaS/index2
STORAGES=/home/MIaS/dataset1,/home/MIaS/dataset2
MAXRESULTS=10000
```

JVM configuration
-----------------
It can be useful to increase memory limits for Tomcat JVM running WebMIaS
instance. See sample file `setenv.sh.sample`. Modify the configuration to fit
your needs and copy the result as an executable script `setenv.sh` in 
`$CATALINA_BASE/bin` of your Tomcat server to keep your customizations separate 
of the Tomcat distribution scripts.

Citing WebMIaS
==============
Text
----
LÍŠKA, Martin, Petr SOJKA, Michal RŮŽIČKA and Peter MRAVEC. Web Interface and
Collection for Mathematical Retrieval : WebMIaS and MREC. In Petr Sojka,
Thierry Bouche. *DML 2011: Towards a Digital Mathematics Library.* Brno:
Masaryk University, 2011. p. 77–84. ISBN 978-80-210-5542-1. 

BibTeX
------
``` bib
@inproceedings{dml:702604,
     author = "Martin L\'{\i}\v{s}ka and Petr Sojka
               and Michal R\r{u}\v{z}i\v{c}ka and Petr Mravec",
      title = "{Web Interface and Collection for Mathematical Retrieval:
                WebMIaS and MREC}",
     editor = "Petr Sojka and Thierry Bouche",
  booktitle = "{Towards a Digital Mathematics Library.}"
    address = "{Bertinoro, Italy}",
       year = 2011,
      month = Jul,
      pages = "77--84",
  publisher = "{Masaryk University}",
       isbn = "978-80-210-5542-1",
        url = {http://hdl.handle.net/10338.dmlcz/702604},
}
```
