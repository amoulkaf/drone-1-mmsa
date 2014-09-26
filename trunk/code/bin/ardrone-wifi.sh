#!/bin/bash

# Copyright (C) 2013-2014  Vincent Autefage

#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU Lesser General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.

#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU Lesser General Public License for more details.

#    You should have received a copy of the GNU Lesser General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.


# Connect a wireless interface card to an ardrone 2.

IFACE="wlan0";
SSID="ardrone";
MODE="managed";
OPERATION="status";
ADDR="none";
CHAN="none";
GW="none";
VER="1.1"

_HELP="Usage: $(basename $0) [-i <wireless iface>] [-o {start|stop|dump|scan|status|up|down}] [-m {adhoc|infra}] [-s <ssid>] [-c {auto}|{none}|<channel>] [-a {auto}|{none}|{192.168.<net>.<host>}] [-g {none}|{<start>:<stop>}]";

function do_usage(){
    echo "$_HELP";
    exit $1;
}

function do_help(){
    echo "$_HELP";
    echo -e "\t -i \t wireless interface to operate (default: wlan0)";
    echo -e "\t -o \t action to do (default: status)";
    echo -e "\t\t\t start \t starts the connection";
    echo -e "\t\t\t stop \t stops the connection";
    echo -e "\t\t\t dump \t dumps traffic in promisc mode";
    echo -e "\t\t\t scan\t displays available wireless networks";
    echo -e "\t\t\t status\t displays wireless interface state";
    echo -e "\t\t\t up\t turns on wireless interface card";
    echo -e "\t\t\t down\t turns off wireless interface card";
    echo -e "\t -m \t wireless connection mode (default: infra)";
    echo -e "\t -s \t SSID to use (default: ardrone)";
    echo -e "\t -c \t wireless channel to use in adhoc mode (default: none)";
    echo -e "\t -a \t IP address to set after connection success (default: none)";
    echo -e "\t\t\t none \t does not try to set IP address";
    echo -e "\t\t\t auto \t fixes IP address through a DHCP server";
    echo -e "\t\t\t <*> \t sets a specific IP address which matches with 192.168.*.*";
    echo -e "\t -g \t gateway service to launch in adhoc mode (default: none)";
    echo -e "\t\t\t none \t does not try to enable the gateway service";
    echo -e "\t\t\t <*> \t launches a DHCP/DNS server, start and stop addresses have to match with 192.168.*.*";
    exit 0;
}

function do_version(){
    echo "ArDrone-WiFi - The magic Wi-Fi Script for Ar.Drone 2 remote connection";
    echo "Version: $VER";
    echo "Author: Vincent Autefage";
    echo "License: GNU Lesser General Public License (LGPL v3)";
    echo "http://nemu.valab.net";
    echo "(c) 2013-2014 Vincent Autefage";
    exit 0;
}


function do_check_error(){
    if ! [ $? -eq 0 ]
    then
	echo "$1";
	exit 1;
    fi
}

function do_check_ok(){
    if [ $? -eq 0 ]
    then
	echo -ne "$1\t\t";
#	exit 1;
    fi
}

function do_error(){
    echo "$1";
    exit 1;
}

function create_tmp_file(){
    /bin/tempfile -m 0666 -s "-$IFACE.cap" --directory /tmp;
}

function do_set_promisc(){
    echo -ne "Enabling promisc on $IFACE...\t\t\t";
    /sbin/ifconfig "$IFACE" promisc &> /dev/null;
    do_check_error "$IFACE does not seem to support promisc mode";
    echo "Done.";
}

function do_unset_promisc(){
    echo -ne "Disabling promisc on $IFACE...\t\t\t";
    /sbin/ifconfig "$IFACE" -promisc &> /dev/null;
    echo "Done.";
}

function do_dump_iface(){
    do_set_promisc;
    _FILE=$(create_tmp_file);
    echo "Dumping iface $IFACE in $_FILE";
    /usr/sbin/tcpdump -q -w "$_FILE" -i "$IFACE";
    do_unset_promisc;
}

function do_check_iface(){
    echo -ne "Checking iface $IFACE...\t\t\t\t"
    /sbin/iw dev |/bin/grep "$IFACE" &> /dev/null;
    do_check_error "$IFACE does not seem to be a valid wireless interface card";
    echo "Done.";
}

function do_sec_scan(){
    /sbin/iwlist "$IFACE" scan |/bin/egrep -i "(essid)|(cell)|(frequency)|(mode)|(results)|(wep)|(wpa)|(ccmp)|(tkip)|(802.1)" 2> /dev/null;
}

function do_short_scan(){
    /sbin/iwlist "$IFACE" scan |/bin/egrep -i "(essid)|(cell)|(frequency)|(mode)|(results)" 2> /dev/null;
}

function do_check_ssid(){
    echo -ne "Checking ssid $SSID...\t\t\t"
    if [ "$MODE" == "managed" ]
    then	
	/sbin/iw dev "$IFACE" scan |/bin/grep "$SSID" &> /dev/null;
	do_check_error "$SSID does not seem to be a valid SSID";
    else
	/sbin/iw dev "$IFACE" scan |/bin/grep "$SSID" &> /dev/null;
	do_check_ok "$SSID already exists.";
    fi
    echo "Done.";
}

function do_check_link(){
    if [ "$MODE" == "managed" ]
    then
	echo -ne "Checking current link...\t\t\t"
	/sbin/iw dev "$IFACE" link |/bin/grep "$IFACE" &> /dev/null;
	do_check_error "$IFACE is not connected";
	echo "Done.";
    fi
}

function do_check_addr(){
    echo -ne "Checking $IFACE addr $ADDR...\t\t\t"
    if [ "$ADDR" == "auto" ]
    then
	/sbin/ip addr show dev "$IFACE" |/bin/grep "inet"| /bin/grep -v "inet6" &> /dev/null;
    elif [ "$ADDR" == "none" ]
    then
	echo -n "";
    else
	/sbin/ip addr show dev "$IFACE" |/bin/grep "$ADDR" &> /dev/null;
    fi
    do_check_error "$IFACE does not seem to be set with addr $ADDR";
    echo "Done.";
}

function do_check_sec_addr(){
    _BASE_L=$(echo "$1" |/usr/bin/cut -d "." -f 1);
    _BASE_R=$(echo "$1" |/usr/bin/cut -d "." -f 2);
    _NET=$(echo "$1" |/usr/bin/cut -d "." -f 3);
    _HOST=$(echo "$1" |/usr/bin/cut -d "." -f 4);
    if [ "$_BASE_L" == "192" ] && [ "$_BASE_R" == "168" ]
    then
	if [ "$_NET" -ge "0" ] && [ "$_NET" -lt "255" ]
	then
	    if [ "$_HOST" -gt "0" ] && [ "$_HOST" -lt "255" ]
	    then
		return;
	    fi
	fi
    fi	    
    do_usage 1;
}

function do_check_sec_gw(){
    _START=$(echo "$GW" |/usr/bin/cut -d ":" -f 1);
    _STOP=$(echo "$GW" |/usr/bin/cut -d ":" -f 2);
    do_check_sec_addr "$_START";
    do_check_sec_addr "$_STOP";
    _START_NET=$(echo "$_START" |/usr/bin/cut -d "." -f 3);
    _START_HOST=$(echo "$_START" |/usr/bin/cut -d "." -f 4);
    _STOP_NET=$(echo "$_STOP" |/usr/bin/cut -d "." -f 3);
    _STOP_HOST=$(echo "$_STOP" |/usr/bin/cut -d "." -f 4);
    if [ "$_START_NET" -eq "$_STOP_NET" ] && [ "$_START_HOST" -lt "$_STOP_HOST" ]
    then
	return;
    fi
    do_usage 1;
}

function do_start_gw(){
    if [ "$ADDR" == "none" ] || [ "$ADDR" == "auto" ] || [ "$GW" == "none" ]
    then
	return;
    else
	_START=$(echo "$GW" |/usr/bin/cut -d ":" -f 1);
	_STOP=$(echo "$GW" |/usr/bin/cut -d ":" -f 2);
	_TIME="2h";
	_NET=$(echo "$ADDR" |/usr/bin/cut -d "." -f 3);
	_NET="192.168.${_NET}.0/24";
	echo -ne "Launching a DHCP/DNS server on $IFACE...\t\t\t";
	/usr/sbin/dnsmasq --interface="$IFACE" --strict-order --expand-hosts --domain="$SSID" --dhcp-range="$_START,$_STOP,$_TIME" --dhcp-option="option:router,$ADDR" &> /dev/null;
	do_check_error "Cannot launch the DHCP/DNS server";
	echo "Done.";
	echo -ne "Setting a forward NAT rule for $IFACE...\t\t\t";
	/sbin/iptables -t nat -A POSTROUTING -s "$_NET" -o eth0 -j MASQUERADE &> /dev/null;
	do_check_error "Cannot set the forward NAT table";
	echo 1 > /proc/sys/net/ipv4/ip_forward;
	do_check_error "Cannot enable IP forwarding";
	echo "Done.";
    fi
}

function do_stop_gw(){
    echo -ne "Stopping gateway service...\t\t\t";
    /usr/bin/killall -r "dnsmasq" &> /dev/null;
    /sbin/iptables -t nat -F POSTROUTING &> /dev/null;
    echo 0 > /proc/sys/net/ipv4/ip_forward;
    echo "Done.";
}

function do_get_ap(){
    if [ "$MODE" == "managed" ]
    then
	_AP=$(/sbin/iw dev "$IFACE" link |/bin/grep -i "$IFACE" |/usr/bin/cut -d " " -f 3);
	_SSID=$(/sbin/iw dev "$IFACE" link |/bin/grep -i "SSID" |/usr/bin/cut -d " " -f 2);
	echo "$IFACE is connected to $_SSID [$_AP]";
    fi
}

function do_get_addr(){
    if [ "$ADDR" == "none" ]
    then
	echo -n "";
    else
	_ADDR=$(/sbin/ip addr show dev "$IFACE" |/bin/grep "inet" |/bin/grep -v "inet6" |/usr/bin/cut -d " " -f 6);
	echo "$IFACE is set to addr $_ADDR";
    fi
}

function do_get_status(){
    /sbin/iwconfig "$IFACE";
    /sbin/iw dev "$IFACE" link;
    do_get_channel;
    /sbin/ip addr show dev "$IFACE";
}

function do_set_link(){
    echo -ne "Connecting to network $SSID...\t\t";
    /sbin/iwconfig "$IFACE" essid "$SSID" &> /dev/null;
    do_check_error "$IFACE cannot be linked to $SSID";
    /bin/sleep 4;
    echo "Done.";
}

function do_unset_link(){
    echo -ne "Reseting $IFACE...\t\t\t\t"
    /sbin/iwconfig "$IFACE" essid "" &> /dev/null;
    echo "Done.";
}

function do_set_channel(){
    if [ "$MODE" == "ad-hoc" ]
    then
	echo -ne "Setting iface $IFACE to channel $CHAN...\t\t";
	if ! [ "$CHAN" == "none" ]
	then
	    /sbin/iwconfig "$IFACE" channel "$CHAN" &> /dev/null;
	    do_check_error "Cannot set iface $IFACE to channel $CHAN";
	fi
	echo "Done.";
    fi
}

function do_check_channel(){
    if [ "$MODE" == "ad-hoc" ]
    then
	echo -ne "Checking iface $IFACE channel...\t\t\t";
	if [ "$CHAN" == "none" ]
	then
	    echo -n "";
	elif [ "$CHAN" == "auto" ]
	then
	    echo -n "";
	else
	    _FREQ=$(/sbin/iwlist "$IFACE" channel |/bin/grep -i "current");
	    do_check_error "$IFACE is not set to any channel";
	    _FREQ=$(echo "$_FREQ" |/usr/bin/cut -d "(" -f 2 |/usr/bin/cut -d ")" -f 1 |/usr/bin/cut -d " " -f 2);
	    if ! [ "$_FREQ" == "$CHAN" ]
	    then
		do_error "$IFACE is not set to channel $CHAN";
	    fi
	fi
	echo "Done.";
    fi
}

function do_get_channel(){
    _FREQ=$(/sbin/iwlist "$IFACE" channel |/bin/grep -i "current" |/usr/bin/cut -d ":" -f 2);
    echo -e "\t$_FREQ\n";
}

function do_set_addr(){
    echo -ne "Setting up $IFACE addr $ADDR...\t\t\t";
    if [ "$ADDR" == "auto" ]
    then
	/sbin/dhclient -4 "$IFACE" &> /dev/null;
    elif [ "$ADDR" == "none" ]
    then
	echo -n "";
    else
	/sbin/ifconfig "$IFACE" "$ADDR" &> /dev/null;
    fi
    do_check_error "$IFACE cannot be set with addr $ADDR";
    echo "Done.";
}

function do_unset_addr(){
    echo -ne "Releasing $IFACE addr...\t\t\t\t"
    /sbin/dhclient -r "$IFACE" &> /dev/null;
    /sbin/dhclient -x "$IFACE" &> /dev/null;
    /sbin/ifconfig "$IFACE" 0.0.0.0 &> /dev/null;
    echo "Done.";
}

function do_set_mode(){
    echo -ne "Setting up iface $IFACE to $MODE mode...\t"
    /sbin/iwconfig "$IFACE" mode "$MODE" &> /dev/null;
    do_check_error "$IFACE cannot be set to $MODE";
    echo "Done.";
}

function do_unset_mode(){
    echo -ne "Reseting iface $IFACE to managed mode...\t\t"
    /sbin/iwconfig "$IFACE" mode managed &> /dev/null;
    echo "Done.";
}

function do_set_power(){
    echo -ne "Disabling iface $IFACE power manager...\t\t"
    /sbin/iwconfig "$IFACE" power off &> /dev/null;
    echo "Done.";
}

function do_start(){
    echo -ne "Starting iface $IFACE...\t\t\t\t"
    /sbin/ifconfig "$IFACE" up &> /dev/null;
    do_check_error "$IFACE cannot be started";
    echo "Done.";
}

function do_stop(){
    echo -ne "Stopping iface $IFACE...\t\t\t\t"
    /sbin/ifconfig "$IFACE" down &> /dev/null;
    do_check_error "$IFACE cannot be stopped";
    echo "Done.";
}

function do_restart(){
    do_stop;
    do_start;
}

while getopts ":hva:c:i:s:m:o:g:" opt; do
    case "$opt" in
	"s")
	    SSID="$OPTARG";
	    ;;
	"i")
	    IFACE="$OPTARG";
	    ;;
	"o")
	    OPERATION="$OPTARG";
	    ;;
	"a")
	    case "$OPTARG" in
		"auto")
		    ADDR="auto";
		    ;;
		"none")
		    ADDR="none";
		    ;;
		*)
		    ADDR="$OPTARG";
		    do_check_sec_addr "$ADDR";
		    ;;
	    esac
	    ;;
	"g")
	    case "$OPTARG" in
		"none")
		    GW="none";
		    ;;
		*)
		    GW="$OPTARG";
		    do_check_sec_gw;
		    ;;
	    esac
	    ;;
	"m")
	    case "$OPTARG" in
		"adhoc")
		    MODE="ad-hoc";
		    ;;
		"infra")
		    MODE="managed";
		    ;;
		*)
		    do_usage 1;
		    ;;
	    esac
	    ;;
	"c")
	    case "$OPTARG" in
		"auto")
		    CHAN="auto";
		    ;;
		"none")
		    CHAN="none";
		    ;;
		*)
		    CHAN="$OPTARG";
		    ;;
	    esac
	    ;;
	"h")
	    do_help;
	    ;;
	"v")
	    do_version;
	    ;;
	":")
	    do_usage 1;
	    ;;
	"?")
	    do_usage 1;
	    ;;
    esac
done

echo "IFACE:      $IFACE";
echo "OPERARTION: $OPERATION"

case "$OPERATION" in
    "start")
	echo "MODE:       $MODE";
	echo "SSID:       $SSID";
	echo "ADDR:       $ADDR";
	echo "CHAN:       $CHAN";
	echo "GW:         $GW";
	do_stop_gw;
	do_check_iface;
	do_stop;
	do_unset_link;
	do_set_mode;
	do_start;
	do_set_power;
	do_set_channel;
	do_check_channel;
	do_check_ssid;
	do_set_link;
	do_check_link;
	do_set_addr;
	do_check_addr;
	do_start_gw;
	do_get_ap;
	do_get_addr;
	;;
    "stop")
	do_stop_gw;
	do_check_iface;
	do_unset_addr;
	do_stop;
	do_unset_link;
	do_unset_mode;
	;;
    "dump")
	do_check_iface;
	do_dump_iface;
	;;
    "status")
	do_check_iface;
	do_get_status;
	;;
    "scan")
	do_check_iface;
	do_short_scan;
	;;
    "sscan")
	do_check_iface;
	do_sec_scan;
	;;
    "up")
	do_check_iface;
	do_start;
	;;
    "down")
	do_check_iface;
	do_stop;
	;;
    *)
	do_usage 1;
	;;
esac
