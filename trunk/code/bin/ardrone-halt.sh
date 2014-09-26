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


# Ar.Drone 2 halt script.


if [ $# -eq 0 ]
then
  echo "Usage : $(basename $0) <targets...>";
  exit 1;
fi

for i in $*
do
    echo "halt" |netcat $i 23;
done
