#!/bin/sh
usage() { 
	local message="Usage: $(basename "$0") [-h host]\n
	    -h host, default:http://ec2-54-199-217-134.ap-northeast-1.compute.amazonaws.com"
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

cmd="curl\
  -H 'Accept: application/json'\
  -H 'Content-type: dapplication/json'\
  -X GET\
  -i $HOST/feeds"

echo "$cmd"
echo "\n==================================\n"
eval $cmd

exit 0
