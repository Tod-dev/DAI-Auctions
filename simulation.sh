#!/bin/bash
NAME=AUCTIONSPACE
PWD=$(pwd)
# Start the RMI Registry
osascript -e "tell app \"Terminal\" to do script \"cd $PWD && ./run.sh RemoteTupleSpace $AUCTIONSPACE\""
#sleep 10 sec to wait for the RMI Registry to start
sleep 10
# Launch some AuctionParticipants
for i in {1..3}
do
  osascript -e "tell app \"Terminal\" to do script \"cd $PWD && ./run.sh  AuctionParticipant $i $AUCTIONSPACE\""
done
# Launch the Auctioneer
osascript -e "tell app \"Terminal\" to do script \"cd $PWD && ./run.sh  Auctioneer $AUCTIONSPACE\""