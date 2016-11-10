#!/bin/sh
usage() { 
	local message="Usage: $(basename "$0") [-h host] [-f feedId] [-c channelId] [-m maxResults]\n
	    -h host, default:http://ec2-54-199-217-134.ap-northeast-1.compute.amazonaws.com\n
	    -f feedId, default:5339232be4b077d5cda855fc\n
	    -c channelId, default:53390eb7e4b0394512908293\n
	    -m maxResults, default:5"
	echo $message
}

HOST=http://ec2-54-199-217-134.ap-northeast-1.compute.amazonaws.com
FEED_ID=53390eb7e4b0394512908292
CHANNEL_ID=53390eb7e4b0394512908293
MAX_RESULTS=5

while getopts ":h:f:c:m:" option; do
	case "$option" in
		h)
			HOST=${OPTARG}
			;;
		f)
			FEED_ID=${OPTARG}
			;;
		c)
			CHANNEL_ID=${OPTARG}
			;;
		m)
			MAX_RESULTS=${OPTARG}
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

cmd="curl \
  -H 'Accept: application/json' \
  -H 'Content-type: application/json' \
  -X GET \
  -i $HOST/feeds/$FEED_ID/channels/$CHANNEL_ID/data?maxResults=$MAX_RESULTS"

echo "$cmd"
echo "\n==================================\n"
eval $cmd

exit 0
