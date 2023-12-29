#!/bin/bash
AUCTIONSPACE="AuctionSpace"
PWD=$(pwd)
TYPE=$1
# Start the RMI Registry
osascript -e "tell app \"Terminal\" to do script \"cd $PWD && ./run.sh RemoteTupleSpace $AUCTIONSPACE\""
#sleep 10 sec to wait for the RMI Registry to start
sleep 10
# Launch some AuctionParticipants
for i in {1..2}
do
  NAME="AuctionParticipant$i"
  osascript -e "tell app \"Terminal\" to do script \"cd $PWD && ./run.sh  AuctionParticipant $AUCTIONSPACE $NAME $TYPE\""
done
# Launch the Auctioneer
osascript -e "tell app \"Terminal\" to do script \"cd $PWD && ./run.sh  Auctioneer $AUCTIONSPACE $TYPE\""