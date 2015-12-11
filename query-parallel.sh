#!/bin/bash

wsurl='http://localhost:8084/WebMIaS/ws/search'
index='0'
limit='1000'

parallel -vv --bar curl --silent --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode '"query=<math><msup><mi>{1}</mi><mn>{2}</mn></msup></math>"' 'http://localhost:8084/WebMIaS/ws/search' \| xmlstarlet sel -t -m '"//totalResults|//time|//Query"' -c . -n ::: a b c x y z ::: $(seq 2 128) 
