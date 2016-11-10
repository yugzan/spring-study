#!/bin/sh
usage() { 
	local message="Usage: $(basename "$0") [-h host] \n
	    -h host, default:http://ec2-54-199-217-134.ap-northeast-1.compute.amazonaws.com\n"
	echo $message
}

HOST=http://ec2-54-199-217-134.ap-northeast-1.compute.amazonaws.com

while getopts ":h:" option; do
	case "$option" in
		h)
			HOST=${OPTARG}
			;;
		:)
			echo "Option -$OPTARG requires an argument." >&2
			usage
			exit 1
			;;
		\?)
			echo "Invalid option: -$OPTARG" >&2
			usage
			exit 1
			;;		
	esac
done

requestBody='{"name":"testName", "location":{"name":"Lname", "lat":25.0584992, "lng":121.5547268 }, "tags":["tA","tB"], "channels":[{"name":"chA"}, {"name":"chB"} ] }'

cmd="curl \
  -H 'Accept: application/json' \
  -H 'Content-type: application/json' \
  -i -X POST \
  -d $requestBody \
  $HOST/feeds"

echo "$cmd"
echo "\n==================================\n"
eval $cmd

exit 0
