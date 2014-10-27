#!/bin/sh

HOST=$1

sudo jstatd \
	-J-Djava.security.policy=jstatd.all.policy \
	-J-Djava.rmi.server.hostname=$HOST